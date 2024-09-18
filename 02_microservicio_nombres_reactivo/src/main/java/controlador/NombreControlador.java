package controlador;

import java.time.Duration;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class NombreControlador {

	@GetMapping("nombres")
	public Flux<String> getNombres() {
		List<String> nombres = List.of("one", "two", "three", "four");
		
		return Flux.fromIterable(nombres)
				.delayElements(Duration.ofSeconds(2));
	}
}
