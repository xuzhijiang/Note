package springboot.security.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecureApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SecureApplication.class, args);
	}
}
