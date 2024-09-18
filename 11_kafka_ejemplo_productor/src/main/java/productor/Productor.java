package productor;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;


public class Productor {
	private KafkaProducer<String, String> productor;
	
	public Productor() {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		
		productor = new KafkaProducer<>(props);
	}
	
	public void enviar(String topico, String mensaje) {
		productor.send(new ProducerRecord<String, String>(topico, mensaje));
	}
	
	public void cerrar() {
		productor.close();
	}
}
