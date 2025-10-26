CREATE TABLE IF NOT EXISTS inventory_outbox
(
	id           BIGSERIAL PRIMARY KEY,
	product_name VARCHAR(255) NOT NULL,
	quantity     INT          NOT NULL,
	event_status VARCHAR(255) NOT NULL DEFAULT 'NEW',
	created_at   TIMESTAMP    NOT NULL DEFAULT NOW(),
	updated_at   TIMESTAMP
)