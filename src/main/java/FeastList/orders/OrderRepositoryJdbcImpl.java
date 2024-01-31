package FeastList.orders;

import FeastList.meals.Meal;
import FeastList.meals.MealRepository;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepositoryJdbcImpl implements OrderRepository{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final MealRepository mealRepository;
    public OrderRepositoryJdbcImpl(NamedParameterJdbcTemplate jdbcTemplate ,MealRepository mealRepository){
        this.jdbcTemplate=jdbcTemplate;
        this.mealRepository=mealRepository;
    }

    @Override
    public List<Order> getOrdersByClientId(String clientId) {
        var query= """
                SELECT orders.order_id, client_id,
                runner_id, vendor_id , delivery_location,
                meals_cost, delivery_cost, placed_at ,meal_id , meal_amount
                FROM orders
                JOIN
                order_meal
                ON
                orders.order_id=order_meal.order_id
                WHERE client_id=:clientId
                ORDER BY order_id;
                """;
        var param =new MapSqlParameterSource().addValue("clientId",clientId);
        return jdbcTemplate.query(query,param,orderResultSetExtractor());
    }

    @Override
    public Optional<Order> getOrderById(Long orderId) {

        var query= """
                SELECT orders.order_id, client_id,
                runner_id, vendor_id , delivery_location,
                meals_cost, delivery_cost, placed_at ,meal_id , meal_amount
                FROM orders
                JOIN
                order_meal
                ON
                orders.order_id=order_meal.order_id
                WHERE orders.order_id=:orderId
                ORDER BY order_id;
                """;
        var param =new MapSqlParameterSource().addValue("orderId",orderId);
        var orders=jdbcTemplate.query(query,param,orderResultSetExtractor());
        return Optional.of(orders.get(0));
    }
    @Override
    public List<Order> getOrdersByVendorId(String vendorId) {
        var query= """
                SELECT orders.order_id, client_id,
                runner_id, vendor_id , delivery_location,
                meals_cost, delivery_cost, placed_at ,meal_id , meal_amount
                FROM orders
                JOIN
                order_meal
                ON
                orders.order_id=order_meal.order_id
                WHERE vendor_id=:vendorId
                ORDER BY order_id;
                """;
        var param =new MapSqlParameterSource().addValue("vendorId",vendorId);
        return jdbcTemplate.query(query,param,orderResultSetExtractor());
    }

    @Override
    public Order updateOrder(Order order) {
        return null;
    }

    @Override
    public int saveOrder(Order order) {
        var insertIntoOrder= """
                INSERT INTO  orders(client_id,runner_id,vendor_id,delivery_location,meals_cost,delivery_cost)
                VALUES (:clientId,:runnerId,:vendorId,:deliveryLocation,:mealsCost,:deliveryCost)
                """;

        var params=new MapSqlParameterSource()
                .addValue("clientId",order.getClientId())
                .addValue("runnerId",order.getRunnerId())
                .addValue("vendorId",order.getVendorId())
                .addValue("deliveryLocation",order.getDeliveryLocation())
                .addValue("deliveryCost",order.getDeliveryCost())
                .addValue("mealsCost",order.getMealsCost());

        var keyHolder=new GeneratedKeyHolder();

        jdbcTemplate.update(insertIntoOrder,params,keyHolder);

        int orderId= Objects.requireNonNull(keyHolder.getKey()).intValue();

        batchInsertOrderMeals(order.getOrderItems(),orderId);

        return orderId;
    }

    private void batchInsertOrderMeals(List<OrderItem> orderItems, int orderId) {

        var query = """
                INSERT INTO order_meal(order_id,meal_id,meal_amount)
                VALUES(:orderId,:mealId,:mealAmount)
                """;
        var params=orderItems.stream()
                .map(orderItem -> new MapSqlParameterSource()
                        .addValue(":orderId",orderId)
                        .addValue("mealId",orderItem.meal().getId())
                        .addValue("mealAmount",orderItem.mealAmount()))
                .toArray(MapSqlParameterSource[]::new);
        jdbcTemplate.batchUpdate(query,params);

    }

    private ResultSetExtractor<List<Order>> orderResultSetExtractor(){
        return rs -> {
            var orderMap=new HashMap<Long,Order>();

            while (rs.next()){
                var orderId=rs.getLong("order_id");
                var order=orderMap.get(orderId);
                if(order==null){
                    order=new Order.OrderBuilder()
                            .orderId(orderId)
                            .clientId(rs.getString("client_id"))
                            .runnerId(rs.getString("runner_id"))
                            .vendorId(rs.getString("vendor_id"))
                            .orderItems(new ArrayList<>())
                            .deliveryLocation(rs.getString("delivery_location"))
                            .placedAt(rs.getTimestamp("placed_at"))
                            .build();
                    orderMap.put(orderId,order);
                }
                order.getOrderItems().add(new OrderItem(
                        mealRepository.getMealById((long) rs.getInt("meal_id")).get() ,
                        rs.getInt("meal_amount")
                ));

            }
            return new ArrayList<>(orderMap.values());
        };
    }


}
