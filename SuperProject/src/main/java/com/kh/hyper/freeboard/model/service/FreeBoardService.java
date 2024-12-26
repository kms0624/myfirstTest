package com.kh.hyper.freeboard.model.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kh.hyper.freeboard.model.vo.FreeBoard;

public interface FreeBoardService {
	
	Map<String, Object> selectBoardList(int Page);
	
	Map<String, Object> selectById(long boardNo);
	
	void insertBoard(FreeBoard freeeBoard, MultipartFile[] upfile);

}
