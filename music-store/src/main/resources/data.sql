--
INSERT INTO roles (name) VALUES ('admin'), ('user');

-- insercion de datos en categorias.
INSERT INTO categories
(creation_date, id, image_url, name)
VALUES
('2025-03-1', DEFAULT, 'url.com', 'Guitarras'),
('2025-03-1', DEFAULT, 'url.com', 'Baterias'),
('2025-03-1', DEFAULT, 'url.com', 'Pianos'),
('2025-03-1', DEFAULT, 'url.com', 'Bajos'),
('2025-03-1', DEFAULT, 'url.com', 'Electricos'),
('2025-03-1', DEFAULT, 'url.com', 'Accesorios');

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
(DEFAULT, 'Steinway & Sons', '2025-03-1');


-- insercion de datos en productos
INSERT INTO products(`category_id`, `creation_date`, `id`,`is_available`, `price_per_hour`, `stock_quantity`, `launch_year`,
`product_condition`, `description`, `brand_id`, `material`, `product_size`, `model`, `name`, `origin`, `recommended_use`)
VALUES
( 2, '2024-06-17', DEFAULT, true, 35.99, 5, '2022', 'Nueva','Batería acústica profesional con gran resonancia y durabilidad.',
1, 'Metal y madera', '140 x 90', 'EXX725S', 'Batería Pearl Export', 'Japón', 'Profesional y estudio'),
( 3, '2024-06-17', DEFAULT, true, 28.99, 3, '2021', 'Nuevo', 'Teclado digital con 88 teclas sensibles al tacto y múltiples funciones integradas.',
2, 'Plástico y metal', '130 x 35', 'FP-30X', 'Teclado Roland FP-30X', 'EE.UU.', 'Profesional'),
( 1, '2024-06-17', DEFAULT, true, 22.50, 4, '2022', 'Seminueva', 'Guitarra acústica de seis cuerdas con gran resonancia y acabado de madera natural.',
3, 'Madera', '100 x 40', 'CD-60', 'Guitarra Fender CD-60', 'México', 'Principiantes y conciertos acústicos'),
( 2, '2024-06-17', DEFAULT, true, 40.00, 3, '2023', 'Nueva', 'Batería acústica de 5 piezas con platillos incluidos, ideal para estudios y presentaciones en vivo.',
4, 'Madera y metal', '150 x 90', 'Imperialstar', 'Batería Tama Imperialstar', 'Japón', 'Profesional y estudio'),
( 6, '2024-06-17', DEFAULT, true, 15.99, 10, '2022', 'Nuevo', 'Audífonos profesionales con cancelación de ruido y sonido envolvente.',
 5, 'Plástico y metal', '18 x 15', 'WH-1000XM4', 'Audífonos Sony WH-1000XM4', 'Japón', 'Estudio de grabación y uso diario'),
 (
     1, '2024-06-17', DEFAULT, true, 30.00, 5, '2023', 'Nueva',
     'Guitarra eléctrica de seis cuerdas con cuerpo de caoba y pastillas humbucker.',
     6, 'Madera', '102 x 35', 'Les Paul Standard 50s',
     'Guitarra Gibson Les Paul', 'EE.UU.', 'Profesional y conciertos en vivo'
 ),
 (
     4, '2024-06-17', DEFAULT, true, 10.50, 8, '2022', 'Nueva',
     'Armónica diatónica de 10 agujeros afinada en Do (C), ideal para blues y folk.',
     7, 'Metal y plástico', '10 x 3', 'Marine Band 1896',
     'Armónica Hohner Marine Band', 'Alemania', 'Música blues, folk y rock'
 ),
 (
     3, '2024-06-17', DEFAULT, true, 50.00, 2, '2023', 'Nuevo',
     'Piano de cola con 88 teclas, sonido envolvente y sistema de martillos de alta precisión.',
     8, 'Madera y metal', '150 x 180', 'Model D',
     'Piano Steinway & Sons Model D', 'Alemania', 'Conciertos y estudio profesional'
 ),
 (
     4, '2024-06-17', DEFAULT, true, 20.00, 4, '2021', 'Seminuevo',
     'Violín acústico con acabado en barniz brillante, ideal para principiantes y músicos avanzados.',
     1, 'Madera de arce y abeto', '59 x 21', '1730',
     'Violín Stradivarius 1730', 'Italia', 'Estudio y presentaciones en vivo'
 ),
 (
     6, '2024-06-17', DEFAULT, true, 15.00, 6, '2022', 'Nueva',
     'Caja de ritmos digital con secuenciador integrado y sonidos de batería electrónica.',
     2, 'Plástico y metal', '30 x 20', 'TR-8S',
     'Caja de Ritmos Roland TR-8S', 'Japón', 'Producción musical y presentaciones en vivo'
 );


-- insercion de datos en productosImagenes
INSERT INTO product_images
(`id`, `is_primary`, `product_id`, `url`)
VALUES
(DEFAULT, true, 1, 'https://asset.cloudinary.com/dqc7cuyox/1e715f125397e13495ed6b8b9e0d13e4'),
(DEFAULT, true, 2, 'https://asset.cloudinary.com/dqc7cuyox/0e48c2194c1eb472e3a267e6b7890fc1'),
(DEFAULT, true, 3, 'https://asset.cloudinary.com/dqc7cuyox/be45206b994aff51159a00c515c2065a'),
(DEFAULT, true, 4, 'https://asset.cloudinary.com/dqc7cuyox/b39e6eead571c9589168f917057ed438'),
(DEFAULT, true, 5, 'https://asset.cloudinary.com/dqc7cuyox/9b7768c1d76947219de7895296f9dce4'),
(DEFAULT, true, 6, 'https://asset.cloudinary.com/dqc7cuyox/f15568e28c5763727a09f606d414d10f'),
(DEFAULT, true, 7, 'https://asset.cloudinary.com/dqc7cuyox/1467078b51f8f6da2a51313aec15b70b'),
(DEFAULT, true, 8, 'https://asset.cloudinary.com/dqc7cuyox/809acca7c39a21f2757c256179f8ed55'),
(DEFAULT, true, 9, 'https://asset.cloudinary.com/dqc7cuyox/b8a9a7fb02c7672d6ea864428bf19790'),
(DEFAULT, true, 10, 'https://asset.cloudinary.com/dqc7cuyox/5ed31526cbfa27e360af3cdc4259f5af');