package com.kh.hyper.board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.hyper.board.model.service.BoardService;
import com.kh.hyper.board.model.vo.Reply;
import com.kh.hyper.common.model.vo.ResponseData;

import lombok.RequiredArgsConstructor;

@RestController	// 데이터를 무조건 보내줄 것이니깐 RestController 애노테이션을 사용한다.
				// ResponseBody + Controller 느낌 (모든 메소드가 ResponseBody 애노테이션이 달릴 경우)
@RequestMapping("reply")
@RequiredArgsConstructor
public class BoardApiController {
	
	private final BoardService boardService;
	
	/*
	 * spring에서 응답 객체를 제공해주는데 그게 ResponseEntity responseData;
	 * 
	 */
	
	@PostMapping
	public ResponseEntity<ResponseData> ajaxInsertReply(Reply reply) {
		 //ResponseEntity responseData;
		
		int result =boardService.insertReply(reply);
		ResponseData response = ResponseData.builder().message("댓글 등록에 성공했습니다!").status(HttpStatus.OK.toString()).data(result).build();
		return new ResponseEntity<ResponseData>(response, HttpStatus.OK);
	}
	
	@GetMapping	//(produces="application/json; charset=UTF-8")	// Json에서 알아서 되니깐 produces속성도 사용할 필요 없다.
	public ResponseEntity<ResponseData> ajaxSelectReply(Long boardNo){
		
		List<Reply> replies = boardService.selectReplyList(boardNo);
		ResponseData response = ResponseData.builder().message("댓글 조회에 성공했습니다!").status(HttpStatus.OK.toString()).data(replies).build();
		
		return new ResponseEntity<ResponseData>(response, HttpStatus.OK);
	}
	
	
	
	
	
	
}
