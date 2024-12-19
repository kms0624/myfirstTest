package com.kh.hyper.member.model.service;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.kh.hyper.exeption.ComparePasswordException;
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
	private final PasswordEncryptor passwordEncoder;
	private final MemberValidator validator;
	
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
		
		
		// 1. 아이디가 존재하지 않는 경우 
		// 2. 비밀번호가 잘못된 경우
		// 3. 둘 다 통과해서 정상적으로 조회
		/*
		Member loginMember = mapper.login(member);
		
		// 1. 아이디가 존재하지 않을 경우
		if(loginMember == null) {
			throw new UserIdNotFoundException("존재하지 않는 아이디로 접속요청");
		}
		*/
		Member loginMember = validator.validateMemberExists(member);
		
		// 2. 비밀번호가 일치하지 않을 경우
		if(!!!passwordEncoder.matches(member.getUserPwd(), loginMember.getUserPwd())) {
			throw new ComparePasswordException("비밀번호가 일치하지 않습니다.");
		} else {
			return loginMember;
		}
	}

	@Override
	@Transactional	// member테이블에 회원가입한 insert는 코드상 가능했지만 트랜잭션을 커밋하지 않고 롤백해버림( 다른 쿼리문이 다 성공해야 커밋한다 )
					// Spring에서의 트랜잭션처리방법
	public void join(Member member) {
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
		// 예외처리( 예외발생 코드 오류가 어떤게 발생했는지를 보고 그걸 catch문에 작성해서 어떤부분에서 발생했는지를 찾아서 try-catch문을 사용하여 예외처리를 해준다)
		/*
		try {
			mapper.join(member);
			return 1;
		} catch(DuplicateKeyException e) {
			return 0;
		}
		*/
		
		// case 1. 사용자가 입력한 아이디값이 userId컬럼에 존재하면 안됨
		// case 2. 사용자가 입력한 아이디값이 30글자가 넘어가선 안됨
		// case 3. 위 두 조건을 만족했다면 사용자가 입력한 비밀번호값을 암호화해서 DB에 INSERT
		/*
		Member userInfo = mapper.login(member);
		if(userInfo != null && member.getUserId().equals(userInfo.getUserId())) {
			throw new UserFoundException("이미 존재하는 아이디입니다.");
		}
		
		if(member.getUserId().length() > 30 || member.getUserId().equals("")) {
			throw new TooLargeValueException("값의 길이기 너무 깁니다.");
		}
		*/
		// 자바스크립트에서 썼던 정규표현식 자바에서도 matches사용해서 쓸 수 있음
		validator.validateJoinMember(member);
		member.setUserPwd(passwordEncoder.encode(member.getUserPwd()));
		mapper.join(member);
	}
	
	
	@Override
	public void updateMember(Member member, HttpSession session) {
		
		// 세션도 같이 받아서 앞단에서 넘어온 userId와 session의 loginUser키값의 userId필드값에 동일한지 확인
		// -> 사용자가 입력한 userId값이 DB컬럼에 존재하는지
		// 사용자가 입력한 업데이트 할 값들이 컬럼의 크기가 넘치지 않는지 || 제약조건에 부합하는지
		/*
		Member userInfo = mapper.login(member);
		System.out.println(userInfo);
		if(userInfo == null) {
			throw new UserIdNotFoundException("존재하지 않는 사용자입니다.");
		}
		*/
		validator.validateMemberExists(member);
		mapper.updateMember(member);
		session.setAttribute("loginUser", mapper.login(member));
	}

	@Override
	public void deleteMember(String userPwd, HttpSession session) {
		
		Member loginUser = (Member)session.getAttribute("loginUser");
		loginUser.setUserPwd(userPwd);
		
		/*
		Member userInfo = mapper.login(loginUser);
		if(userInfo == null) {
			throw new UserIdNotFoundException("존재하지 않는 사용자입니다.");
		}
		
		if(!(passwordEncoder.matches(loginUser.getUserPwd(), userInfo.getUserPwd()))) {
			throw new ComparePasswordException("비밀번호가 일치하지 않습니다.");
		}
		*/
		Member userInfo = validator.validateMemberExists(loginUser);
		
		mapper.deleteMember(userInfo);
	}

}
