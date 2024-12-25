package com.kh.hyper.freeboard.model.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kh.hyper.freeboard.model.vo.FreeBoard;

public interface FreeBoardMapper {

	int selectTotalCount();
	
	List<FreeBoard> selectBoardList(RowBounds rowBounds);
}
