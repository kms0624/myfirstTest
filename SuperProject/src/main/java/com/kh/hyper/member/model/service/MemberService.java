package com.kh.hyper.member.model.service;

import com.kh.hyper.member.model.vo.Member;

public interface MemberService {

	// 로그인
	Member login(Member member);
	
	// 회원 가입
	int join(Member member);
	
	// 회원정보수정
	int updateMember(Member member);
	
	// 회원 탈퇴
	int deleteMember(Member member);
	
	// -------- 1절
	
	// 아이디 중복체크
	
	
	// -------- 2절
	
	// 이메일 인증
	
	// -------- 3절
}
