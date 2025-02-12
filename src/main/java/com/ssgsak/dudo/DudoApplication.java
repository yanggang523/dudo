package com.ssgsak.dudo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DudoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DudoApplication.class, args);
	}


}
