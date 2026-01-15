CREATE TABLE IF NOT EXISTS analytics_outbox
(
	id          BIGSERIAL PRIMARY KEY,
	order_id    UUID         NOT NULL,
	product_id  UUID         NOT NULL,
	user_id     UUID         NOT NULL,
	quantity    INT          NOT NULL,
	price       DECIMAL      NOT NULL,
	sale        INT          NOT NULL,
	total_price DECIMAL      NOT NULL,
	event_status      VARCHAR(255) NOT NULL DEFAULT 'NEW',
	created_at  TIMESTAMP             DEFAULT NOW(),
	updated_at  TIMESTAMP
);