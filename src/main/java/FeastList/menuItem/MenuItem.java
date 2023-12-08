package FeastList.menuItem;

import java.sql.Timestamp;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
public class MenuItem {

	private final int  id;

	private final String name;

	private final double pricePerPortion;

	private final String vendorId;

	private final Timestamp date_added;

	private final String avatarUrl;
}
