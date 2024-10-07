package FeastList.orders.repository;

import FeastList.orders.domain.Order;
import FeastList.orders.domain.OrderGroup;
import FeastList.orders.domain.OrderStatus;
import FeastList.orders.dto.out.OrderDto;
import FeastList.orders.dto.out.OrderGroupDto;
import FeastList.orders.dto.out.OrderItem;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;


@Repository
public class OrderRepositoryExtensionImpl implements OrderRepositoryExtension {

    private final EntityManager entityManager;

    public  OrderRepositoryExtensionImpl(EntityManager  entityManager){
        this.entityManager= entityManager;
    }
    @Override
    public List<OrderDto> getClientOrders(String client) {

        List<Object[]> resultSet=entityManager.createNativeQuery("""
                SELECT
                	o.id,
                	o.placed_at,
                	o.delivery_location ,
                	v.vendor_username,
                	og.delivery_fee,
                	og.status,
                	pmm.meal_name,
                	oi.quantity,
                	pmm.price,
                	pmm.avatar_url
                FROM
                	orders o
                LEFT JOIN
                	order_group og
                ON
                	o.id=og.order_id
                LEFT JOIN
                	order_item oi
                on
                	og.order_id =oi.order_id AND og.vendor =oi.vendor\s
                LEFT JOIN
                	pre_made_meal pmm
                ON
                	oi.meal_id =pmm.id
                INNER JOIN
                	vendors v
                ON
                	og.vendor =v.vendor_id
                WHERE
                	o.client_id =:clientId
                ORDER BY o.placed_at DESC;
                """)
        .setParameter("clientId",client).getResultList();

        var returnList=new ArrayList<OrderDto>();

        for(Object[] tuple: resultSet) {
            var length = returnList.size();
            var orderId = (UUID) tuple[0];
            var placedAt = (Timestamp) tuple[1];
            var deliveryLocation = (String) tuple[2];
            var vendorName = (String) tuple[3];
            var deliveryFee = (BigDecimal) tuple[4];
            var status = (String) tuple[5];
            var mealName = (String) tuple[6];
            var quantity = (int) tuple[7];
            var price = (BigDecimal) tuple[8];
            var avatar_url = (String) tuple[9];

            if (length > 0 && Objects.equals(returnList.get(length - 1).orderId(), orderId)) {
                var lastAddedOrderGroup = returnList.get(length - 1).orderVendorGroups().get(returnList.get(length - 1).orderVendorGroups().size() - 1);
                if (Objects.equals(vendorName, lastAddedOrderGroup.vendorName())) {
                    lastAddedOrderGroup.orderItems().add(new OrderItem(mealName, quantity, price, avatar_url));
                } else {
                    var orderGroup = new OrderGroupDto(vendorName, deliveryFee, new ArrayList<>(), OrderStatus.valueOf(status));
                    orderGroup.orderItems().add(new OrderItem(mealName, quantity, price, avatar_url));
                    returnList.get(length-1).orderVendorGroups().add(orderGroup);
                }
            } else {
                var orderGroup = new OrderGroupDto(vendorName, deliveryFee, new ArrayList<>(), OrderStatus.valueOf(status));
                orderGroup.orderItems().add(new OrderItem(mealName, quantity, price, avatar_url));
                var order = new OrderDto(orderId, new ArrayList<>(), placedAt, deliveryLocation);
                order.orderVendorGroups().add(orderGroup);
                returnList.add(order);
            };
        }
        return  returnList;
    }
}
