package modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
	Nota: En este caso, como los ids no se autogeneran y se pasan manualmente desde el cuerpo de la petición http, para que el método "save" del repositorio
	pueda saber si se trata de una nueva inserción o de una actualización, utilizamos la propiedad booleana "esNuevo" junto con la implemenación de la
	interfaz "Persistable". De esta forma, cuando el método "isNew" de esta interfaz devuelva un true, el método "save" realizará una nueva inserción y
	,en caso contrario, realizará una actualización.
	
	Otra opción sería usar la anotación @Versión sobre una propiedad entera(Por ejemplo, @Versión private int version). Esta propiedad debe estar relacionada
	con un campo de la tabla correspondiente en la base de datos. De esta forma, si el valor de esta propiedad es 0, el método "save" realizará una nueva inserción y
	,en caso contrario, realizará una actualización.
*/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("productos")
public class Producto implements Persistable<Integer> {
	
	@Id
	private int codProducto;
	
	private String nombre;
	private String categoria;
	private double precioUnitario;
	private int stock;
	
	// Usamos la anotación @Transient porque no queremos relacionar esta propiedad con la base de datos.
	@Transient
	// Usamos la anotación @JsonIgnore porque no queremos serializar/deserializar esta propiedad a un Json.
	@JsonIgnore
	private boolean esNuevo; // valor por defecto false
	
	// Usamos la anotación @JsonIgnore porque no queremos serializar este método a un Json.
	@JsonIgnore
	@Override
	public Integer getId() {
		return codProducto;
	}
	
	// Usamos la anotación @JsonIgnore porque no queremos serializar este método a un Json.
	@JsonIgnore
	@Override
	public boolean isNew() {
		return esNuevo;
	}
}
