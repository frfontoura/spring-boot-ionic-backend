package com.cursomc.resources.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ValidationError extends StandardError {

	private List<FieldMessage> errors = new ArrayList<>();

	public ValidationError(HttpStatus status, String message, Long timestamp) {
		super(status, message, timestamp);
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addError(String fieldName, String messagem) {
		errors.add(new FieldMessage(fieldName, messagem));
	}
}