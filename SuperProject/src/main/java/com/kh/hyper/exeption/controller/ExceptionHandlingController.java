package com.kh.hyper.exeption.controller;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.kh.hyper.exeption.ComparePasswordException;
import com.kh.hyper.exeption.TooLargeValueException;
import com.kh.hyper.exeption.UserIdFoundException;
import com.kh.hyper.exeption.UserIdNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionHandlingController {

	private ModelAndView createErrorResponse(String failMsg, Exception e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("failMsg", failMsg).setViewName("common/error_page");
		log.info("발생 예외 : {}", e.getMessage(), e);
		return mv;
	}
	
	@ExceptionHandler(DuplicateKeyException.class)
	protected ModelAndView handleTransactionError(DuplicateKeyException e) {
		return createErrorResponse("잘못된 요청입니다", e);
	}
	/*
	@ExceptionHandler(DuplicateKeyException.class)
	protected ModelAndView handleTransactionError(DuplicateKeyException e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("failMsg", "잘못된용청입니다.").setViewName("common/error_page");
		return mv;
	}
	 */
	
	@ExceptionHandler(UserIdNotFoundException.class)
	protected ModelAndView NoSearchUserIdError(UserIdNotFoundException e) {
		return createErrorResponse("아이디가 존재하지 않습니다.", e);
	}
	
	@ExceptionHandler(ComparePasswordException.class)
	protected ModelAndView NoMatchingPasswordError(ComparePasswordException e) {
		return createErrorResponse("비밀번호가 일치하지 않습니다.", e);
	}
	
	@ExceptionHandler(UserIdFoundException.class)
	protected ModelAndView UserIdExitsError(UserIdFoundException e) {
		return createErrorResponse("이미 존재하는 아이디입니다.", e);
	}
	
	@ExceptionHandler(TooLargeValueException.class)
	protected ModelAndView tooLargeValueError(TooLargeValueException e) {
		return createErrorResponse("너무길어요~", e);
	}
}
