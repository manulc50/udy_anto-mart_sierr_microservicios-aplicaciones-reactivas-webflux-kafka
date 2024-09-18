package servicio;

import modelo.Envio;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EnvioServicio {
	Flux<Envio> obtenerEnviosPendientes();
	Mono<Envio> crearEnvio(Envio envio);
}
