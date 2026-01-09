package com.helpingHands.demo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HelpingHandsApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
    void testMainMethod() {
        // Ensure the main method runs without throwing exceptions
        assertDoesNotThrow(() -> HelpingHandsApplication.main(new String[] {}));
    }

}
