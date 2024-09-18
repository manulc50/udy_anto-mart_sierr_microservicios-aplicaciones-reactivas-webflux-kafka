package configuracion;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

import controlador.ProductoControlador;

@EnableWebFluxSecurity
@Configuration
public class SpringConfig {
	
	@Bean
	public ReactiveUserDetailsService reactiveUserDetailsService() {
		List<UserDetails> users = Arrays.asList(
				User.withUsername("user1")
					.password("{noop}user1")
					.roles("USER")
					.build(),
				User.withUsername("admin")
					.password("{noop}admin")
					.roles("ADMIN")
					.build(),
				User.withUsername("user2")
					.password("{noop}user2")
					.roles("OPERATOR")
					.build()
				);
		
		return new MapReactiveUserDetailsService(users);
	}
	
	@Bean
	public SecurityWebFilterChain filter(ServerHttpSecurity http) {
		return http.csrf(csrf -> csrf.disable())
			.authorizeExchange(auth ->
				auth.pathMatchers(HttpMethod.POST, ProductoControlador.BASE_API_URL).hasRole("ADMIN")
					.pathMatchers(HttpMethod.DELETE, ProductoControlador.BASE_API_URL + "/*").hasAnyRole("ADMIN", "OPERATOR")
					.pathMatchers(HttpMethod.GET, ProductoControlador.BASE_API_URL + "/*").permitAll()
					.anyExchange().authenticated()
			)
			.httpBasic(Customizer.withDefaults())
			.build();
	}

}
