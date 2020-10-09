package com.xgame.restservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.xgame.data")
@EntityScan("com.xgame.data.entities")
@ComponentScan({"com.xgame.restservice", "com.xgame.service"})
public class XgameApplication {

	public static void main(String[] args) {
		SpringApplication.run(XgameApplication.class, args);
	}

}
