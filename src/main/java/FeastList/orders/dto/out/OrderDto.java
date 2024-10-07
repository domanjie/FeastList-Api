package FeastList.orders.dto.out;



import lombok.Builder;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;


@Builder
public record OrderDto(UUID orderId, List<OrderGroupDto> orderVendorGroups , Timestamp placedAt, String deliveryLocation) {
}
