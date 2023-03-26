package com.aussieogame.backend;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles(profiles = "test")
class AussieGameBootstrapTest {

	@Test
	@DisplayName("Full application context loads properly")
	void contextLoads(ApplicationContext context) {
		assertNotNull(context);
	}

}