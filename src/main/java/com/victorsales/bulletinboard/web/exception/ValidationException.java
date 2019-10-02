package com.victorsales.bulletinboard.web.exception;

import org.springframework.validation.BindingResult;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ValidationException(String message) {
		super(message);
	}

	private BindingResult bindingResult;

	public ValidationException(BindingResult result) {
		this.bindingResult = result;
	}

	public BindingResult getBindingResult() {
		return bindingResult;
	}

	public void setBindingResult(BindingResult bindingResult) {
		this.bindingResult = bindingResult;
	}

}
