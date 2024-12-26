package com.kh.hyper.freeboard.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.hyper.common.ModelAndViewUtil;
import com.kh.hyper.freeboard.model.service.FreeBoardService;
import com.kh.hyper.freeboard.model.vo.FreeBoard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FreeBoardController {

	public final FreeBoardService freeBoardService;
	public final ModelAndViewUtil mv;
	
	@GetMapping("freeBoards")
	public ModelAndView selectBoardList(@RequestParam(value="page", defaultValue="1") int Page) {
		
		Map<String, Object> map = freeBoardService.selectBoardList(Page);
		
		
		return mv.setViewNameAndData("board/list", map);
	}
	
	@GetMapping("insertForm")
	public String insertForm() {
		return "board/insert_form";
	}
	
	@PostMapping("freeBoards")
	public ModelAndView insertBoard(FreeBoard freeBoard, MultipartFile[] upfile, HttpSession session) {
		log.info("게시글 정보 : {}, 파일 정보 : {}", freeBoard, upfile);
		
		freeBoardService.insertBoard(freeBoard, upfile);
		
		
		
		return null;
	}
	
	
	
	
	@GetMapping("freeBoards/{boardNo}")
	public ModelAndView selectById(@PathVariable(name="boardNo") Long boardNo) {
		Map<String, Object> responseData = freeBoardService.selectById(boardNo);
		
		return mv.setViewNameAndData("board/detail", responseData);
	}
	
}
