package com.kh.hyper.board.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.hyper.board.model.service.BoardService;
import com.kh.hyper.board.model.vo.Board;
import com.kh.hyper.common.ModelAndViewUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor // 생성자 주입
@Slf4j // 롬복에 있는거
public class BoardController {

	// Board board = Board.builder().boardTitle("안뇽").boardContent("반가워").boardWriter("admin").build(); 하면 객체를 board로 담아준다.
	private final BoardService boardService;
	private final ModelAndViewUtil mv;
	
	// menubar.jsp에서 게시판 클릭 시 => boards ==> Page == 1
	// 페이징바에서 눌렀다 => boards?page=요청페이지 ==> Page == 요청페이지
	@GetMapping("boards")
	public ModelAndView selectBoardList(@RequestParam(value="page", defaultValue="1")int page) {
		
		Map<String, Object> map = boardService.selectBoardList(page);
		
		return mv.setViewNameAndData("board/list", map);
	}
	
	@GetMapping("insertForm")
	public String insertForm() {
		return "board/insert_form";
	}
	
	@PostMapping("boards")	// 권장사항 복수형으로 써라~ 요청하는게 여러 자원들이기 때문에
	public ModelAndView save(Board board, MultipartFile upfile, HttpSession session) {
		
		log.info("게시글 정보 : {}, 파일정보 : {}", board, upfile);
		
		// 첨부파일의 존재 유무
		// 무조건 객체가 생성됨!!
		// => filename이랑 size 중 무엇을 비교해서 사용해야 할까?
		// 파일이라는 건 메모장이어도 안에 .이라도 찍어야 용량이 0이 아니다. file만 있으면 0이기 때문에 size로는 비교 불가
		// 정답은 filename
		// => 차이점 => filename필드에 원본명이 존재하는가 ""인가
		// 전달된 파일이 존재할 경우 => 파일명 수정 후 업로드
		
		boardService.insertBoard(board, upfile);
		session.setAttribute("alertMsg", "게시글 등록 성공");
		return mv.setViewNameAndData("redirect:boards", null);
	}
	
	@GetMapping("boards/${id}")	// url에서 /해서 넘기면 /${별칭} 으로 받아서 매개변수에 @PathVariable(name="별칭")으로 값을 뽑아낸다.
	public ModelAndView selectById(@PathVariable(name="id") Long id) {
		log.info("{}", id);
		
		
		
		return mv.setViewNameAndData(null, null);
	}
	
	
	
	
	
	
	
	
	
	
	
}
