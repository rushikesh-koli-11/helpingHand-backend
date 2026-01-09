package com.helpingHands.demo.globalException;


import org.springframework.stereotype.Component;

@Component
public class Response<T> {
	public Response() {
		super();
	}

	private String status;
	private String message;
	private T data;

	
	public Response(String status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}
	public Response( T data) {
		this.data = data;
	}

	// Getters and Setters
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}