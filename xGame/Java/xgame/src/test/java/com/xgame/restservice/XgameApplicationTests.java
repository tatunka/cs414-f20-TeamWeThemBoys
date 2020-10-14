package com.xgame.restservice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.xgame.restservice.controller.ChessMatchController;
import com.xgame.restservice.controller.TestController;

@SpringBootTest
class XgameApplicationTests {

	@Autowired
	TestController testController;
	@Autowired
	ChessMatchController matchController;
	
	@Test
	void contextLoads() {
		assertThat(testController).isNotNull();
		assertThat(matchController).isNotNull();
		
		//https://spring.io/guides/gs/testing-web/
	}

}
