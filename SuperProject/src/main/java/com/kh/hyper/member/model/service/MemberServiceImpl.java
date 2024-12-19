package com.kh.hyper.member.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.kh.hyper.exeption.UserIdNotFoundException;
import com.kh.hyper.member.model.dao.MemberMapper;
import com.kh.hyper.member.model.vo.Member;

import lombok.RequiredArgsConstructor;

// @Component == Bean으로 등록하겠다.
@EnableTransactionManagement
@Service	// Component보다 더 구체적으로 ServiceBean으로 등록하겠다.
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	
	
	private final MemberMapper mapper;
	private final PasswordEncryptor passwordEncoder;
	
	@Override
	public Member login(Member member) {
		
		// 1. 아이디가 틀릴경우
		// 2. 비밀번호가 틀릴경우
		// 3. 통과
		
		Member loginMember = mapper.login(member);
		
		// 1.
		if(loginMember == null) {
			throw new UserIdNotFoundException("아이디가 존재하지 않습니다.");
		}
		
		// 2.
		//if()
		
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
