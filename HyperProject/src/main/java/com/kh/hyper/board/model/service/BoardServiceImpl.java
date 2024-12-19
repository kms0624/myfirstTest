package com.kh.hyper.board.model.service;

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
import org.springframework.web.multipart.MultipartFile;

import com.kh.hyper.board.model.dao.BoardMapper;
import com.kh.hyper.board.model.vo.Board;
import com.kh.hyper.common.model.vo.PageInfo;
import com.kh.hyper.common.template.Pagination;
import com.kh.hyper.exeption.BoardNoValueException;
import com.kh.hyper.exeption.BoardNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardMapper mapper;
	private final ServletContext context;
	
	// 이건 한개의 메소드가 한개의 작업을 해야하니 메소드를 분리시켜 메소드 책임분리
	private int getTotalCount() {
		int totalCount = mapper.selectTotalCount();
		
		if(totalCount == 0) {
			throw new BoardNotFoundException("없어~~~");
		}
		
		return totalCount;
	}
	
	private PageInfo getPageInfo(int totalCount, int page) {
		return Pagination.getPageInfo(totalCount, page, 5, 5);
	}
	
	private List<Board> getBoardList(PageInfo pi){
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.selectBoardList(rowBounds);
	}
	
	private void validateBoard(Board board) {
		if(board == null || board.getBoardTitle() == null ||board.getBoardTitle().trim().isEmpty() ||
							board.getBoardContent() == null || board.getBoardContent().trim().isEmpty() || 
							board.getBoardWriter() == null || board.getBoardWriter().trim().isEmpty()) {
			throw new BoardNoValueException("부적절한 입력값");
		}
		
		// XSS (크로스사이드스크립트) 공격 방지를 위한 처리
		/*
		 * < == &lt;
		 * > == &gt;
		 * \ == &quot;
		 * & == &amp;
		 */
		String boardTitle = escapeHtml(board.getBoardTitle());
		String boardContent = escapeHtml(board.getBoardContent());
		
		convertNewLineToBr(boardTitle);
		convertNewLineToBr(boardContent);
		
		board.setBoardTitle(boardTitle);
		board.setBoardContent(boardContent);
		
	}
	
	private String escapeHtml(String value) {
		return value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
	
	private String convertNewLineToBr(String value) {
		return value.replaceAll("\n", "<br>");
	}
	
	private void handleFileUpload(Board board, MultipartFile upfile) {
		String fileName = upfile.getOriginalFilename();
		String ext = fileName.substring(fileName.lastIndexOf("."));
		int randomNo = (int)(Math.random() * 90000) + 10000;
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String changeName = currentTime + randomNo + ext;
		
		// 서블릿컨텍스트에서 경로지정하는 걸 만들 수 있는데
		// 서블릿컨텍스트르르 필드부에서 final로 달라고 할 수 있다
		// 또는 컨트롤러에서 HttpSession session 매개변수에서 받아서 보내줘서 여기서 session을 사용할 수 있다.	session을 받아서 여기서 로그인했는지도 확인할 수 있음
		// 왜 이렇게 사용했는지 물어보면 말할 수 있어야 하는데
		// service에서 쓰기로 결정
		
		String savePath = context.getRealPath("/resources/upload_files/");
		
		try {
			upfile.transferTo(new File(savePath + changeName));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Map<String, Object> selectBoardList(int currentPage) {
		
		// 총 개수				== DB가서 조회
		// 요청 페이지				== currentPage
		// 한 페이지에 게시글 몇 개?	== 5
		// 페이징바 몇 개?			== 5
		/*
		int totalCount = mapper.selectTotalCount();
		
		if(totalCount == 0) {
			throw new BoardNotFoundException("없어~~~");
		}
		*/
		int totalCount = getTotalCount();
		
		
		//log.info("게시글 개수 : {} ", totalCount);
		//log.info("요청페이지 : {}", currentPage);
		//PageInfo pi = Pagination.getPageInfo(totalCount, currentPage, 5, 5);
		
		PageInfo pi = getPageInfo(totalCount, currentPage);
		/*
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		
		List<Board> boards = mapper.selectBoardList(rowBounds);
		
		*/
		
		List<Board> boards = getBoardList(pi);
		//log.info("게시글 목록 : {}", boards);
		
		Map<String, Object> map = new HashMap();
		map.put("board", boards);
		map.put("pageInfo", pi);
		
		return map;
	}

	@Override
	public void insertBoard(Board board, MultipartFile upfile) {
		
		validateBoard(board);	// Board 유효성 검증
		
		
		
		// 파일 유무 체크 업로드
		// 코드 작성은 방어적으로
		/*
		if(!!!("".equals(upfile.getOriginalFilename()))) {
			String fileName = upfile.getOriginalFilename();
			String ext = fileName.substring(fileName.lastIndexOf("."));
			int randomNo = (int)(Math.random() * 90000) + 10000;
			String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			String changeName = currentTime + randomNo + ext;
			
			// 서블릿컨텍스트에서 경로지정하는 걸 만들 수 있는데
			// 서블릿컨텍스트르르 필드부에서 final로 달라고 할 수 있다
			// 또는 컨트롤러에서 HttpSession session 매개변수에서 받아서 보내줘서 여기서 session을 사용할 수 있다.	session을 받아서 여기서 로그인했는지도 확인할 수 있음
			// 왜 이렇게 사용했는지 물어보면 말할 수 있어야 하는데
			// service에서 쓰기로 결정
			
			String savePath = context.getRealPath("/resources/upload_files/");
			
			try {
				upfile.transferTo(new File(savePath + changeName));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			//첨부파일이 존재했다 => 업로드 + Board객체에 originName + changeName
			board.setOriginName(fileName);
			board.setChangeName("resources/upload_files" + changeName);
			
		}
		 */
		// 첨부파일이 존재하지 않을 경우 : board == 제목, 내용, 작성자
		// 첨부파일이 존재할 경우 : board == 제목, 내용, 작성자, 원본명, 변경명
		/*
		if(("").equals(board.getBoardTitle()) || ("").equals(board.getBoardContent()) || ("").equals(board.getBoardWriter())) {
			throw new BoardNoValueException("부적절한 입력값");
		}
		 */
		
		handleFileUpload(board, upfile);
		
		mapper.insertBoard(board);
		
		
	}

	@Override
	public Board selectById(Long boardNo) {
		return null;
	}

	@Override
	public void updateBoard(Board board) {

	}

	@Override
	public void deleteBoard(Long boardNo) {

	}



}
