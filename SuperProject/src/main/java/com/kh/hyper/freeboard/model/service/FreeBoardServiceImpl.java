package com.kh.hyper.freeboard.model.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

@Service
@Slf4j
@RequiredArgsConstructor

public class FreeBoardServiceImpl implements FreeBoardService {

	private final FreeBoardMapper mapper;
	private final ServletContext context;
	
	private int getTotalCount() {
		int totalCount = mapper.selectTotalCount();
		
		return totalCount;
	}
	
	private PageInfo getPageInfo(int totalCount, int page) {
		return Pagination.getPageInfo(totalCount, page, 5, 5);
	}
	
	private List<FreeBoard> getBoardList(PageInfo pi){
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.selectBoardList(rowBounds);
	}
	
	private void incrementViewCount(long boardNo) {
		
		int result = mapper.increaseCount(boardNo);
		if(result < 0) {
			throw new BoardNotFoundException("게시글이 존재하지 않습니다.");
		}
	}
	
	private FreeBoard selectBoardById(long boardNo) {
		
		FreeBoard freeBoard = mapper.selectBoardById(boardNo);
		
		if(freeBoard == null) {
			throw new BoardNotFoundException("게시글을 찾을 수 없습니다.");
		}
		return freeBoard;
	}
	
	private FreeBoardFile handleFileUpload(MultipartFile upfile, int num) {
		
		
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
	
	private void validateBoard(FreeBoard freeBoard) {
		if(freeBoard == null || freeBoard.getBoardTitle() == null || freeBoard.getBoardTitle().trim().isEmpty() ||
								freeBoard.getBoardContent() == null || freeBoard.getBoardContent().trim().isEmpty() ||
								freeBoard.getBoardWriter() == 0 ) {
			throw new BoardNoValueException("부적절한 입력값입니다.");
		}
	}
	
	private void validateBoardNo(Long boardNo) {
		if(boardNo == null || boardNo <= 0) {
			throw new InvalidParameterException("잘못된 방법으로 접근하지 마세요.");
		}
	}
	
	private FreeBoard selectBoardByFreeBoardNo(Long boardNo) {
		FreeBoard freeBoard = mapper.selectBoardById(boardNo);
		if(freeBoard == null) {
			throw new BoardNotFoundException("게시글을 찾을 수 없습니다.");
		}
		return freeBoard;
	}
	
	// ========================================================================

	@Override
	public Map<String, Object> selectBoardList(int currentPage) {
		
		int totalCount = getTotalCount();
		
		PageInfo pi = getPageInfo(totalCount, currentPage);
		
		List<FreeBoard> boards = getBoardList(pi);
		
		Map<String, Object> map = new HashMap();
		map.put("freeBoard", boards);
		map.put("pageInfo", pi);
		
		return map;
	}

	@Override
	@Transactional
	public void insertBoard(FreeBoard freeBoard, MultipartFile[] upfile) {
		validateBoard(freeBoard);
		
		mapper.insertBoard(freeBoard);
		
		//FreeBoardFile file01 = (FreeBoardFile)upfile[0];
		//String result = upfile[0].getOriginalFilename();
		//log.info("{}", result);
		//log.info("{}", file01);
		//log.info("",upfile); 인덱스참조해서 .filename="" 으로 비교하기
		for(int i = 0; i < 5; i++) {
			if(!!!("".equals(upfile[i].getOriginalFilename()))) {
				int num = i + 1;
				FreeBoardFile freeBoardFile = handleFileUpload(upfile[i], num);
				mapper.insertBoardFile(freeBoardFile);
			}
		}
	}
	
	
	
	@Override
	public Map<String, Object> selectById(long boardNo) {
		
		incrementViewCount(boardNo);
		
		FreeBoard freeBoard = selectBoardById(boardNo);
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
		return map;
		
	}

	@Override
	public void deleteFreeBoard(Long boardNo, String file1ChangeName
											, String file2ChangeName
											, String file3ChangeName
											, String file4ChangeName
											, String file5ChangeName) {
		
		validateBoardNo(boardNo);
		FreeBoard freeBoard = selectBoardByFreeBoardNo(boardNo);
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
	            try {
	                new File(context.getRealPath(fileName)).delete();
	            } catch (RuntimeException e) {
	                throw new FileNotFoundException("파일을 찾을 수 없습니다.");
	            }
	        }
	    }
		
	}


}
