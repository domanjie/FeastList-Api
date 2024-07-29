package FeastList.orders.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Builder
@Embeddable
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class OrderItemDetails {

    @Column(name = "quantity")
    private final int quantity;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private final OrderStatus orderStatus;

    @Column(name="delivery_fee")
    private final double deliveryFee;
}
