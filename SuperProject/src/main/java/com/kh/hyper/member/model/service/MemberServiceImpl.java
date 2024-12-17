package com.kh.hyper.member.model.service;

import org.springframework.stereotype.Service;

import com.kh.hyper.member.model.dao.MemberMapper;
import com.kh.hyper.member.model.vo.Member;

import lombok.RequiredArgsConstructor;

// @Component == Bean으로 등록하겠다.
@Service	// Component보다 더 구체적으로 ServiceBean으로 등록하겠다.
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	
	
	private final MemberMapper mapper;
	
	@Override
	public Member login(Member member) {
		
		return mapper.login(member);
	}

	@Override
	public int join(Member member) {
		/*
		 * 커넥션만들기
		 * DAO호출
		 * 트랜잭션처리
		 * 자원반납
		 * 결과반환
		 */
		
		// sqlSessionTemplate이 트랜잭션 자동 commit해줌
		// spring이 자원반납해줌
		
		return mapper.join(member);
	}

	@Override
	public int updateMember(Member member) {
		return mapper.updateMember(member);
	}

	@Override
	public int deleteMember(Member member) {
		return mapper.deleteMember(member);
	}

}
