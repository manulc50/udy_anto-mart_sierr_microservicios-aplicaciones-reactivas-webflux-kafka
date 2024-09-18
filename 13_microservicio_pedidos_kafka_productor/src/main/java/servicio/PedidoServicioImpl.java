package servicio;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import modelo.Pedido;

@Service
public class PedidoServicioImpl implements PedidoServicio {
	private final KafkaTemplate<String, Pedido> kafkaTemplate;
	private final String topico;
	
	public PedidoServicioImpl(KafkaTemplate<String, Pedido> kafkaTemplate,
			@Value("${topico}") String topico) {
		this.kafkaTemplate = kafkaTemplate;
		this.topico = topico;
	}

	@Override
	public void registrarPedido(Pedido pedido) {
		CompletableFuture<SendResult<String, Pedido>> future = kafkaTemplate.send(topico, pedido);
		
		future.whenCompleteAsync((result, error) -> {
			if(error != null) {
				throw new RuntimeException("Error en el envío del pedido " + pedido.getNombre() +
						" al tópico " + topico);
			}
			
			System.out.println("Se ha registrado el pedido " + result.getProducerRecord().value().getNombre() +
					" en el tópico " + result.getRecordMetadata().topic());
		});
	}

}
