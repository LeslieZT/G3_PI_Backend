--
SELECT CONCAT('ALTER TABLE ', table_name, ' CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;')
FROM information_schema.tables
WHERE table_schema = 'music-store'
AND table_type = 'BASE TABLE';

INSERT INTO roles (name) VALUES ('admin'), ('user');

-- insercion de datos en categorias.
INSERT INTO categories
(creation_date, id, image_url, name)
VALUES
('2025-03-1', DEFAULT, 'https://res.cloudinary.com/dqc7cuyox/image/upload/fl_preserve_transparency/v1741105007/CGuitarras_oney1g.jpg?_s=public-apps', 'Guitarras'),
('2025-03-1', DEFAULT, 'https://res.cloudinary.com/dqc7cuyox/image/upload/fl_preserve_transparency/v1741105104/CBaterias_twvhnx.jpg?_s=public-apps', 'Baterias'),
('2025-03-1', DEFAULT, 'https://res.cloudinary.com/dqc7cuyox/image/upload/fl_preserve_transparency/v1741105173/CPianos_ktcukz.jpg?_s=public-apps', 'Pianos'),
('2025-03-1', DEFAULT, 'https://res.cloudinary.com/dqc7cuyox/image/upload/fl_preserve_transparency/v1741105226/CBajos_y0dbkl.jpg?_s=public-apps', 'Bajos'),
('2025-03-1', DEFAULT, 'https://res.cloudinary.com/dqc7cuyox/image/upload/fl_preserve_transparency/v1741105287/CElectricos_zzifvp.jpg?_s=public-apps', 'Electricos'),
('2025-03-1', DEFAULT, 'https://res.cloudinary.com/dqc7cuyox/image/upload/fl_preserve_transparency/v1741105359/CAccesorios_erfr38.jpg?_s=public-apps', 'Accesorios');

-- insercion de datos en tabla Brand
INSERT INTO brands(id, name, creation_date)
VALUES
(DEFAULT, 'Pearl', '2025-03-1'),
(DEFAULT, 'Roland', '2025-03-1'),
(DEFAULT, 'Fender', '2025-03-1'),
(DEFAULT, 'Tama', '2025-03-1'),
(DEFAULT, 'Sony', '2025-03-1'),
(DEFAULT, 'Gibson', '2025-03-1'),
(DEFAULT, 'Hohner', '2025-03-1'),
(DEFAULT, 'Steinway & Sons', '2025-03-1'),
(DEFAULT, 'Otras', '2025-03-14');



-- insercion de datos en productos
INSERT INTO products(`category_id`, `creation_date`, `id`,`is_available`, `price_per_hour`, `stock_quantity`, `launch_year`,
`product_condition`, `description`, `brand_id`, `material`, `product_size`, `model`, `name`, `origin`, `recommended_use`)
VALUES
( 2, '2025-03-16', DEFAULT, true, 35.99, 1, '2022', 'Nueva','Bateria acustica profesional con gran resonancia y durabilidad.',
1, 'Metal y madera', '140x90x30', 'EXX725S', 'Bateria Pearl Export', 'Japon', 'Profesional'),
( 3, '2025-03-17', DEFAULT, true, 28.99, 1, '2021', 'Nuevo', 'Teclado digital con 88 teclas sensibles al tacto y multiples funciones integradas.',
2, 'Plastico y metal', '130x35x40', 'FP-30X', 'Teclado Roland FP-30X', 'EE.UU.', 'Profesional'),
( 1, '2025-03-18', DEFAULT, true, 22.50, 1, '2022', 'Seminueva', 'Guitarra acustica de seis cuerdas con gran resonancia y acabado de madera natural.',
3, 'Madera', '100x40x50', 'CD-60', 'Guitarra Fender CD-60', 'Mexico', 'Principiante'),
( 2, '2025-03-19', DEFAULT, true, 40.00, 1, '2023', 'Nueva', 'Bateria acustica de 5 piezas con platillos incluidos, ideal para estudios y presentaciones en vivo.',
4, 'Madera y metal', '150x90x100', 'Imperialstar', 'Bateria Tama Imperialstar', 'Japon', 'Profesional'),
( 6, '2025-03-20', DEFAULT, true, 15.99, 1, '2022', 'Nuevo', 'Audifonos profesionales con cancelacion de ruido y sonido envolvente.',
 5, 'Plastico y metal', '18x15x5', 'WH-1000XM4', 'Audifonos Sony WH-1000XM4', 'Japon', 'Intermedio'),
 (
     1, '2025-03-21', DEFAULT, true, 30.00, 1, '2023', 'Nueva',
     'Guitarra electrica de seis cuerdas con cuerpo de caoba y pastillas humbucker.',
     6, 'Madera', '102x35x30', 'Les Paul Standard 50s',
     'Guitarra Gibson Les Paul', 'EE.UU.', 'Profesional'
 ),
 (
     4, '2025-03-21', DEFAULT, true, 25.00, 1, '2022', 'Nueva',
     'Bajo eléctrico de cuatro cuerdas con cuerpo de aliso y pastillas pasivas de precisión.',
     8, 'Madera', '115x38x10', 'Precision Bass',
     'Fender Precision Bass', 'EE.UU.', 'Profesional'
 ),
 (
     3, '2025-03-16', DEFAULT, true, 50.00, 1, '2023', 'Nuevo',
     'Piano de cola con 88 teclas, sonido envolvente y sistema de martillos de alta precision.',
     8, 'Madera y metal', '150x180x90', 'Model D',
     'Piano Steinway & Sons Model D', 'Alemania', 'Profesional'
 ),
 (
     4, '2025-03-21', DEFAULT, true, 28.00, 1, '2021', 'Usado',
     'Bajo eléctrico de cinco cuerdas con cuerpo de fresno y circuito activo.',
     7, 'Madera', '120x40x12', 'StingRay',
     'Music Man StingRay 5', 'EE.UU.', 'Intermedio'
 ),
 (
     6, '2025-03-17', DEFAULT, true, 15.00, 1, '2022', 'Nueva',
     'Caja de ritmos digital con secuenciador integrado y sonidos de bateria electronica.',
     2, 'Plastico y metal', '30x20x14', 'TR-8S',
     'Caja de Ritmos Roland TR-8S', 'Japon', 'Intermedio'
 );

-- ( 6, '2024-06-17', DEFAULT, true, 15.00, 1, '2022', 'Nueva',
--     'Caja de ritmos digital con secuenciador integrado y sonidos de bateria electronica.',
--     2, 'Plastico y metal', '30x20x40', 'TR-8S',
--     'Caja de Ritmos Roland TR-8S', 'Japon', 'Principiante')

-- insercion de datos en productosImagenes
INSERT INTO product_images
(`id`, `is_primary`, `product_id`, `url`)
VALUES
(DEFAULT, true, 1, 'https://res.cloudinary.com/dqc7cuyox/image/upload/fl_preserve_transparency/v1740764829/bateria_wv0uz9.jpg?_s=public-apps'),
(DEFAULT, true, 2, 'https://res.cloudinary.com/dqc7cuyox/image/upload/fl_preserve_transparency/v1740764931/teclado_dwzjio.jpg?_s=public-apps'),
(DEFAULT, true, 3, 'https://res.cloudinary.com/dqc7cuyox/image/upload/fl_preserve_transparency/v1740765016/guitarra_foto_1_err4rl.jpg?_s=public-apps'),
(DEFAULT, true, 4, 'https://res.cloudinary.com/dqc7cuyox/image/upload/fl_preserve_transparency/v1740765108/bateria2_z2hmc5.jpg?_s=public-apps'),
(DEFAULT, true, 5, 'https://res.cloudinary.com/dqc7cuyox/image/upload/fl_preserve_transparency/v1740765167/accesorio_imagen_1_oytuan.jpg?_s=public-apps'),
(DEFAULT, true, 6, 'https://res.cloudinary.com/dqc7cuyox/image/upload/fl_preserve_transparency/v1740765247/guitarra2_orh7lj.jpg?_s=public-apps'),
(DEFAULT, true, 7, 'https://res.cloudinary.com/dwzugg5gm/image/upload/v1743490093/Screenshot_2025-04-01_014717_ip0clr.png'),
(DEFAULT, true, 8, 'https://res.cloudinary.com/dqc7cuyox/image/upload/fl_preserve_transparency/v1740765641/piano_imagen_1_k196yx.jpg?_s=public-apps'),
(DEFAULT, true, 9, 'https://res.cloudinary.com/dwzugg5gm/image/upload/v1743490239/im21005174635dsc0316_ldwvxu.jpg'),
(DEFAULT, true, 10, 'https://res.cloudinary.com/dqc7cuyox/image/upload/fl_preserve_transparency/v1740765878/equipomejoras_aefxbm.jpg?_s=public-apps');


-- INSERT USERS
INSERT INTO users(creation_date, email, id, first_name, last_name, password, role_id)
VALUES
('2025-03-1', 'admin@email.com', DEFAULT, 'admin', 'admin', '$2a$10$6eRTbsXOKMvRgKQ2Xzo2Ee2QEGdjsYxTwzCKkBUpeSFIVXNe2UErW', '1'),
('2025-03-1', 'carla@email.com', DEFAULT, 'Carla', 'Perez', '$2a$10$6eRTbsXOKMvRgKQ2Xzo2Ee2QEGdjsYxTwzCKkBUpeSFIVXNe2UErW', '2'),
('2025-03-1', 'juan@email.com', DEFAULT, 'Juan', 'Perez', '$2a$10$6eRTbsXOKMvRgKQ2Xzo2Ee2QEGdjsYxTwzCKkBUpeSFIVXNe2UErW', '2'),
('2025-03-1', 'pedro@email.com', DEFAULT, 'Pedro', 'Perez', '$2a$10$6eRTbsXOKMvRgKQ2Xzo2Ee2QEGdjsYxTwzCKkBUpeSFIVXNe2UErW', '2'),
('2025-03-1', 'admin2@email.com', DEFAULT, 'admin2', 'admin2', '$2a$10$6eRTbsXOKMvRgKQ2Xzo2Ee2QEGdjsYxTwzCKkBUpeSFIVXNe2UErW', '1');

  -- admin123456
-- INSERT RESERVED PRODUCTS

INSERT INTO reservations (start_date, end_date, status, user_id, product_id, creation_date)
VALUES
    -- Reservas pasadas (PENDING, RETURNED, CANCELED)
    ('2024-03-10', '2024-03-15', 'PENDING', 2, 1, '2024-03-01'),
    ('2024-02-20', '2024-02-25', 'RETURNED', 2, 2, '2024-02-10'),
    ('2024-01-05', '2024-01-10', 'CANCELED', 3, 3, '2024-01-01'),
    ('2024-02-15', '2024-02-22', 'RETURNED', 3, 4, '2024-02-05'),
    ('2024-03-01', '2024-03-05', 'PENDING', 4, 5, '2024-02-25'),

    -- Reservas futuras (IN_PROGRESS, APPROVED)

    ('2025-04-17', '2025-04-20', 'IN_PROGRESS', 2, 1, NOW()),
    ('2025-03-23', '2025-03-25', 'APPROVED', 3, 1, NOW()),
    ('2025-04-23', '2025-04-25', 'APPROVED', 3, 1, NOW()),

    ('2025-04-17', '2025-04-18', 'IN_PROGRESS', 3, 2, NOW()),
    ('2025-04-26', '2025-05-15', 'APPROVED', 3, 2, NOW()),
    ('2025-04-20', '2025-04-27', 'APPROVED', 3, 2, NOW()),

    ('2025-04-18', '2025-04-22', 'APPROVED', 4, 3, NOW()),
    ('2025-04-23', '2025-04-30', 'APPROVED', 2, 4, NOW()),
    ('2025-03-10', '2025-03-12', 'IN_PROGRESS', 4,5, NOW()),

    ('2025-04-16', '2025-04-20', 'IN_PROGRESS', 2, 6, NOW()),
    ('2025-04-23', '2025-04-25', 'APPROVED', 3, 6, NOW()),

    ('2025-04-17', '2025-04-20', 'IN_PROGRESS', 4, 9, NOW()),
    ('2025-04-22', '2025-04-26', 'APPROVED', 3, 9, NOW()),
    ('2025-05-10', '2025-05-15', 'APPROVED', 2, 9, NOW());


