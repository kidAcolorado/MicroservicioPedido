create database pedidosbdd;
use pedidosbdd;

CREATE TABLE pedidos (
    id INT PRIMARY KEY NOT NULL auto_increment,
    codigo int,
    unidades INT,
    total DOUBLE,
    fecha timestamp
);


-- Ejemplo 1
INSERT INTO pedidos (codigo, unidades, total, fecha)
VALUES ('1001', 5, 79.95, NOW());

-- Ejemplo 2
INSERT INTO pedidos (codigo, unidades, total, fecha)
VALUES ('1002', 3, 239.97, NOW());

-- Ejemplo 3
INSERT INTO pedidos (codigo, unidades, total, fecha)
VALUES ('1003', 2, 59.9, NOW());

-- Ejemplo 4
INSERT INTO pedidos (codigo, unidades, total, fecha)
VALUES ('1004', 4, 1599.96, NOW());

-- Ejemplo 5
INSERT INTO pedidos (codigo, unidades, total, fecha)
VALUES ('1005', 7, 6299.93, NOW());

-- Ejemplo 6
INSERT INTO pedidos (codigo, unidades, total, fecha)
VALUES ('1006', 2, 1399.98, NOW());

-- Ejemplo 7
INSERT INTO pedidos (codigo, unidades, total, fecha)
VALUES ('1007', 5, 99.95, NOW());

-- Ejemplo 8
INSERT INTO pedidos (codigo, unidades, total, fecha)
VALUES ('1008', 1, 59.95, NOW());

-- Ejemplo 9
INSERT INTO pedidos (codigo, unidades, total, fecha)
VALUES ('1009', 6, 299.94, NOW());

-- Ejemplo 10
INSERT INTO pedidos (codigo, unidades, total, fecha)
VALUES ('1010', 4, 1199.96, NOW());