DROP TABLE IF EXISTS productos; 

CREATE TABLE productos (
	cod_producto INT PRIMARY KEY,
	nombre VARCHAR(30) NOT NULL,
	categoria VARCHAR(30) NOT NULL,
	precio_unitario DOUBLE NOT NULL,
	stock INT NOT NULL
);