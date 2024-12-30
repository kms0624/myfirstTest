package com.kh.hyper.freeboard.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.hyper.common.model.vo.PageInfo;
import com.kh.hyper.exeption.BoardNotFoundException;
import com.kh.hyper.freeboard.model.dao.FreeBoardMapper;
import com.kh.hyper.freeboard.model.vo.FreeBoard;
import com.kh.hyper.freeboard.model.vo.FreeBoardFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor

public class FreeBoardServiceImpl implements FreeBoardService {

	private final FreeBoardMapper mapper;
	private final FreeBoardValidator validator;
	
	@Override
	public Map<String, Object> selectBoardList(int currentPage) {
		
		int totalCount = validator.getTotalCount();
		
		PageInfo pi = validator.getPageInfo(totalCount, currentPage);
		
		List<FreeBoard> boards = validator.getBoardList(pi);
		
		Map<String, Object> map = new HashMap();
		map.put("freeBoard", boards);
		map.put("pageInfo", pi);
		
		return map;
	}

	@Override
	@Transactional
	public void insertBoard(FreeBoard freeBoard, MultipartFile[] upfile) {
		validator.validateBoard(freeBoard);
		
		mapper.insertBoard(freeBoard);
		
		//FreeBoardFile file01 = (FreeBoardFile)upfile[0];
		//String result = upfile[0].getOriginalFilename();
		//log.info("{}", result);
		//log.info("{}", file01);
		//log.info("",upfile); 인덱스참조해서 .filename="" 으로 비교하기
		for(int i = 0; i < 5; i++) {
			if(!!!("".equals(upfile[i].getOriginalFilename()))) {
				int num = i + 1;
				FreeBoardFile freeBoardFile = validator.handleFileUpload(upfile[i], num);
				mapper.insertBoardFile(freeBoardFile);
			}
		}
	}
	
	
	
	@Override
	public Map<String, Object> selectDetailByBoardNo(Long boardNo) {
				
		FreeBoard freeBoard = validator.selectBoardById(boardNo);
		//log.info("{}", freeBoard);
		
		Map<String, Object> map = new HashMap();
		
		for(int i = 1; i < 6; i++) {
			FreeBoardFile freeBoardFile = new FreeBoardFile();
			freeBoardFile.setRefBno(boardNo);
			freeBoardFile.setFileType(i);
			map.put("file"+i,mapper.selectBoardFile(freeBoardFile));
		}
		//log.info("{}", map);
		map.put("freeBoard", freeBoard);
		validator.incrementViewCount(boardNo);
		return map;
		
	}
	
	@Override
	public Map<String, Object> selectUpdateByBoardNo(Long boardNo) {
				
		FreeBoard freeBoard = validator.selectBoardById(boardNo);
		
		Map<String, Object> map = new HashMap();
		
		for(int i = 1; i < 6; i++) {
			FreeBoardFile freeBoardFile = new FreeBoardFile();
			freeBoardFile.setRefBno(boardNo);
			freeBoardFile.setFileType(i);
			map.put("file"+i,mapper.selectBoardFile(freeBoardFile));
		}
		map.put("freeBoard", freeBoard);
		return map;
		
	}

	@Override
	public void deleteFreeBoard(Long boardNo, String file1ChangeName
											, String file2ChangeName
											, String file3ChangeName
											, String file4ChangeName
											, String file5ChangeName) {
		
		validator.validateBoardNo(boardNo);
		FreeBoard freeBoard = validator.selectBoardByFreeBoardNo(boardNo);
		//log.info("{}",file1ChangeName);
		//log.info("{}",file2ChangeName);
		
		int result = mapper.deleteBoard(boardNo);
		
		if(result <= 0) {
			throw new BoardNotFoundException("게시글 삭제 실패");
		}
		
		String[] fileChangeNames = { file1ChangeName, file2ChangeName, file3ChangeName, file4ChangeName, file5ChangeName };

	    for (int i = 0; i < fileChangeNames.length; i++) {
	        String fileName = fileChangeNames[i];
	    	if (fileName != null && !"".equals(fileName)) {
	    		validator.delete(fileName);
	    	}
	    }
		
	}

	@Override
	public void updateBoard(FreeBoard freeBoard, MultipartFile[] upfile) {

		validator.validateBoardNo(freeBoard.getBoardNo());
		validator.selectBoardByFreeBoardNo(freeBoard.getBoardNo());
		
		
		
		for(int i = 0; i < 5; i++) {
			FreeBoardFile searchFile = new FreeBoardFile();
			int num = i + 1;
			// log.info("{}", freeBoard.getBoardNo());
			// searchFile.builder().refBno(freeBoard.getBoardNo()).fileType(num).build();
			searchFile.setRefBno(freeBoard.getBoardNo());
			searchFile.setFileType(num);
			// log.info("{}", searchFile);
			FreeBoardFile searchFileTotal = mapper.selectBoardFile(searchFile);
			// log.info("{}",searchFileTotal);
			// log.info("{}",upfile);
			if(searchFileTotal != null && !("".equals(searchFileTotal.getOriginName()))){
				if(!!!("".equals(upfile[i].getOriginalFilename()))) {
					validator.delete(searchFileTotal.getChangeName());
					FreeBoardFile freeBoardFile = validator.handleFileUpload(upfile[i], num);
					freeBoardFile.setRefBno(freeBoard.getBoardNo());
					mapper.updateBoardFile(freeBoardFile);
				}
			} else {
				if(!!!("".equals(upfile[i].getOriginalFilename()))) {
					FreeBoardFile freeBoardFile = validator.handleFileUpload(upfile[i], num);
					freeBoardFile.setRefBno(freeBoard.getBoardNo());
					mapper.insertBoardFile(freeBoardFile);
				} 
			}
		}

		// log.info("{}",freeBoard);
		int result = mapper.updateBoard(freeBoard);
		
		if(result < 1) {
			throw new BoardNotFoundException("게시글 업데이트 실패하셨습니다.");
		}
		
	}

	@Override
	public Map<String, Object> searchList(Map<String, Object> map) {

		int result = mapper.searchListCount(map);
		
		PageInfo pi = validator.getPageInfo(result, (int)map.get("page"));
		
		RowBounds rowBounds = validator.getRowBounds(pi);
		
		map.put("rowBounds", rowBounds);
		
		List<FreeBoard> boards = mapper.searchList(map);
		map.put("freeBoard", boards);
		map.put("pageInfo", pi);
		
		return map;
	}

}
