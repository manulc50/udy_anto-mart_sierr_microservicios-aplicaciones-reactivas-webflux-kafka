package enrutador;

import java.time.Duration;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;

@Configuration
public class NombreEnrutador {

	@Bean
	public RouterFunction<ServerResponse> enrutador() {
		return RouterFunctions.route(RequestPredicates.GET("nombres"),
				request -> {
					List<String> nombres = List.of("one", "two", "three", "four");
					
					return ServerResponse.ok().body(Flux.fromIterable(nombres)
							.delayElements(Duration.ofSeconds(2)), String.class);
				});
	}
}
