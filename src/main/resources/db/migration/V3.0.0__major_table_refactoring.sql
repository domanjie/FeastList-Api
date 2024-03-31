DROP TABLE client_runner_detail ;
DROP TABLE vendor_detail ;

ALTER TABLE activation
DROP PRIMARY KEY,
ADD PRIMARY KEY(user_id);

ALTER TABLE users
DROP COLUMN zip_code,
DROP COLUMN location,
DROP COLUMN role;

CREATE TABLE clients (
    client_id VARCHAR(90) PRIMARY KEY,
    CONSTRAINT fk_users_clients FOREIGN KEY (client_id) REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE vendors(
	vendor_name VARCHAR(90) PRIMARY KEY,
	header_photo VARCHAR(512),
	email VARCHAR(90) NOT NULL,
	location VARCHAR(255),
	CONSTRAINT fk_users_vendors FOREIGN KEY(vendor_name) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
ALTER TABLE A_LA_CARTE_meal_items
RENAME TO custom_meal_menu_items,
DROP PRIMARY KEY,
DROP FOREIGN KEY fk_meals_A_LA_CARTE_meal_items,
DROP FOREIGN KEY fk_menu_items_A_LA_CARTE_meal_items,
DROP COLUMN  meal_id,
DROP COLUMN  menu_item_id;

ALTER TABLE custom_meal_menu_items
ADD COLUMN  meal_id UUID,
ADD COLUMN  menu_item_id UUID,
ADD PRIMARY KEY (meal_id, menu_item_id);


ALTER TABLE tray_items
RENAME tray,
DROP PRIMARY KEY,
DROP FOREIGN KEY  fk_meals_tray_items,
DROP FOREIGN KEY fk_users_tray_items,
DROP COLUMN meal_id,
ADD COLUMN meal_id UUID,
ADD PRIMARY KEY(client_id,meal_id);

ALTER TABLE meals
DROP PRIMARY KEY,
DROP FOREIGN KEY fk_users_meals,
DROP CONSTRAINT name,
DROP COLUMN meal_type,
DROP COLUMN user_id,
DROP COLUMN avatar_url,
DROP COLUMN price,
DROP COLUMN name,
DROP COLUMN id,
ADD column id UUID DEFAULT CONCAT(UNHEX(CONV(ROUND(UNIX_TIMESTAMP(CURTIME(4))*1000), 10, 16)), RANDOM_BYTES(10)),
ADD PRIMARY KEY(id);

CREATE TABLE custom_meal(
	id UUID PRIMARY KEY DEFAULT CONCAT(UNHEX(CONV(ROUND(UNIX_TIMESTAMP(CURTIME(4))*1000), 10, 16)), RANDOM_BYTES(10)),
	vendor_name VARCHAR(90) NOT NULL,
	client_id VARCHAR(90) NOT NULL,
	CONSTRAINT fk_vendors_custom_meal FOREIGN KEY(vendor_name) REFERENCES vendors(vendor_name) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_clients_custom_meal FOREIGN KEY(client_id) REFERENCES clients(client_id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_meals_custom_meal FOREIGN KEY (id) REFERENCES meals(id) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
CREATE TABLE preMade_meal(
	id UUID PRIMARY KEY  DEFAULT CONCAT(UNHEX(CONV(ROUND(UNIX_TIMESTAMP(CURTIME(4))*1000), 10, 16)), RANDOM_BYTES(10)),
	vendor_name VARCHAR(90) NOT NULL,
	meal_name VARCHAR(90) NOT NULL,
	avatar_url VARCHAR(512),
	price DECIMAL(11,2) NOT NULL,
	UNIQUE(vendor_name,meal_name),
	CONSTRAINT fk_meals_preMade_meal FOREIGN KEY(id) REFERENCES meals(id) ON DELETE CASCADE  ON UPDATE CASCADE,
	CONSTRAINT fk_vendors_preMade_meal FOREIGN KEY(vendor_name) REFERENCES vendors(vendor_name) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;




ALTER TABLE menu_items
MODIFY COLUMN price_per_portion DECIMAL(10,2) NOT NULL,
RENAME COLUMN vendor_id to vendor_name,
DROP FOREIGN KEY fk_users_menu_items,
DROP CONSTRAINT name,
DROP PRIMARY KEY,
DROP  COLUMN id,
ADD COLUMN id UUID DEFAULT CONCAT(UNHEX(CONV(ROUND(UNIX_TIMESTAMP(CURTIME(4))*1000), 10, 16)), RANDOM_BYTES(10)),
ADD PRIMARY KEY(id),
ADD UNIQUE KEY(vendor_name,name),
ADD CONSTRAINT fk_vendors_menu_items FOREIGN KEY (vendor_name) REFERENCES vendors(vendor_name) ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE custom_meal_menu_items
ADD CONSTRAINT fk_custom_meal_menu_items FOREIGN KEY(menu_item_id) REFERENCES menu_items(id) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT fk_custom_meal_menu_items_custom_meal FOREIGN KEY(meal_id) REFERENCES custom_meal(id) ON DELETE CASCADE ON UPDATE CASCADE;


alter table tray
ADD CONSTRAINT fk_clients_tray FOREIGN KEY(client_id) REFERENCES clients(client_id) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT fk_meals_tray  FOREIGN KEY (meal_id) REFERENCES meals(id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE password_reset
MODIFY COLUMN reset_code UUID NOT NULL;

ALTER TABLE custom_meal
DROP CONSTRAINT fk_vendors_custom_meal,
DROP COLUMN vendor_name;

ALTER TABLE preMade_meal
DROP CONSTRAINT vendor_name,
DROP CONSTRAINT fk_vendors_preMade_meal,
DROP COLUMN vendor_name;


ALTER TABLE meals
ADD COLUMN vendor_name VARCHAR(90) NOT NULL,
ADD CONSTRAINT fk_vendors_meals FOREIGN KEY(vendor_name) REFERENCES vendors(vendor_name) ON DELETE CASCADE ON UPDATE CASCADE;
