package repositorio;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import modelo.Producto;
import reactor.core.publisher.Flux;

public interface ProductoRepositorio extends ReactiveCrudRepository<Producto, Integer> {
	Flux<Producto> findByCategoria(String categoria);
}
