package com.kh.hyper.board.model.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kh.hyper.board.model.vo.Board;

public interface BoardService {

	Map<String, Object> selectBoardList(int currentPage);
	
	void insertBoard(Board board, MultipartFile upfile);
	
	Map<String, Object> selectById(Long boardNo);
	
	Board updateBoard(Board board, MultipartFile upfile);
	
	void deleteBoard(Long boardNo, String changeName);
	
}
