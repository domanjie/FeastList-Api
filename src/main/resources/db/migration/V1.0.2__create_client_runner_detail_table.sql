CREATE TABLE client_runner_detail(
	id VARCHAR(90) PRIMARY KEY,
	first_name varchar(30),
	last_name varchar(30),
	gender ENUM("FEMALE","MALE")NOT NULL,
	CONSTRAINT fk_users_client_runner_detail  FOREIGN KEY(id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;