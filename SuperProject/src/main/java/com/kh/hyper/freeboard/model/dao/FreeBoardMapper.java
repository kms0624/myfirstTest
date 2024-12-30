package com.kh.hyper.freeboard.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.hyper.freeboard.model.vo.FreeBoard;
import com.kh.hyper.freeboard.model.vo.FreeBoardFile;

@Mapper
public interface FreeBoardMapper {

	int selectTotalCount();
	
	List<FreeBoard> selectBoardList(RowBounds rowBounds);
	
	int increaseCount(long boardNo);
	
	FreeBoard selectBoardById(long boardNo);
	
	FreeBoard selectBoardByTitle(String boardTitle);
	
	void insertBoardFile(FreeBoardFile freeBoardFile);
	
	void insertBoard (FreeBoard freeboard);
	
	FreeBoardFile selectBoardFile(FreeBoardFile freeBoardFile);
	
	int deleteBoard(Long boardNo);
	
	int updateBoard(FreeBoard freeBoard);
	
	void updateBoardFile(FreeBoardFile freeBoardFile);
	
	int searchListCount(Map<String, Object> map);
	
	List<FreeBoard> searchList(Map<String, Object> map);
}
