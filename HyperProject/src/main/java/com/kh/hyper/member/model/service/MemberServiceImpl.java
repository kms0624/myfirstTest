package com.kh.hyper.member.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.kh.hyper.member.model.dao.MemberMapper;
import com.kh.hyper.member.model.vo.Member;

import lombok.RequiredArgsConstructor;

// @Component == Bean으로 등록하겠다.
@EnableTransactionManagement	// spring에서의 트랜잭션하기위한 방법
@Service	// Component보다 더 구체적으로 ServiceBean으로 등록하겠다.
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	
	//private final MemberDao memberDao;
	//private final SqlSessionTemplate sqlSession; // 기존의 myBatis의 sqlSession 대체
	
	private final MemberMapper mapper;
	
	/*
	@Autowired
	public MemberServiceImpl(MemberDao memberDao, SqlSessionTemplate sqlSession) {
		this.memberDao = memberDao;
		this.sqlSession = sqlSession;
	}
	*/
	
	@Override
	public Member login(Member member) {
		
		/*
		 * SqlSession sqlSession = getSqlSession();
		 * 
		 * Member member = new MemberDao().login(sqlSession, member);
		 * 
		 * sqlSession.close();
		 * 
		 * return member;
		 */
		
		//Member loginUser = memberDao.login(sqlSession, member);
		// 스프링이 사용 후 자동으로 객체를 반납해주기 때문에 close() 호출하지 않음!
		return mapper.login(member);
	}

	@Override
	@Transactional	// member테이블에 회원가입한 insert는 코드상 가능했지만 트랜잭션을 커밋하지 않고 롤백해버림( 다른 쿼리문이 다 성공해야 커밋한다 )
					// Spring에서의 트랜잭션처리방법
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
		// mapper.test();
		// 트랜잭션이 join은 처리가 되고 test는 트랜잭션을 처리(sqlSessionTemplate가 처리)할 수 가 없어서 오류가 발생(하지만 join은 insert했다)
		
		return mapper.join(member);
	}

	@Override
	public int updateMember(Member member) {
		return mapper.updateMember(member);
	}

	@Override
	public int deleteMember(Member member) {
		System.out.println(member.getModifyDate());
		return mapper.deleteMember(member);
	}

}
