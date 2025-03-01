-- insercion de datos en categorias.
INSERT INTO categories
(creation_date,
id,
image_url,
name)
VALUES
('2024-06-17',
DEFAULT,
'url.com',
'Guitarras');

-- insercion de datos en productos
INSERT INTO `soundkeeper_prod`.`products`
(`category_id`,
`creation_date`,
`id`,
`is_available`,
`price_per_hour`,
`stock_quantity`,
`anio_lanzamiento`,
`condicion`,
`description`,
`marca`,
`material`,
`medidas`,
`modelo`,
`name`,
`origen`,
`uso_recomendado`)
VALUES
(1,
'2024-06-17',
DEFAULT,
true,
24.45,
2,
'2023',
'Seminueva',
'Es una guitarra clasica',
'Yamaha',
'Madera',
'120 x 50',
'12030-0',
'Guitarra 0-12 Vendo',
'Vietnam',
'aficionado');

-- insercion de datos en productosImagenes
INSERT INTO `soundkeeper_prod`.`product_images`
(`id`,
`is_primary`,
`product_id`,
`url`)
VALUES
(DEFAULT,
true,
1,
'url.com');
