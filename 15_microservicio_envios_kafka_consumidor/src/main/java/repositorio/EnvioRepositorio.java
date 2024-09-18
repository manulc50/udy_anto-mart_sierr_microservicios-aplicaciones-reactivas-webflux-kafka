package repositorio;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import modelo.Envio;
import reactor.core.publisher.Flux;

public interface EnvioRepositorio extends ReactiveCrudRepository<Envio, Integer> {
	
	@Query("select * from envios where estado='pendiente'")
	Flux<Envio> obtenerPendientes();

}
