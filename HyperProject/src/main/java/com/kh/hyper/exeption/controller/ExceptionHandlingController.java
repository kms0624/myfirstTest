package com.kh.hyper.exeption.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.kh.hyper.exeption.ComparePasswordException;
import com.kh.hyper.exeption.UserIdNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionHandlingController {

	@ExceptionHandler(UserIdNotFoundException.class)
	protected ModelAndView NoSearchUserIdError(UserIdNotFoundException e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("errorMsg", "아이디가 존재하지 않습니다.").setViewName("common/error_page");
		log.info("발생한 예외 : {}", e.getMessage(), e);
		return mv;
	}
	
	@ExceptionHandler(ComparePasswordException.class)
	protected ModelAndView NotMatchingPasswordError(ComparePasswordException e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("errorMsg", "비밀번호가 일치하지 않습니다.").setViewName("common/error_page");
		return mv;
	}
	
	@ExceptionHandler(UserFoundException.class)
	protected ModelAndView UserExitsError(UserFoundException e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("errorMsg", "이미 존재하는 아이디입니다.").setViewName("common/error_page");
		return mv;
	}
	
	@ExceptionHandler(TooLargeValueException.class)
	protected ModelAndView tooLargeValueError(TooLargeValueException e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("errorMsg", "유효하지 않은 값을 입력하셨습니다.").setViewName("common/error_page");
		return mv;
	}
	
	
	
	
	
	
	
	
	
}
