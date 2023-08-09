package com.sh.app.common.controller.advice;

import javax.validation.ConstraintViolationException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e) {
		log.error(e.getMessage(), e);
		return "common/error";
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public String invalidRequestValue(ConstraintViolationException e) {
		log.error("입력값오류!! " + e.getMessage(), e);
		return "common/error";
	}

}
