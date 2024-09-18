package modelo;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "envios")
public class Envio {
	
	@Id
	private int idEnvio;
	
	// Esta anotación nos permite mapear campos de un Json a propiedades de Java durante la deserialización.
	@JsonAlias("nombre")
	private String pedido;
	
	private LocalDateTime fecha;
	private String direccion;
	private String estado;
}
