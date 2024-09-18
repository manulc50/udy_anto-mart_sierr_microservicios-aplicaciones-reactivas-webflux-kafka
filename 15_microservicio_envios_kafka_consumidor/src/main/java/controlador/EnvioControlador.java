package controlador;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import modelo.Envio;
import reactor.core.publisher.Mono;
import servicio.EnvioServicio;

@RequiredArgsConstructor
@RestController
public class EnvioControlador {
	private final EnvioServicio envioServicio;
	
	@GetMapping("envios")
	public Mono<ResponseEntity<List<Envio>>> obtenerEnviosPendientes() {
		return envioServicio.obtenerEnviosPendientes()
				.collectList()
				// Versión simplificada de la expresión "listaEnvios -> ResponseEntity.ok(listaEnvios)"
				.map(ResponseEntity::ok);
				
	}
}
