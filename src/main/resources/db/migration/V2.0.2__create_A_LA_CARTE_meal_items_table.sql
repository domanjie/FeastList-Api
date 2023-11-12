CREATE TABLE A_LA_CARTE_meal_items(
	meal_id INT ,
	menu_item_id INT,
	amount TINYINT NOT NULL,
	PRIMARY KEY(meal_id,menu_item_id),
	CONSTRAINT fk_meals_A_LA_CARTE_meal_items FOREIGN KEY(meal_id) REFERENCES meals(id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_menu_items_A_LA_CARTE_meal_items FOREIGN KEY (menu_item_id) REFERENCES menu_items(id) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;