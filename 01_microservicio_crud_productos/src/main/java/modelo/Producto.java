package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {
	private int codProducto;
	private String nombre;
	private String categoria;
	private double precioUnitario;
	private int stock;
}
