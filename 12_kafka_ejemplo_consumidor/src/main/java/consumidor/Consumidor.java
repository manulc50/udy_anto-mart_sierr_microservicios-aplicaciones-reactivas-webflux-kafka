package consumidor;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class Consumidor {
	private KafkaConsumer<String, String> consumidor;
	
	public Consumidor() {
		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "grupoA");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
	
		consumidor = new KafkaConsumer<>(props);
	}
	
	public void suscribir(String topico) {
		consumidor.subscribe(List.of(topico));
		
		while(true) {
			// Cada 200ms mira si hay mensajes que consumir en el tópico indicado.
			ConsumerRecords<String, String> registros = consumidor.poll(Duration.ofMillis(200));
			
			for(ConsumerRecord<String, String> registro: registros)
				System.out.println(registro.value());
		}
	}
}
