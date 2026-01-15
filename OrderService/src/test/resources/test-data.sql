INSERT INTO users (id, username, password, email, role)
VALUES ('11111111-1111-1111-1111-111111111111', 'pavel', '$2a$10$wbp4M/RfYFgMHdPPhPW17.QcF/doQn0Yi64o3QyRzNHtdPgYx4CyG', 'pavel@test.com', 'ROLE_USER'),
	   ('22222222-2222-2222-2222-222222222222', 'elona', '$2a$10$uQspe5AVIdIMFlsTAnC7IeZuTEi.FU89oleexhVsZF73sekBz9XE2', 'elona@test.com', 'ROLE_ADMIN');

INSERT INTO baskets (id, user_id, total_sum)
VALUES ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '11111111-1111-1111-1111-111111111111', 300);

INSERT INTO basket_products (id, product_id, basket_id, name, quantity, price, sale, total_sum)
VALUES ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'Product1', 1, 100, 0, 100),
	   ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'Product2', 1, 200, 0, 200);