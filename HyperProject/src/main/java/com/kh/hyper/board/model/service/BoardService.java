package com.kh.hyper.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kh.hyper.board.model.vo.Board;
import com.kh.hyper.board.model.vo.Reply;

public interface BoardService {

	Map<String, Object> selectBoardList(int currentPage);
	
	void insertBoard(Board board, MultipartFile upfile);
	
	Map<String, Object> selectById(Long boardNo);
	
	Board updateBoard(Board board, MultipartFile upfile);
	
	void deleteBoard(Long boardNo, String changeName);
	
	// 나중에 어떻게 돌려줄까요?
	int insertReply(Reply replt);
	
	List<Reply> selectReplyList(Long boardNo);
	
}
