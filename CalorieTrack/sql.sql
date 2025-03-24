CREATE TABLE users(
	id SERIAL PRIMARY KEY,
	name VARCHAR(200) NOT NULL,
	email VARCHAR(255) UNIQUE NOT NULL,
	age INT CHECK (age >= 0 AND age <= 120),
	weight DECIMAL(5,2) CHECK (weight > 0),
	height DECIMAL(5,2) CHECK (height > 0),
	goal VARCHAR(20) CHECK (goal IN ('WEIGHT_LOSS','MAINTENANCE','WEIGHT_GAIN')),
	daily_calories INT NOT NULL
);

CREATE TABLE dishes(
	id SERIAL PRIMARY KEY,
	name VARCHAR(200) NOT NULL UNIQUE,
	calories INT CHECK (calories > 0),
	proteins DECIMAL(5,2) CHECK (proteins >= 0),
	fats DECIMAL(5,2) CHECK (fats >= 0),
	carbohydrates DECIMAL(5,2) CHECK (carbohydrates >= 0)
);

CREATE TABLE meals(
	id SERIAL PRIMARY KEY,
	user_id INT,
	meal_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE meal_dishes (
	meal_id INT,
	dish_id INT,
	portion INT CHECK (portion > 0),
	FOREIGN KEY(meal_id) REFERENCES meals(id) ON DELETE CASCADE,
	FOREIGN KEY(dish_id) REFERENCES dishes(id) ON DELETE CASCADE,
	PRIMARY KEY (meal_id,dish_id)
);