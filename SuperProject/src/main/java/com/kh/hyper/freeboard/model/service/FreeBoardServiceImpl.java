package com.kh.hyper.freeboard.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.kh.hyper.common.model.vo.PageInfo;
import com.kh.hyper.common.template.Pagination;
import com.kh.hyper.freeboard.model.dao.FreeBoardMapper;
import com.kh.hyper.freeboard.model.vo.FreeBoard;

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
}
