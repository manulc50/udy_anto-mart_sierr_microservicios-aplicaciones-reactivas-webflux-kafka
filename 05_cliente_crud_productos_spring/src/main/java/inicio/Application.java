package inicio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "runner")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
