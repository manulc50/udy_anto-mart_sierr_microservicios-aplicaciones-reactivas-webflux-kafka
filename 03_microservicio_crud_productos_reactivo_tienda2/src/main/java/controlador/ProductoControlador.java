package controlador;

import java.net.URI;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.AllArgsConstructor;
import modelo.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import servicio.ProductoServicio;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping(ProductoControlador.BASE_API_URL)
public class ProductoControlador {
	public static final String BASE_API_URL = "/productos";
	
	private final ProductoServicio productoServicio;
	
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
	@GetMapping
	public Flux<Producto> obtenerProductos(@RequestParam(required = false) String categoria) {
		if(categoria != null && !categoria.isEmpty())
			return productoServicio.obtenerProductosPorCategoria(categoria);
		
		return productoServicio.obtenerProductos();
	}
	
	@GetMapping("/{codProducto}")
	public Mono<ResponseEntity<Producto>> obtenerProductoPorCodigo(@PathVariable int codProducto) {
		return productoServicio.obtenerProductoPorCodigo(codProducto)
				// Versión simplficada de la expresion "producto -> ResponseEntity.ok(producto)"
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public Mono<ResponseEntity<Void>> crearProducto(@RequestBody Producto producto) {
		return productoServicio.crearProducto(producto).log()
				.filter(result -> result)
				.map(result -> {
					URI uri = UriComponentsBuilder.fromUriString(BASE_API_URL + "/{codigo}").build(producto.getCodProducto());
					
					HttpHeaders headers = new HttpHeaders();
					headers.add(HttpHeaders.LOCATION, uri.toString());
					
					return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
				})
				.switchIfEmpty(Mono.just(ResponseEntity.badRequest().build()));
	}
	
	@PatchMapping("/{codProducto}")
	public Mono<ResponseEntity<Producto>> actualizarPrecioProducto(@PathVariable int codProducto, @RequestBody Map<String, Double> mapaPrecio) {
		return productoServicio.actualizarPrecioProducto(codProducto, mapaPrecio.get("precioUnitario"))
				.map(product -> new ResponseEntity<>(product, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@DeleteMapping("/{codProducto}")
	public Mono<ResponseEntity<Producto>> eliminarProduct(@PathVariable int codProducto) {
		return productoServicio.eliminarProducto(codProducto)
				// Versión simplficada de la expresion "producto -> ResponseEntity.ok(producto)"
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
}
