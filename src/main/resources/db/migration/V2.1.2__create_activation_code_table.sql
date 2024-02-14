 CREATE TABLE  activation (
 	activation_code varchar(11) primary key,
 	user_id varchar(90) NOT NULL,
 	ttl BIGINT NOT NULL,
 	CONSTRAINT fk_users_activation FOREIGN KEY(user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
 )ENGINE=InnoDB DEFAULT CHARSET=UTF8;