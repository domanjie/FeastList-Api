CREATE TABLE tray_items(
    meal_id INT,
    client_id VARCHAR(90),
    amount int NOT NULL,
    PRIMARY KEY(meal_id,client_id),
    CONSTRAINT fk_users_tray_items FOREIGN KEY(client_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_meals_tray_items FOREIGN KEY(meal_id) REFERENCES meals(id) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;