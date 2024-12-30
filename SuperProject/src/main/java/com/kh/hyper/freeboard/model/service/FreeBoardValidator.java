package com.kh.hyper.freeboard.model.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.kh.hyper.common.model.vo.PageInfo;
import com.kh.hyper.common.template.Pagination;
import com.kh.hyper.exeption.BoardNoValueException;
import com.kh.hyper.exeption.BoardNotFoundException;
import com.kh.hyper.exeption.FailToFileUploadException;
import com.kh.hyper.exeption.FileNotFoundException;
import com.kh.hyper.exeption.InvalidParameterException;
import com.kh.hyper.freeboard.model.dao.FreeBoardMapper;
import com.kh.hyper.freeboard.model.vo.FreeBoard;
import com.kh.hyper.freeboard.model.vo.FreeBoardFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class FreeBoardValidator {
	
	private final FreeBoardMapper mapper;
	private final ServletContext context;
	
	public int getTotalCount() {
		int totalCount = mapper.selectTotalCount();
		
		return totalCount;
	}
	
	public PageInfo getPageInfo(int totalCount, int page) {
		return Pagination.getPageInfo(totalCount, page, 5, 5);
	}
	
	public List<FreeBoard> getBoardList(PageInfo pi){
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.selectBoardList(rowBounds);
	}
	
	public RowBounds getRowBounds(PageInfo pi){
		
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return rowBounds;
	}
	
	public void incrementViewCount(Long boardNo) {
		
		int result = mapper.increaseCount(boardNo);
		if(result < 0) {
			throw new BoardNotFoundException("게시글이 존재하지 않습니다.");
		}
	}
	
	public FreeBoard selectBoardById(Long boardNo) {
		
		FreeBoard freeBoard = mapper.selectBoardById(boardNo);
		
		if(freeBoard == null) {
			throw new BoardNotFoundException("게시글을 찾을 수 없습니다.");
		}
		return freeBoard;
	}
	
	public void validateBoard(FreeBoard freeBoard) {
		if(freeBoard == null || freeBoard.getBoardTitle() == null || freeBoard.getBoardTitle().trim().isEmpty() ||
								freeBoard.getBoardContent() == null || freeBoard.getBoardContent().trim().isEmpty() ||
								freeBoard.getBoardWriter() == 0 ) {
			throw new BoardNoValueException("부적절한 입력값입니다.");
		}
	}
	
	public FreeBoardFile handleFileUpload(MultipartFile upfile, int num) {
		
		
		String fileName = upfile.getOriginalFilename();
		
		String ext = fileName.substring(fileName.lastIndexOf("."));
		int randomNo = (int)(Math.random() * 90000) + 10000;
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmsss").format(new Date());
		String changeName = currentTime + randomNo + ext;
		
		String savePath = context.getRealPath("/resources/upload_files/");
		
		FreeBoardFile freeBoardFile = FreeBoardFile.builder().originName(fileName)
															.changeName(changeName)
															.filePath(savePath)
															.fileType(num)
															.build();
		
		try {
			upfile.transferTo(new File(savePath + changeName));
		} catch(IllegalStateException | IOException e) {
			throw new FailToFileUploadException("파일 이상");
		}
		
		return freeBoardFile;
		
	}
	
	public void delete(String fileName) {
	
        try {
            new File(context.getRealPath(fileName)).delete();
        } catch (RuntimeException e) {
            throw new FileNotFoundException("파일을 찾을 수 없습니다.");
        }
	}
	
	public void validateBoardNo(Long boardNo) {
		if(boardNo == null || boardNo <= 0) {
			throw new InvalidParameterException("잘못된 방법으로 접근하지 마세요.");
		}
	}
	
	public FreeBoard selectBoardByFreeBoardNo(Long boardNo) {
		FreeBoard freeBoard = mapper.selectBoardById(boardNo);
		if(freeBoard == null) {
			throw new BoardNotFoundException("게시글을 찾을 수 없습니다.");
		}
		return freeBoard;
	}
}
