package com.kh.hyper.freeboard.model.vo;

import lombok.Data;

@Data
public class FreeBoardFile {
	private int fileNo;
	private int fileType;
	private long refBno;
	private String originName;
	private String changeName;
	private String uploadDate;
	private String filePath;
	private String status;
}
