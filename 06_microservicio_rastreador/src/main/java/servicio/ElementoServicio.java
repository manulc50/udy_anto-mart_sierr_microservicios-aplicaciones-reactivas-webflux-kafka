package servicio;

import modelo.Elemento;
import reactor.core.publisher.Flux;

public interface ElementoServicio {
	Flux<Elemento> obtenerElementosPorPrecioMax(double precioMaximo);
}
