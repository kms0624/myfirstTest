package com.kh.hyper.freeboard.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kh.hyper.common.ModelAndViewUtil;
import com.kh.hyper.freeboard.model.service.FreeBoardService;

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
	
}
