package configuracion;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import modelo.Envio;

// Nota: Debido a que tenemos que configurar las propiedades "USE_TYPE_INFO_HEADERS" y "VALUE_DEFAULT_TYPE", no podemos
// usar el archivo de propiedades del proyecto para establecer las propiedades de Kafka, es decir, tenemos que usar
// una clase de Configuración de Spring como ésta.

@Configuration
public class KafkaConfig {
	
	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Envio>> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, Envio> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		
		return factory;
	}
	
	private ConsumerFactory<String, Envio> consumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		
		/* Por defecto, cuando un productor envía un mensaje en formato Json de un tipo de dato, ese tipo de dato
		   se establece en una cabecera para el proceso de deserialización en los consumidores. Por lo tanto, si ese
		   tipo de dato no se encuentra en algún consumidor, se produce un error o excepción en ese condumidor.
		   En nuestro caso, el productor envía un mensaje en formato Json del tipo "Pedido" pero este consumidor espera
		   la deserialización al tipo "Envio" y, como no coinciden, tenemos que configurar estas dos siguientes propiedades
		   de esta forma:
		*/
		
		// Propiedad para que se ignore en la deserialización el tipo de dato establecido en la cabecera por el productor.
		props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
		
		// Propiedad para que que la deserialización de lleve a cabo sobre un objeto de tipo "Envio".
		props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Envio.class);
		
		return new DefaultKafkaConsumerFactory<>(props);
	}

}
