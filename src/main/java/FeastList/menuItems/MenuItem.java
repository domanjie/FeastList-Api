package FeastList.menuItems;

import java.sql.Timestamp;
import java.util.Objects;

import FeastList.users.User;
import com.sun.jna.platform.win32.Sspi;
import lombok.Data;

@Data
public class MenuItem {

	private final int  id;
	private final String name;
	private final double pricePerPortion;
	private final String vendorId;
	private final String avatarUrl;
	private final Timestamp date_added;
}
