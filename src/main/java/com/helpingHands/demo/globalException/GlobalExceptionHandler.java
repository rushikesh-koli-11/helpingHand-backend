package com.helpingHands.demo.globalException;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(exception = Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request){
	return	ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(com.helpingHands.demo.globalException.ErrorResponse.builder()
			.details(ex.getMessage())
			.message(ex.getStackTrace())
		.timestamp(LocalDateTime.now()).build());
	}
    
}
