package com.kh.hyper.board.model.vo;

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
@Builder	// Vo클래스 필드에 하나하나 세터로 넣어주는 것
@ToString
public class Board {

	// 일반게시글 / 사진게시글
	// 하나의 테이블
	// 파일저장하는 폴더
	// resources/upload_files/바뀐파일명
	
	private Long boardNo;	// int -> Long(기본자료형이 long이 아님 long의 매퍼) 바꾼 이유 // 기본자료형은 null값을 가질 수없어서  List에는 int 기본자료형은 안들어간다
							// Integer로는 부족할 수 있어서 Long을 하는 것이 좋다.
	private String boardTitle;
	private String boardWriter;
	private String boardContent;
	private String originName;
	private String changeName;
	private int count;
	private String createDate;
	private String status;
	
}
