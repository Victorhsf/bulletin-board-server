package com.victorsales.bulletinboard.web.error;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonPropertyOrder({ "objectName", "field", "code", "message" })
@JsonInclude(Include.NON_NULL)
public class ValidatorError implements Serializable {

	private static final long serialVersionUID = -1000119365868318752L;

	private String objectName;
	private String field;
	private String code;
	private String message;

	public ValidatorError() {
	}

	public ValidatorError(String objectName, String field, String code, String message) {
		this.objectName = objectName;
		this.field = field;
		this.code = code;
		this.message = message;
	}

	public ValidatorError(String objectName, String code, String message) {
		this.objectName = objectName;
		this.code = code;
		this.message = message;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
