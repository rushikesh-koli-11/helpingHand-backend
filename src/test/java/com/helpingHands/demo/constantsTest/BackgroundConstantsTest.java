package com.helpingHands.demo.constantsTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.helpingHands.demo.constants.BackgroundConstants;

public class BackgroundConstantsTest {

	@Test
	void testBackgroundConstantsValues() {
		assertNotNull(BackgroundConstants.BACKGROUND_NOT_FOUND);
		assertEquals("Background not found with id: ", BackgroundConstants.BACKGROUND_NOT_FOUND);
	}

	@Test
	void testBackgroundConstantsClassCannotBeInstantiated() {
		assertThrows(UnsupportedOperationException.class, () -> {
			new BackgroundConstants();
		});
	}
}
