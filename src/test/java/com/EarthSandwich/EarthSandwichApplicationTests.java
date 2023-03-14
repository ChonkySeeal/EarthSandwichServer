package com.EarthSandwich;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import ai.djl.ModelException;
import ai.djl.translate.TranslateException;

@TestPropertySource(locations = "classpath:/test.properties")
@SpringBootTest
class EarthSandwichApplicationTests {
	private static final Logger logger = LoggerFactory.getLogger(EarthSandwichApplicationTests.class);

	@Test
	void runTest() throws IOException, ModelException, TranslateException {

	}

}
