package com.kh.hyper.exeption.controller;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.kh.hyper.exeption.BoardNoValueException;
import com.kh.hyper.exeption.BoardNotFoundException;
import com.kh.hyper.exeption.ComparePasswordException;
import com.kh.hyper.exeption.FailToFileUploadException;
import com.kh.hyper.exeption.InvalidParameterException;
import com.kh.hyper.exeption.UserIdNotFoundException;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@ControllerAdvice
public class ExceptionHandlingController {
	
	private ModelAndView createErrorResponse(String errorMsg, Exception e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("errorMsg", errorMsg).setViewName("common/error_page");
		log.info("발생 예외 : {}", e.getMessage(), e);
		return mv;
	}
	
	@ExceptionHandler(DuplicateKeyException.class)
	protected ModelAndView handleTransactionError(DuplicateKeyException e) {
		return createErrorResponse("잘못된 요청입니다.", e);
	}
	// 중복코드 제거
	/*
	@ExceptionHandler(DuplicateKeyException.class)
	protected ModelAndView handleTransactionError(DuplicateKeyException e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("errorMsg","잘못된 요청입니다.").setViewName("common/error_page");
		return mv;
	}
	*/
	
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
	
	@ExceptionHandler(UserIdFoundException.class)
	protected ModelAndView UserExitsError(UserIdFoundException e) {
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
	
	@ExceptionHandler(BoardNotFoundException.class)
	protected ModelAndView NoSearchBoardError(BoardNotFoundException e) {
		return createErrorResponse("게시글이 존재하지 않습니다.", e);
	}
	
	@ExceptionHandler(BoardNoValueException.class)
	protected ModelAndView noValueError(BoardNoValueException e) {
		return createErrorResponse("필수 입력사항을 모두 입력해주세요", e);
	}
	
	@ExceptionHandler(FailToFileUploadException.class)
	protected ModelAndView failtoFileupload(FailToFileUploadException e) {
		return createErrorResponse("파일 업로드에 실패했습니다.", e);
	}
	
	@ExceptionHandler(InvalidParameterException.class)
	protected ModelAndView invalidParameterError(InvalidParameterException e) {
		return createErrorResponse("너 아주 장난꾸러기구나??", e);
	}
	
	public ResponseEntity<String> badRequest(){
		return new ResponseEntity<String>("Bad request", HttpStatus.BAD_REQUEST);
	}
	
	
	
	
	
	
	
}