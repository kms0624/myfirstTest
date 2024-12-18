package com.kh.hyper.member.model.service;

import javax.servlet.http.HttpSession;

import com.kh.hyper.member.model.vo.Member;

public interface MemberService {
	
	// 로그인
	Member login(Member member);

	/*
	// 회원 가입
	int join(Member member);
	
	// 회원정보수정
	int updateMember(Member member);
	
	// 회원 탈퇴
	// int deleteMember(Member member); // 버전 1
	int deleteMember(String userPwd, HttpSession session);
	*/
	void join(Member member);
	
	void updateMember(Member member, HttpSession session);
	
	void deleteMember(String userPwd, HttpSession session);
	// -------- 1절
	
	// 아이디 중복체크
	
	
	// -------- 2절
	
	// 이메일 인증
	
	// -------- 3절
}
