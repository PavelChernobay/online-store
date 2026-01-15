CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS orders
(
	id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	order_id    UUID    NOT NULL,
	product_id  UUID    NOT NULL,
	user_id     UUID    NOT NULL,
	quantity    INT     NOT NULL,
	price       DECIMAL NOT NULL,
	sale        INT     NOT NULL,
	total_price DECIMAL NOT NULL,
	created_at  TIMESTAMP DEFAULT NOW()
)