package FeastList.orders.dto.in;

import java.util.HashMap;
import java.util.UUID;

public record OrderGroupDto(String vendorId, HashMap<UUID,Integer> orderItems) {
}
