package com.victorsales.bulletinboard.web.validator;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.victorsales.bulletinboard.domain.Notice;
import com.victorsales.bulletinboard.service.INoticeService;

@Component
public class NoticeValidator implements Validator {
	private final String CLASS_NAME = "notice.";

	@Autowired
	private INoticeService noticeService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Notice.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Notice notice = (Notice) target;
		if (!this.objectIsNull(notice, errors)) {
			this.validateUpdate(notice, errors);
			this.validateTitle(notice, errors);
			this.validateDescription(notice, errors);
		}
	}

	private boolean objectIsNull(Notice notice, Errors errors) {
		if (Objects.isNull(notice)) {
			this.addErrorMessage("objectIsEmpty", "Fill the fields", errors);
			return true;
		}
		return false;
	}

	private void validateUpdate(Notice notice, Errors errors) {
		if (notice.getId() > 0 && !noticeService.existsById(notice.getId())) {
			this.addErrorMessage("objectNotExist", "Notice not found on the database", errors);
		}
	}

	private void validateTitle(Notice notice, Errors errors) {
		if (!this.isEmptyOrNull("title", notice.getTitle(), errors)
				&& !this.validateLength("title", notice.getTitle(), 1, 255, errors)) {
			if (noticeService.existsTitle(notice.getTitle())) {
				this.addErrorMessage("titleExists", "The field title must be unique", errors);
			}
		}
	}

	private void validateDescription(Notice notice, Errors errors) {
		if (!this.isEmptyOrNull("description", notice.getDescription(), errors)) {
			this.validateLength("description", notice.getDescription(), 1, 255, errors);

		}
	}

	public boolean isEmptyOrNull(String fieldName, String value, Errors errors) {
		if (StringUtils.isEmpty(value)) {
			this.addErrorMessage(fieldName + ".isEmpty", "The field " + fieldName + " not be empty", errors);
			return true;
		}
		return false;
	}

	public boolean validateLength(String fieldName, String value, long min, long max, Errors errors) {
		if (value == null || value.length() < min || value.length() > max) {
			errors.rejectValue(fieldName + ".invalidLength",
					"The field" + fieldName + " must be size between " + min + " and " + max);
			return true;
		}

		return false;
	}

	private void addErrorMessage(final String error, String detailMessage, Errors errors) {
		errors.reject(CLASS_NAME + error, detailMessage);
	}
}
