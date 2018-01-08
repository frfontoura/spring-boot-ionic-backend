package com.cursomc.resources.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cursomc.services.exception.DataIntegrityException;
import com.cursomc.services.exception.ObjectNotFoundException;

@RestControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError error = new StandardError(status, e.getMessage(), System.currentTimeMillis(), true);
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError error = new StandardError(status, e.getMessage(), System.currentTimeMillis(), true);
		return ResponseEntity.status(status).body(error);
	}
	
}
