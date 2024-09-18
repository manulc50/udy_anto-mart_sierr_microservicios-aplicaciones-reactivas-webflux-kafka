package inicio;

import consumidor.Consumidor;

public class Principal {

	public static void main(String... args) {
		Consumidor consumidor = new Consumidor();
		
		consumidor.suscribir("topicTest");
	}
}
