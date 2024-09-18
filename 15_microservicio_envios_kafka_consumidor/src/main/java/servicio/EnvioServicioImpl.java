package servicio;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import modelo.Envio;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import repositorio.EnvioRepositorio;

@RequiredArgsConstructor
@Service
public class EnvioServicioImpl implements EnvioServicio {
	private final EnvioRepositorio envioRepositorio;
	
	@Override
	public Flux<Envio> obtenerEnviosPendientes() {
		return envioRepositorio.obtenerPendientes()
				.delayElements(Duration.ofMillis(1500));
	}

	@Override
	public Mono<Envio> crearEnvio(Envio envio) {
		envio.setFecha(LocalDateTime.now());
		envio.setEstado("pendiente");
		
		return envioRepositorio.save(envio);
	}

}
