package servicio;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import modelo.Elemento;
import reactor.core.publisher.Flux;

@Service
public class ElementoServicioImpl implements ElementoServicio {
	private static final String URL_TIENDA1 = "http://localhost:8000/productos";
	private static final String URL_TIENDA2 = "http://localhost:9000/productos";
	
	private final String usuario;
	private final String pwd;
	
	public ElementoServicioImpl(@Value("${usuario}") String usuario,
			@Value("${pwd}") String pwd) {
		this.usuario = usuario;
		this.pwd = pwd;
	}

	@Override
	public Flux<Elemento> obtenerElementosPorPrecioMax(double precioMaximo) {
		Flux<Elemento> fluxElementosTienda1 = obtenerElementosPorTienda(URL_TIENDA1, "Tienda 1");
		Flux<Elemento> fluxElementosTienda2 = obtenerElementosPorTienda(URL_TIENDA2, "Tienda 2");
		
		// En este caso, se ha decidido usar este método "mergeDelayError" en vez del método "merge" porque, como el
		// microservicio de la tienda 1 ahora está securizado, aunque se envíen a este microservicio credenciales inválidas
		// y se produzca un error o excepción, aún queremos recibir los elementos del microservico de la tienda 2 que no esta securizado.
		return Flux.mergeDelayError(1, fluxElementosTienda1, fluxElementosTienda2)
				.filter(elemento -> elemento.getPrecioUnitario() <= precioMaximo);
	}
	
	private Flux<Elemento> obtenerElementosPorTienda(String url, String tienda) {
		WebClient cliente = WebClient.create(url);
		
		return cliente.get()
				// 3 formas equivalentes de pasar las credenciales mediante Autenticación Básica
				//.header(HttpHeaders.AUTHORIZATION, "Basic " + obtenerCredencialesBase64(usuario, pwd))
				//.headers(headers -> headers.setBasicAuth(obtenerCredencialesBase64(usuario, pwd)))
				.headers(headers -> headers.setBasicAuth(usuario, pwd))
				.retrieve()
				.bodyToFlux(Elemento.class)
				.doOnNext(elemento -> elemento.setTienda(tienda));
	}
	
	private String obtenerCredencialesBase64(String usuario, String password) {
		return Base64.getEncoder().encodeToString((usuario + ":" + password).getBytes());
	}

}
