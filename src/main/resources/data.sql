
INSERT INTO orders (id, created_date, init_by, last_modified_by, modified_date, buyer_id, buyer_username, `status`, total) VALUES (1, NOW(), 'system', 'system', NOW(), 5, 'user_1', 0, 35);
INSERT INTO order_detail (id, created_date, init_by, last_modified_by, modified_date, product_id, product_price, quantity, sub_total, order_id) VALUES (1, NOW(), 'system', 'system', NOW(), 1, 10, 2, 20, 1);
INSERT INTO order_detail (id, created_date, init_by, last_modified_by, modified_date, product_id, product_price, quantity, sub_total, order_id) VALUES (2, NOW(), 'system', 'system', NOW(), 3, 15, 3, 45, 1);

INSERT INTO orders (id, created_date, init_by, last_modified_by, modified_date, buyer_id, buyer_username, `status`, total) VALUES (2, NOW(), 'system', 'system', NOW(), 6, 'user_2', 0, 195);
INSERT INTO order_detail (id, created_date, init_by, last_modified_by, modified_date, product_id, product_price, quantity, sub_total, order_id) VALUES (3, NOW(), 'system', 'system', NOW(), 2, 15, 1, 25, 2);
INSERT INTO order_detail (id, created_date, init_by, last_modified_by, modified_date, product_id, product_price, quantity, sub_total, order_id) VALUES (4, NOW(), 'system', 'system', NOW(), 4, 25, 2, 50, 2);
INSERT INTO order_detail (id, created_date, init_by, last_modified_by, modified_date, product_id, product_price, quantity, sub_total, order_id) VALUES (5, NOW(), 'system', 'system', NOW(), 5, 30, 4, 120, 2);
