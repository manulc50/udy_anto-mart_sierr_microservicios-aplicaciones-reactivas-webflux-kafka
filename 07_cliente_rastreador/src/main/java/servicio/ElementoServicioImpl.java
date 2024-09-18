package servicio;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import modelo.Elemento;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class ElementoServicioImpl implements ElementoServicio {
	private final WebClient cliente;

	@Override
	public Flux<Elemento> obtenerElementosPorPrecioMax(double precioMaximo) {
		return cliente.get()
				.uri(uriBuilder -> uriBuilder.queryParam("precioMaximo", precioMaximo).build())
				.retrieve()
				.bodyToFlux(Elemento.class);
	}

}
