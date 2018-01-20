package com.cursomc.resources.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ValidationError extends StandardError {

	private List<FieldMessage> errors = new ArrayList<>();

	public ValidationError(Long timestamp, HttpStatus status, String error, String message, String path) {
		super(timestamp, status, error, message, path);
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addError(String fieldName, String messagem) {
		errors.add(new FieldMessage(fieldName, messagem));
	}
}