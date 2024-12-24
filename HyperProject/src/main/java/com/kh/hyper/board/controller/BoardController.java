package com.kh.hyper.board.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.hyper.board.model.service.BoardService;
import com.kh.hyper.board.model.vo.Board;
import com.kh.hyper.board.model.vo.Reply;
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
	
	@PostMapping("boards")	// 권장사항 복수형으로 써라~ 요청하는게 여러 자원들이기 때문에		 MultipartFile[] => 첨부파일 여러개
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
	
				// boards/{category}/{id}	계층구조인것만 board의 10번 뭐 이런식
				// 							아니면 ?해서 키=벨류 형태로 사용해야한다.
	@GetMapping("boards/{id}")	// url에서 /해서 넘기면 /{별칭} 으로 받아서 매개변수에 @PathVariable(name="별칭")으로 값을 뽑아낸다.
	public ModelAndView selectById(@PathVariable(name="id") Long id) {
		// log.info("{}", id);
		Map<String, Object> responseData = boardService.selectById(id);
		
		
		return mv.setViewNameAndData("board/detail", responseData);
	}
	
	@PostMapping("boards/delete")
	public ModelAndView deleteBoard(Long boardNo, String changeName) {
		boardService.deleteBoard(boardNo, changeName);
		
		return mv.setViewNameAndData("redirect:/boards", null);
		
	}
	
	@PostMapping("boards/update-form")
	public ModelAndView updateForm(Long boardNo) {
		Map<String, Object> responseData = boardService.selectById(boardNo);
		return mv.setViewNameAndData("board/update",responseData);
	}
	
	@PostMapping("boards/update")
	public ModelAndView update(Board board, MultipartFile upfile) {
		 log.info("게시글 정보 : {}, 파일정보 : {}", board, upfile);
		 
		 //DB가서 BOARD테이블에 UPDATE
		 
		 // BOARD -> boardTitle, boardContent, bordWriter, boardNo
		 // 이 네게는 무조건 있음
		 
		 /*
		  * 첨부파일은?
		  * 
		  * 1. 기존 첨부파일 x, 새 첨부파일 x => 그렇구나~~
		  * 
		  * 2. 기존 첨부파일 O, 새 첨부파일 O => originName : 쌔거, changeName : 쌔거
		  * 
		  * 3. 기존 첨부파일 X, 새 첨부파일 O => originName : 쌔거, changeName : 쌔거
		  * 
		  * 4. 기존 첨부파일 O, 새 첨부파일 X => originName : 기존 첨부파일 정보, changeName : 기존 첨부파일 정보
		  */
		 
		boardService.updateBoard(board, upfile);
		
		return mv.setViewNameAndData("redirect:/boards", null);
	}
	
	/*
	 * reply
	 * 앞단과 뒷단과의 약속(암묵적인걸 넘어서 대놓고 약속)
	 * 
	 * SELECT : GET
	 * INSERT : POST
	 * UPDATE : PUT
	 * DELETE : DELETE
	 */
	
		
	
	
	// 컨트롤러에서 ajax라는걸 알려주기 위해 메소드 앞에 ajax를 붙인다
	/*
	@ResponseBody
	@PostMapping("reply")
	public int ajaxInsertReply(Reply reply) {
		 return boardService.insertReply(reply);
		
	}
	
	@ResponseBody
	@GetMapping(value = "reply", produces="application/json; charset=UTF-8") // JSON
	public List<Reply> ajaxSelectReply(Long boardNo){
		return boardService.selectReplyList(boardNo);
	}
	*/
	
	@GetMapping("map")
	public String mapForward() {
		return "common/map";
	}
	
	
	
	
}
