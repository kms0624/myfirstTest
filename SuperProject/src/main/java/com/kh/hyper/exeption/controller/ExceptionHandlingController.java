package com.kh.hyper.exeption.controller;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.kh.hyper.exeption.BoardNoValueException;
import com.kh.hyper.exeption.BoardNotFoundException;
import com.kh.hyper.exeption.ComparePasswordException;
import com.kh.hyper.exeption.FailToFileUploadException;
import com.kh.hyper.exeption.FailToReplyDeleteException;
import com.kh.hyper.exeption.FailToReplyInsertException;
import com.kh.hyper.exeption.FailToReplySelectException;
import com.kh.hyper.exeption.FileNotFoundException;
import com.kh.hyper.exeption.InvalidParameterException;
import com.kh.hyper.exeption.TooLargeValueException;
import com.kh.hyper.exeption.UserIdFoundException;
import com.kh.hyper.exeption.UserIdNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionHandlingController {

	private ModelAndView createErrorResponse(String failMsg, Exception e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("errorMsg", failMsg).setViewName("common/error_page");
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
	
	@ExceptionHandler(BoardNotFoundException.class)
	protected ModelAndView boardNotFoundError(BoardNotFoundException e) {
		return createErrorResponse("자유게시판에는 그런 글이 존재하지 않습니다.", e);
	}
	
	@ExceptionHandler(BoardNoValueException.class)
	protected ModelAndView boardNoValueError(BoardNoValueException e) {
		return createErrorResponse("게시판의 글을 입력하기엔 부족한 정보입니다.", e);
	}
	
	@ExceptionHandler(FailToFileUploadException.class)
	protected ModelAndView failToFileUploadError(FailToFileUploadException e) {
		return createErrorResponse("파일이 업로드 되지 않습니다.", e);
	}
	
	@ExceptionHandler(InvalidParameterException.class)
	protected ModelAndView invalidParameterException(InvalidParameterException e) {
		return createErrorResponse("잘못된 경로로 접근하였습니다.", e);
	}
	
	@ExceptionHandler(FileNotFoundException.class)
	protected ModelAndView fileNotFoundException(FileNotFoundException e) {
		return createErrorResponse("파일을 찾을 수 없습니다.", e);
	}
	
	@ExceptionHandler(FailToReplyInsertException.class)
	protected ModelAndView failToReplyInsertException(FailToReplyInsertException e) {
		return createErrorResponse("댓글 작성을 실패했습니다.", e);
	}
	
	@ExceptionHandler(FailToReplyDeleteException.class)
	protected ModelAndView failToReplyDeleteException(FailToReplyDeleteException e) {
		return createErrorResponse("댓글 삭제에 실패했습니다.", e);
	}

}
