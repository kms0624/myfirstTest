package com.kh.hyper.member.model.service;

import org.springframework.stereotype.Component;

import com.kh.hyper.exeption.UserIdNotFoundException;
import com.kh.hyper.exeption.controller.TooLargeValueException;
import com.kh.hyper.exeption.controller.UserIdFoundException;
import com.kh.hyper.member.model.dao.MemberMapper;
import com.kh.hyper.member.model.vo.Member;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberValidator {
	
	private final MemberMapper mapper;
	
	public void validateDuplicateMember(Member member) {
		Member existingMember = mapper.login(member);
		
		if(existingMember != null && !member.getUserId().equals(existingMember.getUserId())) {
			throw new UserIdFoundException("이미 존재하는 회원입니다.");
		}
	}
	
	public void validateIdLength(Member member) {
		if(member.getUserId().length() > 30) {
			throw new TooLargeValueException("아이디가 너무 깁니다.");
		}
	}
	
	public void validateJoinMember(Member member) {
		validateDuplicateMember(member);
		validateIdLength(member);
	}
	
	public Member validateMemberExists(Member member) {
		Member existingMember = mapper.login(member);
		if(existingMember != null) {
			return existingMember;
		}
		throw new UserIdNotFoundException("존재하지 않는 사용자입니다.");
	}
	
}
