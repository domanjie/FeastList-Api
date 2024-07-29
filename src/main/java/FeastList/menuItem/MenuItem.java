package FeastList.menuItem;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
@Entity
@Setter
@Getter
@Table(name = "menu_items")
@ToString
@EqualsAndHashCode
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class MenuItem {

	@Column(name = "id", columnDefinition = "UUID")
	@Id
	private  UUID id;

	@Column(name = "name")
	private final String name;

	@Column(name = "price_per_portion" ,columnDefinition = "decimal(10,2)")
	private final float pricePerPortion;

	@Column(name = "vendor_name")
	private final String vendorId;

	@Column(name = "date_added")
	private final Timestamp dateAdded;

	@Column(name = "avatar_url")
	private final String avatarUrl;
}
