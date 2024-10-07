package FeastList.orders.dto.out;

public record OrderItem (String itemName, int quantity, java.math.BigDecimal price, String avatar_url){
}
