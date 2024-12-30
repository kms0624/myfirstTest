package com.kh.hyper.freeboard.model.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kh.hyper.freeboard.model.vo.FreeBoard;
import com.kh.hyper.freeboard.model.vo.FreeBoardFile;

public interface FreeBoardService {
	
	Map<String, Object> selectBoardList(int Page);
	
	Map<String, Object> selectDetailByBoardNo(Long boardNo);
	
	void insertBoard(FreeBoard freeeBoard, MultipartFile[] upfile);
	
	void deleteFreeBoard(Long boardNo, String file1ChangeName
									 , String file2ChangeName
									 , String file3ChangeName
									 , String file4ChangeName
									 , String file5ChangeName);
	
	void updateBoard(FreeBoard freeBoard, MultipartFile[] upfile);
	
	Map<String, Object> selectUpdateByBoardNo(Long boardNo);
	
	Map<String, Object> searchList(Map<String, Object> map);
}
