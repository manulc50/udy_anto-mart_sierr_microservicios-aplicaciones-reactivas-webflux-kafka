package runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import modelo.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TestRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		WebClient cliente = WebClient.create("http://localhost:8000/productos");
		
		//obtenerProductos(cliente);
		//crearProducto1(cliente);
		//crearProducto2(cliente);
		//obtenerProductoPorCodigo(cliente, 1011);
		eliminarProducto(cliente, 101);
	}
	
	private void obtenerProductos(WebClient cliente) {
		Flux<Producto> fluxProductos = cliente.get()
				.retrieve()
				.bodyToFlux(Producto.class);
		
		// Versión simplificada de la expresión "producto -> System.out.println(producto)"
		fluxProductos.subscribe(System.out::println);
	}

	private void crearProducto1(WebClient cliente) {
		cliente.post()
			.body(Mono.just(new Producto(200, "Producto Prueba", "Categoría Prueba", 5.0, 20)), Producto.class)
			.retrieve()
			.bodyToMono(Void.class)
			.subscribe(null, null, () -> System.out.println("Se ha dado de alta el producto"));
	}
	
	private void crearProducto2(WebClient cliente) {
		Mono<ResponseEntity<Void>> monoRespuesta = cliente.post()
				.body(Mono.just(new Producto(200, "Producto Prueba", "Categoría Prueba", 5.0, 20)), Producto.class)
				.retrieve()
				.toEntity(Void.class);
		
		monoRespuesta.subscribe(respuesta -> {
			 String cabeceraLocation = respuesta.getHeaders().get(HttpHeaders.LOCATION).get(0);
			 System.out.println("Location: "  + cabeceraLocation);
		});
	}
	
	private void obtenerProductoPorCodigo(WebClient cliente, int codProducto) {
		cliente.get()
			.uri("/{codigo}", codProducto)
			.retrieve()
			// Versión simplificada de la expresión "estado -> estado.is4xxClientError()"
			.onStatus(HttpStatusCode::is4xxClientError, respuestaCliente -> manejarErrores4xx(respuestaCliente, codProducto))
			.bodyToMono(Producto.class)
			// Versión simplificada de la expresión "producto -> System.out.println(producto)"
			.subscribe(System.out::println, error -> System.out.println(error.getMessage()));
	}
	
	private void eliminarProducto(WebClient cliente, int codProducto) {
		cliente.delete()
			.uri("/{codigo}", codProducto)
			.retrieve()
			// Versión simplificada de la expresión "estado -> estado.is4xxClientError()"
			.onStatus(HttpStatusCode::is4xxClientError, respuestaCliente -> manejarErrores4xx(respuestaCliente, codProducto))
			.bodyToMono(Producto.class)
			// Versión simplificada de la expresión "producto -> System.out.println(producto)"
			.subscribe(producto -> System.out.println("Producto eliminado: " + producto),
					error -> System.out.println(error.getMessage()));
	}
	
	private Mono<Throwable> manejarErrores4xx(ClientResponse respuestaCliente, int codProducto) {
		if(respuestaCliente.statusCode().equals(HttpStatus.NOT_FOUND))
			return Mono.error(new RuntimeException("Producto con código " + codProducto + " no encontrado"));
		
		return respuestaCliente.bodyToMono(String.class)
				.flatMap(respuesta -> Mono.error(new RuntimeException(respuesta)));
	}
}
