package FeastList.orders.dto.out;


import FeastList.orders.domain.OrderStatus;
import lombok.Builder;

import java.util.List;

@Builder
public record OrderGroupDto(String vendorName, java.math.BigDecimal deliveryFee, List<OrderItem> orderItems , OrderStatus orderStatus){}