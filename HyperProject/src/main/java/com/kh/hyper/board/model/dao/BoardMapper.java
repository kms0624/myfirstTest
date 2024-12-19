package com.kh.hyper.board.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.hyper.board.model.vo.Board;

@Mapper
public interface BoardMapper {

	// 몇개??
	int selectTotalCount();
	
	// 목록 조회
	List<Board> selectBoardList(RowBounds rowBounds);
	
	// 상세조회
	
	// 작성
	void insertBoard(Board board);
	// 수정
	
	// 삭제
	
	// 댓글 목록조회
	
	// 댓글 작성
}
