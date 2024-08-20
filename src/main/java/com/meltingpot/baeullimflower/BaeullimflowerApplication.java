package com.meltingpot.baeullimflower;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class BaeullimflowerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaeullimflowerApplication.class, args);
	}

}
