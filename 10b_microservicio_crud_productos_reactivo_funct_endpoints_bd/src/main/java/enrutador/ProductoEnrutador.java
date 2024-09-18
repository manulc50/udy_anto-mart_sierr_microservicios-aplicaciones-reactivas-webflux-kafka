package enrutador;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

import manejador.ProductoManejador;


@Configuration
public class ProductoEnrutador {
	public static final String BASE_API_URL = "/productos";
	
	@Bean
	public RouterFunction<ServerResponse> enrutador(ProductoManejador productoManejador) {
		// Versión simpplificada de la expresión "request -> productoManejador.obtenerProductos(request)"
		return RouterFunctions.route(GET(BASE_API_URL), productoManejador::obtenerProductos)
				.andRoute(GET(BASE_API_URL + "/{codProducto}"), productoManejador::obtenerProductoPorCodigo)
				.andRoute(POST(BASE_API_URL), productoManejador::crearProducto)
				.andRoute(PATCH(BASE_API_URL + "/{codProducto}"), productoManejador::actualizarPrecioProducto)
				.andRoute(DELETE(BASE_API_URL + "/{codProducto}"), productoManejador::eliminarProducto);
	}

}
