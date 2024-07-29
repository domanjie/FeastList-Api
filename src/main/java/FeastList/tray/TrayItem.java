package FeastList.tray;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name="tray")
@ToString
@Builder
@EqualsAndHashCode
@Getter
public class TrayItem {

    @EmbeddedId
    private final TrayItemId trayItemId;
    private  int amount;

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor(force = true)
    @EqualsAndHashCode
    @ToString
    @Getter
    public static class TrayItemId {
        @Column(name ="client_id")
        private final String clientId;

        @Column(name="meal_id")
        @JdbcType(VarcharJdbcType.class)
        private final UUID mealId;
    }
}
