package servicio;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import modelo.Elemento;
import reactor.core.publisher.Flux;

@Service
public class ElementoServicioImpl implements ElementoServicio {
	private static final String URL_TIENDA1 = "http://localhost:8000/productos";
	private static final String URL_TIENDA2 = "http://localhost:9000/productos";

	@Override
	public Flux<Elemento> obtenerElementosPorPrecioMax(double precioMaximo) {
		Flux<Elemento> fluxElementosTienda1 = obtenerElementosPorTienda(URL_TIENDA1, "Tienda 1");
		Flux<Elemento> fluxElementosTienda2 = obtenerElementosPorTienda(URL_TIENDA2, "Tienda 2");
		
		return Flux.merge(fluxElementosTienda1, fluxElementosTienda2)
				.filter(elemento -> elemento.getPrecioUnitario() <= precioMaximo);
	}
	
	private Flux<Elemento> obtenerElementosPorTienda(String url, String tienda) {
		WebClient cliente = WebClient.create(url);
		
		return cliente.get()
				.retrieve()
				.bodyToFlux(Elemento.class)
				.doOnNext(elemento -> elemento.setTienda(tienda));
	}

}
