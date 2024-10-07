DROP TABLE orders_meals ;
ALTER TABLE vendors
	RENAME COLUMN vendor_name TO vendor_id,
	MODIFY COLUMN email VARCHAR(90) NOT NULL UNIQUE;
ALTER TABLE vendors
	RENAME COLUMN email to vendor_username;
CREATE TABLE  order_group(
	order_id UUID,
	vendor VARCHAR(90),
	status VARCHAR(90) NOT NULL,
	delivery_fee DECIMAL (11,2) NOT NULL,
	primary key(order_id ,vendor),
	CONSTRAINT vendors_order_group FOREIGN KEY (vendor) REFERENCES vendors(vendor_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT order_ordergroup FOREIGN KEY (order_id)  REFERENCES orders(id) ON DELETE RESTRICT ON UPDATE RESTRICT
)ENGINE=InnoDB DEFAULT CHARSET =UTF8;
CREATE TABLE order_item(
	order_id UUID,
	vendor varchar(90),
	meal_id UUId,
	quantity INTEGER NOT NULL,
	PRIMARY KEY(order_id,vendor,meal_id),
	CONSTRAINT ordergroup_orderitem   FOREIGN KEY (order_id,vendor) REFERENCES order_group(order_id, vendor) ON DELETE RESTRICT  ON UPDATE RESTRICT,
	CONSTRAINT orderitem_meal FOREIGN KEY(meal_id) REFERENCES meals(id) ON DELETE RESTRICT ON UPDATE RESTRICT
)ENGINE=InnoDB DEFAULT CHARSET =UTF8;
