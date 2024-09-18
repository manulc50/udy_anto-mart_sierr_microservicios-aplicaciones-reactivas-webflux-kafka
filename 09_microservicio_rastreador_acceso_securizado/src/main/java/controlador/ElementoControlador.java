package controlador;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import modelo.Elemento;
import reactor.core.publisher.Flux;
import servicio.ElementoServicio;

// Nota: Este microservicios se comunica con los microservicios "08_microservicio_crud_productos_reactivo_securizado"
// y "03_microservicio_crud_productos_reactivo_tienda2".

@RequiredArgsConstructor
@RestController
public class ElementoControlador {
	private final ElementoServicio elementoServicio;
	
	/* Nota: Si envíamos un ResponseEntity<Mono<...>> o un ResponseEntity<Flux<...>> a un cliente, el código de la respuesta y las cabeceras
	   se conocen y se obtienen al instante de forma síncrona. Sin embargo, el cuerpo de la respuesta, que es un flujo reactivo, se obtiene
	   de forma asíncrona.
	   Si envíamos un Mono<ResponseEntity<...>> al cliente, el código de la respuesta, las cabeceras y el cuerpo de la respuesta se obtienen
	   de forma asíncrona.
	*/
	
	/* Nota: Si se manda una petición a este endpoint con la cabecera "Accept: application/json", internamente se invoca al método
	   "collectList" de la clase "Flux" para devolver todos los elementos emitidos por el flujo reactivo Flux como un array en
	   formato Json(se espera a que se emita el último elemento para devolverlos todos en un array de Json).
	   Si se manda una petición a este endpoint con la cabecera "text/event-stream"(SSE - Server Side Events), cada elemento emitido
	   por el flujo reactivo se devuelve con el formato "data: " + json.
	   Si se manda una petición a este endpoint con la cabecera "application/x-ndjson", cada elemento emitido por el flujo reactivo
	   se devuelve como un Json independiente.
	*/
	@GetMapping("/elementos")
	public Flux<Elemento> obtenerElementosPorPrecioMax(@RequestParam double precioMaximo) {
		return elementoServicio.obtenerElementosPorPrecioMax(precioMaximo);
	}
}
