package com.helpingHands.demo.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ResourceNotFoundExceptionTest {

    @Test
    void testExceptionMessage() {
        String message = "Resource not found";
        ResourceNotFoundException exception = new ResourceNotFoundException(message);
        
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testExceptionMessageAndCause() {
        String message = "Resource not found";
        Throwable cause = new RuntimeException("Cause of the error");
        ResourceNotFoundException exception = new ResourceNotFoundException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testExceptionInheritance() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Test");
        assertInstanceOf(RuntimeException.class, exception);
    }
}
