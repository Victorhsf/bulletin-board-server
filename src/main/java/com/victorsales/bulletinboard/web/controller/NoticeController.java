package com.victorsales.bulletinboard.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.victorsales.bulletinboard.domain.Notice;
import com.victorsales.bulletinboard.service.INoticeService;
import com.victorsales.bulletinboard.web.dto.NoticeDto;
import com.victorsales.bulletinboard.web.error.RestError;
import com.victorsales.bulletinboard.web.exception.NoticeNotFoundException;
import com.victorsales.bulletinboard.web.exception.NoticeNotUpdatableException;
import com.victorsales.bulletinboard.web.exception.ValidationException;
import com.victorsales.bulletinboard.web.validator.NoticeValidator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Endpoints da para configurar um canalAtendimentoWhatsapp")
@RestController
@RequestMapping("/notice")
public class NoticeController {

	protected static final String HEADER_EXCEPTION = "x-exception-error";

	@Autowired
	private INoticeService noticeService;

	@Autowired
	private NoticeValidator validator;

	@InitBinder("notice")
	public void initBinder(WebDataBinder binder, HttpServletRequest request) {
		binder.addValidators(validator);
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid Notice notice, BindingResult result) {
		if (result.hasErrors()) {
			throw new ValidationException(result);
		}
		return ResponseEntity.ok(noticeService.save(notice));
	}

	@ApiOperation("return page with NoticeDto objects")
	@GetMapping
	public Page<NoticeDto> list(Pageable pageable) {
		return noticeService.findAll(pageable);
	}

	@ApiOperation("find notice by id")
	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable("id") long id) {
		return ResponseEntity
				.ok(noticeService.find(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT)));
	}

	@ApiOperation("updated viewedAt field of Notice object by id")
	@PatchMapping("/visualize/{id}")
	public ResponseEntity<?> visualize(@PathVariable("id") long id) {
		return ResponseEntity.ok(this.noticeService.visualize(id));
	}
	
	@ApiOperation("updated Notice object")
	@PutMapping
	public ResponseEntity<?> update(@RequestBody @Valid Notice notice, BindingResult result) {
		if (result.hasErrors()) {
			throw new ValidationException(result);
		}
		return ResponseEntity.ok(this.noticeService.update(notice));
	}

	@ApiOperation("delete Notice object")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		boolean isDeleted = this.noticeService.delete(id);
		return new ResponseEntity<Void>(isDeleted ? HttpStatus.OK : HttpStatus.PRECONDITION_FAILED);
	}

	@ExceptionHandler({ NoticeNotFoundException.class })
	@ResponseBody
	public ResponseEntity<?> handleNoticeNotFound(NoticeNotFoundException exception) {
		return new ResponseEntity<String>(exception.getMessage(), HttpStatus.PRECONDITION_FAILED);
	}

	@ExceptionHandler({ NoticeNotUpdatableException.class })
	@ResponseBody
	public ResponseEntity<?> handleNoticeNotFoundNotUpdatable(NoticeNotUpdatableException exception) {
		return new ResponseEntity<String>(exception.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(ValidationException.class)
	@ResponseBody
	public RestError exceptionValidation(HttpServletRequest request, HttpServletResponse response,
			ValidationException exception) {
		RestError restValidatorError = new RestError(request, response, exception.getBindingResult(),
				HttpStatus.PRECONDITION_FAILED);
		response.setStatus(HttpStatus.PRECONDITION_FAILED.value());
		response.setHeader(HEADER_EXCEPTION, restValidatorError.toString());
		return restValidatorError;
	}

}
