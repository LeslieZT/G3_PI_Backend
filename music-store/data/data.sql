INSERT INTO brands (name) VALUES ('Zildjian'), ('Peavey'), ('Yamaha');

INSERT INTO categories (name) VALUES ('Guitarras'), ('Baterías'), ('Bajos');

INSERT INTO products (category_id, brand_id, name, price_per_hour, quantity, status)
VALUES (1, 1, 'Bajo Yamaha ', 500.00, 4, TRUE),
 (2, 3, 'Batería Peavey ', 600.00, 4, TRUE);

INSERT INTO roles (name) VALUES ('admin'), ('user');

INSERT INTO users (first_name, last_name, email, password, role_id, creation_date)
VALUES ('Juan', 'Perez', 'juan@gmail.com', '123456', 1, '2022-01-01'),
 ('Maria', 'Perez', 'maria@gmail.com', '123456', 1, '2022-01-01'),
 ('Pedro', 'Perez', 'pedro@gmail.com', '123456', 1, '2022-01-01'),
