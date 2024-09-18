package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Elemento {
	private String nombre;
	private String categoria;
	private double precioUnitario;
	private String tienda;
}
