package com.kh.hyper.common.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data	// 매개변수 생성자는 생성을 안해준다 그래서 @AllArgsConstructor을 작성해줘야 매개변수 생성자를 생성할 수 있다.
@AllArgsConstructor
@Builder	// 전체 매개변수 생성자가 있어야 한다.
public class PageInfo {
	
	private int listCount;
	private int currentPage;
	private int boardLimit;
	private int pageLimit;
	
	private int maxPage;
	private int startPage;
	private int endPage;

}
