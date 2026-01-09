package com.helpingHands.demo.globalException;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ResponseTest {

    @Test
    void testDefaultConstructor() {
        Response<String> response = new Response<>();
        assertNotNull(response);
    }

    @Test
    void testConstructorWithAllFields() {
        String status = "success";
        String message = "Operation successful";
        String data = "Sample data";

        Response<String> response = new Response<>(status, message, data);

        assertEquals(status, response.getStatus());
        assertEquals(message, response.getMessage());
        assertEquals(data, response.getData());
    }

    @Test
    void testConstructorWithOnlyData() {
        String data = "Sample data";

        Response<String> response = new Response<>(data);

        assertEquals(data, response.getData());
        assertNull(response.getStatus());
        assertNull(response.getMessage());
    }

    @Test
    void testGettersAndSetters() {
        Response<Integer> response = new Response<>();
        response.setStatus("error");
        response.setMessage("Something went wrong");
        response.setData(404);

        assertEquals("error", response.getStatus());
        assertEquals("Something went wrong", response.getMessage());
        assertEquals(404, response.getData());
    }
}
