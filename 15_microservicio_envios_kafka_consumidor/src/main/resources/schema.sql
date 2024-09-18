DROP TABLE IF EXISTS envios; 

CREATE TABLE envios (
	id_envio INT NOT NULL AUTO_INCREMENT,
	pedido VARCHAR(50) NOT NULL,
	fecha DATETIME NOT NULL,
	direccion VARCHAR(75) NOT NULL,
	estado varchar(30) NOT NULL,
	PRIMARY KEY (id_envio)
);