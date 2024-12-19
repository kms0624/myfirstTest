package com.kh.hyper.member.model.vo;

import java.sql.Date;

import lombok.Data;

/*
 * Lombok(롬복)
 * 
 * - 자동 코드 생성 라이브러리
 * 
 * Lombok 설치 방법
 * 1. 라이브러리를 다운로드
 * 2. 다운로드 된 .jar파일을 찾아서 설치(작업할 IDE를 체크)
 * 3. IDE 재실행
 * 
 * @Getter
 * @Setter
 * @ToString
 * @NoArgsConstructor
 * --------------------
 * @Builder
 * @AllArgsConstructor
 * --------------------
 * @Data	- 유연성이 부족함 내가 빼고 싶은거를 따로 지정해서 뺄 수 가 없음 다 넣어버림
 * --------------------
 * 
 * - Lombok 사용 시 주의사항
 * 
 * - pName이라는 필드를 선언했을 경우 => personName이라고 풀로 다 쓰는게 맞다. 외자 ㄴㄴ
 * Lombok의 명명규칙 == setPName() / getPName(String pName)
 * 
 * => getter메소드를 내부적으로 호출할 때
 * 
 * ${ pName } / #{ pName } == getpName()을 찾는다.
 * 
 */
/*
// 기본생성자 생성하는 애노테이션
@NoArgsConstructor
// 매개변수 생성자 생성하는 애노테이션
@AllArgsConstructor
// 게터 생성하는 애노테이션
@Getter
// 세터 생성하는 애노테이션
@Setter
// toString 생성하는 애노테이션
@ToString
*/
@Data
public class Member {
	
	private String userId;
	private String userPwd;
	private String userName;
	private String email;
	private String gender;
	private String age;
	private String phone;
	private String address;
	private Date enrollDate;
	private Date modifyDate;
	
	
}
