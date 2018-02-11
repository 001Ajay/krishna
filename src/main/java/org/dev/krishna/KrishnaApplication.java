package org.dev.krishna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.dev.krishna")
public class KrishnaApplication {

	public static void main(String[] args) {
		SpringApplication.run(KrishnaApplication.class, args);
	}
}
