package com.kh.hyper.freeboard.model.vo;

import java.util.Date;

import lombok.Data;

@Data
public class FreeBoard {

	private Long boardNo;
	private int boardWriter;
	private String boardTitle;
	private String boardContent;
	private String createDate;
	private int selectCount;
	private String status;
}