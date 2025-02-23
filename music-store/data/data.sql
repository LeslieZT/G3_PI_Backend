INSERT INTO brands (name) VALUES ('Zildjian'), ('Peavey'), ('Yamaha');

INSERT INTO categories (name) VALUES ('Guitarras'), ('Baterías'), ('Bajos');

INSERT INTO products (category_id, brand_id, name, price_per_hour, quantity, status)
VALUES (1, 1, 'Bajo Yamaha ', 500.00, 4, TRUE),
 (2, 3, 'Batería Peavey ', 600.00, 4, TRUE);
