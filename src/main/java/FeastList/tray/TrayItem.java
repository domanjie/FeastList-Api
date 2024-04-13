package FeastList.tray;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name="tray")
@ToString
@Builder
@EqualsAndHashCode
public class TrayItem {

    @EmbeddedId
    private final TrayItemId trayItemId;
    private  int amount;

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor(force = true)
    @EqualsAndHashCode
    @ToString
    public static class TrayItemId {

        private final String clientId;

        @Column(columnDefinition = "UUID")
        private final UUID mealId;
    }
}
