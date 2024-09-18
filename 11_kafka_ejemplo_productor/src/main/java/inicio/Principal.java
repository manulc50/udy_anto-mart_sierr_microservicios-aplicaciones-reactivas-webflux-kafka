package inicio;

import java.time.LocalDateTime;

import productor.Productor;

public class Principal {
	
	public static void main(String[] args) {
		Productor productor = new Productor();
		
		for(int i = 1; i <= 10; i++) {
			productor.enviar("topicTest", "Mensaje " + i + " generado a las " + LocalDateTime.now() + " para el tópico \"topicTest\"");
			
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		productor.cerrar();
	}
}
