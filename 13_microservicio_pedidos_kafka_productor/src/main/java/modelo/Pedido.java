package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
	private String nombre;
	private int codProducto;
	private int unidades;
	private String direccion;
}
