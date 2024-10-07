package FeastList.orders.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@Embeddable
@NoArgsConstructor(force = true)
public class OrderGroupPk {
    private final UUID orderId;
    private final String vendor;
}
