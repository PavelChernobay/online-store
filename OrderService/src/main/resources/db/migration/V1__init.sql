CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS users
(
	id       UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	username VARCHAR(255) NOT NULL UNIQUE,
	password VARCHAR(255) NOT NULL,
	email    VARCHAR(255) NOT NULL UNIQUE,
	role     VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS baskets
(
	id          UUID PRIMARY KEY        DEFAULT gen_random_uuid(),
	user_id     UUID           NOT NULL UNIQUE REFERENCES users (id) ON DELETE CASCADE,
	total_sum DECIMAL(10, 2) NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS orders
(
	id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	user_id    UUID REFERENCES users (id) NOT NULL,
	status     VARCHAR(20)                NOT NULL,
	created_at TIMESTAMP                  NOT NULL,
	total_sum  DECIMAL(10, 2)   DEFAULT 0 NOT NULL
);

CREATE TABLE IF NOT EXISTS product_items
(
	id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	product_id UUID           NOT NULL,
	basket_id  UUID             DEFAULT NULL REFERENCES baskets (id) ON DELETE CASCADE,
	order_id   UUID UNIQUE      DEFAULT NULL REFERENCES orders (id) ON DELETE CASCADE,
	name       VARCHAR(255)   NOT NULL,
	quantity   INT            NOT NULL,
	price      DECIMAL(10, 2) NOT NULL,
	sale       INT              DEFAULT 0,
	total_sum  DECIMAL(10, 2)        NOT NULL NOT NULL
);

