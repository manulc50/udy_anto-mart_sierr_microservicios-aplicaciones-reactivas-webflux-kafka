package consumidor;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import modelo.Envio;
import servicio.EnvioServicio;

@AllArgsConstructor
@Component
public class EnvioConsumidor {
	private final EnvioServicio envioServicio;
	
	@KafkaListener(topics = "${topico}", groupId = "${grupo.id}")
	public void gestionEnvio(Envio envio) {
		envioServicio.crearEnvio(envio)
			.subscribe(envioRegistrado -> System.out.println("Envio registrado: " + envioRegistrado));
	}
}
