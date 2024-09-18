package manejador;

import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;

import enrutador.ProductoEnrutador;
import lombok.AllArgsConstructor;
import modelo.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import servicio.ProductoServicio;

@AllArgsConstructor
@Component
public class ProductoManejador {
	private final ProductoServicio productoServicio;
	
	public Mono<ServerResponse> obtenerProductos(ServerRequest request) {
		Flux<Producto> fluxProductos = request.queryParam("categoria")
				// Versión simplificada de la expresión "categoria -> productoServicio.obtenerProductosPorCategoria(categoria)"
				.map(productoServicio::obtenerProductosPorCategoria)
				.orElse(productoServicio.obtenerProductos());
		
		return ServerResponse.ok().body(fluxProductos, Producto.class);
	}
	
	public Mono<ServerResponse> obtenerProductoPorCodigo(ServerRequest request) {
		int codProducto = Integer.parseInt(request.pathVariable("codProducto"));
		
		return productoServicio.obtenerProductoPorCodigo(codProducto).log()
				// Versión simplificada de la expresión "producto -> ServerResponse.ok().bodyValue(producto)"
				.flatMap(ServerResponse.ok()::bodyValue)
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	public Mono<ServerResponse> crearProducto(ServerRequest request) {
		return request.bodyToMono(Producto.class)
				.flatMap(producto -> productoServicio.crearProducto(producto)
						.filter(result -> result)
						.map(result -> UriComponentsBuilder.fromUriString(ProductoEnrutador.BASE_API_URL + "/{codigo}").build(producto.getCodProducto()))
						.flatMap(uri -> ServerResponse.created(uri).build())
						.switchIfEmpty(ServerResponse.badRequest().build()));		
	}
	
	public Mono<ServerResponse> actualizarPrecioProducto(ServerRequest request) {
		int codProducto = Integer.parseInt(request.pathVariable("codProducto"));
		
		return request.bodyToMono(new ParameterizedTypeReference<Map<String, Double>>() {})
				.flatMap(mapaPrecio -> productoServicio.actualizarPrecioProducto(codProducto, mapaPrecio.get("precioUnitario")))
				// Versión simplificada de la expresión "producto -> ServerResponse.ok().bodyValue(producto)"
				.flatMap(ServerResponse.ok()::bodyValue)
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	public Mono<ServerResponse> eliminarProducto(ServerRequest request) {
		int codProducto = Integer.parseInt(request.pathVariable("codProducto"));
		
		return productoServicio.eliminarProducto(codProducto)
				.flatMap(producto -> ServerResponse.ok().body(Mono.just(producto), Producto.class))
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
}
