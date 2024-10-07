package FeastList.meal.dto.Out;
import jakarta.persistence.Column;

import java.util.UUID;

public interface PreMadeMealProjection{
    UUID getId();
    String getVendor_name();
    String getMeal_name();
    String getAvatar_url();
    double getPrice();
    String getVendor_avatar_url();
    int getIs_in_cart();
}
