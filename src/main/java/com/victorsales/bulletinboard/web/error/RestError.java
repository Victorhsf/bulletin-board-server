package com.victorsales.bulletinboard.web.error;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonPropertyOrder({ "httpStatus", "httpStatusMessage", "errorDate", "errorMessage", "errors" })
@JsonInclude(Include.NON_NULL)
public class RestError implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private List<ValidatorError> errors = new ArrayList<ValidatorError>();
	private int httpStatus;
	private String httpStatusMessage;
	private String errorDate;
	private String errorMessage;

	public RestError(HttpServletRequest request, HttpServletResponse response, BindingResult result,
			HttpStatus httpStatus) {
		this.httpStatus = response.getStatus();
		this.errorDate = dateFormat.format(new Date());
		this.httpStatusMessage = httpStatus.getReasonPhrase();
		this.httpStatus = httpStatus.value();
		for (ObjectError erro : result.getAllErrors()) {
			if (erro instanceof FieldError) {
				this.errors.add(new ValidatorError(erro.getObjectName(), ((FieldError) erro).getField(), erro.getCode(),
						erro.getDefaultMessage()));
			} else {
				this.errors.add(new ValidatorError(erro.getObjectName(), erro.getCode(), erro.getDefaultMessage()));
			}
		}
	}

	public List<ValidatorError> getErrors() {
		return errors;
	}

	public void setErrors(List<ValidatorError> errors) {
		this.errors = errors;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getHttpStatusMessage() {
		return httpStatusMessage;
	}

	public void setHttpStatusMessage(String httpStatusMessage) {
		this.httpStatusMessage = httpStatusMessage;
	}

	public String getErrorDate() {
		return errorDate;
	}

	public void setErrorDate(String errorDate) {
		this.errorDate = errorDate;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String toJson() {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "";
		try {
			jsonString = mapper.writeValueAsString(this);
		} catch (Exception e) {
			jsonString = toString();
		}
		return jsonString;
	}

}
