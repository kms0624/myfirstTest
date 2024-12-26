package com.kh.hyper.member.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.hyper.member.model.vo.Member;

@Mapper
public interface MemberMapper {

	Member login(Member member);
	
	int join(Member member);
	
	int updateMember(Member member);
	
	int deleteMember(Member member);
	
	int test();
	
	int checkId(String userId);
}
