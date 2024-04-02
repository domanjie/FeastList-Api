package FeastList.menuItem;

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

	@Column(name = "id")
	@Id
	private  UUID id;

	@Column(name = "name")
	private final String name;

	@Column(name = "price_per_portion")
	private final double pricePerPortion;

	@Column(name = "vendor_name")
	private final String vendorId;

	@Column(name = "date_added")
	private final Timestamp dateAdded;

	@Column(name = "avatar_url")
	private final String avatarUrl;
}
