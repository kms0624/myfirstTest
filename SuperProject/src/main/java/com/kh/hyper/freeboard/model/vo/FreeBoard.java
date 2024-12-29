package com.kh.hyper.freeboard.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FreeBoard {

	private Long boardNo;
	private int boardWriter;
	private String nickname;
	private String boardTitle;
	private String boardContent;
	private String createDate;
	private int selectCount;
	private String status;
	private int likeCount;
}
