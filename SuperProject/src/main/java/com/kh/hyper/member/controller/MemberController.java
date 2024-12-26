package com.kh.hyper.member.controller;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kh.hyper.common.ModelAndViewUtil;
import com.kh.hyper.member.model.service.MemberService;
import com.kh.hyper.member.model.vo.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
// Component와 Controller 둘 다 사용 가능
// Servlet-context.xml에 annotation-driven이 찾아줌
@Slf4j						// log를 사용가능하게 함 (pom.xml에 있는 것)
@Controller
@RequiredArgsConstructor	// lombok에서 제공하는 애노테이션으로 필드부에 파이널로 만든걸 보고 자동으로 생성자(생성자 주입)를 만들어준다
							// final이 붙은 것 만 생성자매개변수를 생성해줌 private String name; => X
							// 이걸 사용하면 Autowired를 사용하지 않아도 된다
//@RequestMapping(value="member")
public class MemberController {
	private final MemberService memberService;
	//private final BCryptPasswordEncoder passwordEncoder;
	private final ModelAndViewUtil mv; // 모델앤드뷰유틸을 커먼에 템플릿처럼 만들어놓음
	
	
	/*
	// @Autowired // 필드 주입	=> 쓸게 적다는 장점 끝~ 단점 : 순환의존성이 있을 수 있다, Service의 필드부분이 있을 수 있는데 그걸 체크하기 힘듦.
				//bean을 체크하는게 MemberController을 직접 시키기전까지는 알 수 없다.
	private MemberService memberService;
	private BCryptPasswordEncoder passwordEncoder;	// 암호화 사용하기 위해서
	
	// Spring D.I(Dependency Injection)
	@Autowired	// 줄을달아서 빈에서 타입을 보고 찾아서 넣어주는 행위를 해준다 => 생성자 주입	(필드 주입보다는 정석적이고 권장하는 방법)
				// 매개변수와 일치하는 타입의 Bean객체를 검색해서 인자값으로 주입을 해줌!
	public MemberController(MemberService memberService, BCryptPasswordEncoder passwordEncoder) {
		this.memberService = memberService;
		this.passwordEncoder = passwordEncoder;
	}
	*/
	
	
	
	/*
	 * public void login(String userId, String userPwd){
	 *
	 * }
	 *
	 * public void save(String userId, ....){
	 * 	new MemberView().success();
	 * }
	 *
	 * 내가 만든 빈이기 때문에 수정이 가능하다
	 *
	 * 컨트롤러까지는 왔는데 어떤 메소드한테 주어야하는지 어떻게 판단하니?
	 * 핸들러 매핑이라는 클래스가 각각 매핑을 해서 핸들러를 처리해주어야 하는데
	 * 이 클래스로 doDispatch() 메소드로 찾아가는데
	 * 찾아가서 getHandler()메소드를 호출해서 요청한 핸들러가 있는지 확인하고 없으면 그냥 돌려보냄
	 * 아래 @RequestMapping(value="login.me")를 해서 bean으로 등록되었기 때문에 사용 가능
	 *
	 * @ 매핑값이 겹치면 서버킬때 오류가 생김 - 해결방안은 controller 자체에 RequsetMapping을 할 수 있다.
	 *
	 */
	/*
	 * login.me
	 * insert.me
	 * update.me
	 */
	/*
	@RequestMapping(value="login.me")	// RequestMapping타입의 애노테이션을 등록함으로써 HandlerMapping을 등록할 수 있다 (빈으로 등록)
	public void login() {
		System.out.println("로그인 요청을 보냈니?");
	}
	
	@RequestMapping(value="insert")
	public void insert() {
		System.out.println("추가할래?");
	}
	
	@RequestMapping(value="delete")
	public void delete() {
		System.out.println("삭제할래?");
	}
	*/
	
	/*
	 * Spring에서 요청 시 전달값(Parameter)를 받아서 사용하는 방법
	 *
	 * 1. HttpServletRequest를 이용해서 전달받기 (기존의 JSP / Servlet 방식)
	 *
	 * 핸들러의 매개변수로 HttpServletRequest타입을 작성해두면
	 * DispatcherServlet이 해당 메소드를 호출할 때 request객체를 전달해서 매개변수로 주입해줌!
	 *
	 * DispatcherServlet이 request 선언만해도 아규먼트 뭐시기 해서 보내준다
	 *
	 * return "경로" 경로로 바로 간다
	 */
	
	/*
	@RequestMapping(value="login.me")
	public String login(HttpServletRequest request) {
		
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		
		System.out.println(id);
		System.out.println(pwd);
		
		return "main";
	}
	*/
	
	/*
	 * 2. @RequestParam 애노테이션을 이용하는 방법
	 *
	 * request.getParameter("키")로 벨류를 뽑아오는 역할을 대신해주는 애노테이션
	 * value속성의 값으로 jsp작성한 키 값을 적으면 알아서 해당 매개변수에 주입을 해줌
	 * @RequestParam 속성안에 defaultValue에 작성한 값이 아무것도 안적으면 작성한 값이 출력된다.
	 * 만약, 넘어온 값이 비어있는 형태라면 defaultValue속성으로 기본값을 지정할 수 있음
	 */
	/*
	@RequestMapping("login.me")
	public String login(@RequestParam(value="id", defaultValue="user01") String id,@RequestParam(value="pwd") String pwd) {
		
		System.out.println(id);
		System.out.println(pwd);
		
		return "main";
	}
	*/
	
	/*
	 * 3. RequestParam애노테이션을 생략하는 방법
	 * 생략하면 spring이 알아서 달아준다
	 * 단, 매개변수 명이 jsp에서 전달한 key 값과 동일하게 세팅해둬야 자동으로 값이 주입되어야 한다.
	 * 단점은 키값에 의미가 명확하지 않을 수 있고, defaultValue속성을 사용할 수 없다.
	 */
	/*
	@RequestMapping("login.me")
	public String login(String id, String pwd) {
		
		System.out.println(id);
		System.out.println(pwd);
		
		Member member = new Member();
		member.setUserId(id);
		member.setUserPwd(pwd);
		
		return "main";
	}
	*/
	
	/*
	 * 4. 커맨드 객체 방식
	 *
	 * 1. 전달되는 키값과 객체의 필드명이 동일해야 한다.
	 * 2. 기본생성자가 반드시 존재해야 한다.
	 * 3. setter메소드가 반드시 존재해야함
	 *
	 * 매개변수 생성할 때 타입을 본다.
	 * 알아서 만들 수 없는 타입이면 spring이 가지고 있는 클래스를 찾아서
	 * 앞에 @을 붙여서 기본생성자를 찾는다.
	 * 기본생성자가 있다면 객체를 생성한다.
	 * 객체를 생성해서 필드를 화인해서
	 * 앞단에서 넘어온 name 속성의 값이 필드명과 같다면
	 * vlaue값을 setter()를 찾아 이용해서 필드에 값을 저장한다.
	 * 이 현상을 => setter injection
	 */
	
	/*
	@RequestMapping("login.me")			여기엔 잘 안붙임 컨트롤러 가리킬때 정도 붙임RequestMapping
	public String login(Member member) {
		
		// System.out.println(member);
		
		// new MemberServiceImpl().login(member);
		// Service 클래스의 수정이 일어날 경우 Service 클래스를 의존하고 있던 Controller가 영향을 받게됨!! 아 어이없네?
		
		Member loginMember = memberService.login(member);
		
		if(loginMember != null) {
			System.out.println("로그인 성공!");
		} else {
			System.out.println("로그인 실패!");
		}
		
		return "main";
		// ModelAndView 안에 View 안에 viewName이라는 필드가 있는데 String으로 returng난 main은 viewName필드로 들어가서 viewName = "main"를 담은 mv
		// mv로 반환이 되는데 mv를 가지고 render()에 들고간다
	}
	*/
	
	
	/*
	 * Client의 요청 처리 후 응답데이터를 담아서 응답페이지로 포워딩 또는 URL재요청 하는 방법
	 *
	 * 1. 스프링에서 제공하는 Model객체를 사용하는 방법
	 * 포워딩할 응답 뷰로 전달하고자 하는 데이터를 맵형식(key-value)으로 담을 수 있는 영역
	 * Model객체는 requestScope
	 *
	 * 단, setAttribute()가 아닌 addAttribute()를 호출해야함
	 *
	 *
	 *
	 */
	// @Post Get Put Delete 이런거로 구분하기 때문에 mapping값을 board로 같게 써도 된다.
	/*
	@PostMapping("login.me")
	public String login(Member member, Model model, HttpSession session) {
		
		Member loginMember = memberService.login(member);
		
		if(loginMember != null) {	// 정보가 있다 => loginMember를 sessionScope에 담기
			
			session.setAttribute("loginUser", loginMember);
			
			//return "main"; => forwarding -> Redirect
			
			// sendRedirect
			// localhost/hyper/
			
			// redirect:요청할URL
			
			return "redirect:/";
			
		} else { // 로그인 실패 => 에러문구를 requestScope에 담아서 에러페이지로 포워딩
			
			model.addAttribute("errormemberServiceg", "로그인 실패!");
			
			/*
			 * \/WEB-INF/views/common/error_page.jsp
			 *
			 * String Type return -> viewName에 대입
			 * -> DispatcherServlet -> ViewResolver
			 *
			 * - prefix : /WEB-INF/views
			 *
			 * - 중간 : return viewName;
			 *
			 * - suffix : .jsp
			 *
			
			return "common/error_page";
		}
	}
	*/
	
	/*
	 * 2. ModelAndView타입을 사용하는 방법
	 *
	 * Model은 데이터를 key-value세트로 담을 수 있는 객체
	 * View는 응답 뷰에 대한 정보를 담을 수 있음 ( 인터페이스이기 때문에 단독으로 단독으로 가져올 수 없음)
	 *
	 * Model객체와 View가 결합된 형태의 객체
	 */
	@PostMapping("login.me")
	public ModelAndView login(Member member, HttpSession session) {
		
		// 사용자가 입력한 비밀번호 : member => memberPwd필드(평문 == plaintext)
		
		// Member Table의 USER_PWD컬럼에는 암호문이 들어있기 때문에
		// WHERE 조건절의 결과가 절대로 true가 될 순 없음
		
		Member loginMember = memberService.login(member);
		
		// Member타입의 loginMember의 userPwd 필드 : DB에 기록된 암호화된 비밀번호
		// Member타입의 member의 userPwd 필드 : 사용자가 입력한 평문 비밀번호
		
		// $2a$10$OLx4iHr7GF9PN8ETsGOKFeoQRYLmB9Qp.NvxJm1y2.nkVgjh6htHS
		// 1234
		
		// 평문에서 암호문으로 바꾸는 방법이 있는데 같은소금으로 같은 횟수로 소금치면 같은 암호문이 나온다
		// BCryptPasswordEncoder 객체 matches()
		// matches(평문, 암호문)
		// 암호문에 포함되어있는 버전과 반복횟수와 소금값을 가지고 인자로 전달된 평문을 다시 암호화를 거쳐서
		// 두 번째 인자로 전달된 암호문과 같은지 다른지를 비교한 결과값을 반환
		
		
		
		
		/*
		if(loginMember != null && passwordEncoder.matches(member.getUserPwd(), loginMember.getUserPwd())) {
			// 숏서킷 - and연산자쓸때 앞에가 false이면 뒤에꺼를 검사하지 않는다.
			session.setAttribute("loginUser", loginMember);
			session.setAttribute("alertMsg", "로그인에 성공했습니다");
			mv.setViewName("redirect:/");
		} else {
			// model.addAttribute
			// mv.addObject("errormemberServiceg", "로그인실패...");		addObject가 mv형태라 메소드체이닝 가능
			// mv.setViewName("common/errorPage");
			mv.addObject("errorMsg", "로그인실패...").setViewName("common/error_page");
		}
		 */
		
		session.setAttribute("loginUser", loginMember);
		session.setAttribute("alertMsg", "로그인에 성공했습니다~");
		//mv.setViewName("redirect:/");
		
		return mv.setViewNameAndData("redirect:/", null);
	}
	
	@GetMapping("logout.me")
	public String logout(HttpSession session) {
		session.removeAttribute("loginUser");
		return "redirect:/";
	}
	
	/*
	 @GetMapping
	 public ModelAndView log(HttpSession session, ModelAndView mv){
	 	session.removeAttribute("loginUser");
	
	 }
	 */
	
	@GetMapping(value="enrollform.me")
	public String enrollForm() {
		return "member/enroll_form";
	}
	
	
// 회원가입 실패했을 경우도 생각해서 string이 아닌 modelandview로 해야함(오류메세지 보내야하므로)
	@PostMapping("sign-up.me")
	public ModelAndView singUp(Member member, HttpSession session ) {
		// 매개변수에는 내가 가공할 타입을 적어준다
		// + modelandview를 스프링에게 받아야함
		
		/*
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		*/
		
		System.out.println(member);
		log.info("{}", member);
		// 문제점 1. 한글 문자가 깨짐 -> post방식으로 왔으니깐 인코딩을 해야함
		//-> request로 해야하니깐 달라고 해야함(매개변수 httpServlet~) -> 받음 다음에 request.setCharacter~
		//-> UnsupportedEncodingException 발생 예외처리를 해줘야함(throws가 없어서 직접해줘야함)
		// 이렇게 해도 한글이 깨짐 왜? 값을 뽑기전!!에 미리 UTF-8방식으로 변경해주어야하기때문에! -> 우린 request에서 값을 다 뽑고 다 담고나서 인코딩함 이건 의미가 없어
		// 그럼 어떻게해야함? -> 한글부분만 다시 값을 뽑아서 담는 방법도 생각해볼 수 있음 member.setUserName(request.getParameter("userName");
		// => 우린 filter라는걸 사용해볼것!! 제공이 되어서 만들어진것만 사용하면 됨 -> 스프링에서 제공하는 "인코딩filter"를 사용하자!!
		
		//인코딩filter --> 어디서 등록하냐-> web.xml
		
		// 문제점 2. 나이를 입력하지 않았을 경우 int자료형 age필드에 빈문자열이 Bind되기 때문에
		// Parsing과정에서 String -> int NumberFormatException이 발생함
		// 클라이언트에게 400 Bad Request가 뜸
		
		// 문제점 3. 비밀번호가 사용자가 입력한 그대로의 평문(plain text)
		// Bcrypt 이용해서 암호화 -> Spring Security Modules에서 제공(pom.xml)
		// passwordEncoder를 .xml파일을 이용해서 configurationBean으로 Bean으로 등록
		// ==> web.xml에서 spring-security, xml파일을 로딩할 수 있도록 추가
		
		// 평문 출력
		//System.out.println("평문 : " + member.getUserPwd());
		//log.info("평문 : {}", member.getUserPwd());
		// 암호화 작업(암호문을 만드는 방법)
		//String encPwd = passwordEncoder.encode(member.getUserPwd());
		// 암호문 출력
		//System.out.println("암호문 : " + encPwd);
		//log.info("암호문 : {}", encPwd);
		
		//member.setUserPwd(encPwd);
		// Member객체 userPwd필드에 평문이 아닌 암호문을 담아서 INSERT를 수행
		/*
		int result = memberService.join(member);
		
		if(result > 0) {
			session.setAttribute("alertMsg", "회원가입에 성공했습니다.");
			mv.setViewName("redirect:/");
		} else {
			mv.addObject("errorMsg", "회원가입실패").setViewName("common/error_page");
		}
		*/
		memberService.join(member);
		session.setAttribute("alertMsg", "기입성공성공");
		//mv.setViewName("redirect:/");
		return mv.setViewNameAndData("redirect:/", null);
	}
	
	@GetMapping("mypage.me")
	public String mypage() {
		
		return "member/my_page";
	}
	
	@PostMapping("update.me")
	public ModelAndView update(Member member, HttpSession session) {
		/*
		if(memberService.updateMember(member) > 0) {
			// DB로부터 수정된 회원정보를 다시 조회해서
			// sessionScope의 loginUser라는 키값으로 덮어씌울것!
			
		} else { // 수정 실패 => 에러문구를 담아서 에러페이지로 포워딩
			
			mv.addObject("errorMsg", "정보수정에 실패했습니다.").setViewName("common/error_page");
		}
		*/
		memberService.updateMember(member, session);
		session.setAttribute("alertMsg", "정보수정에 성공했습니다!!");
		//mv.setViewName("redirect:mypage.me");
		return mv.setViewNameAndData("redirect:mypage.me", null);
	}
	
	@PostMapping("delete.me")
	public ModelAndView delete(String userPwd, HttpSession session) {
		
		/*
		// userPwd : 회원 탈퇴 시 요청 시 사용자가 입력한 비밀번호 평문
		// session의 loginUser객체의 userPwd필드 : DB에 기록된 암호화된 비밀번호
		
		Member loginUser = ((Member)session.getAttribute("loginUser"));
		
		String encPwd = loginUser.getUserPwd();
		
		if(passwordEncoder.matches(userPwd, encPwd)) {
			// 비밀번호가 사용자가 입력한 평문을 이용해서 만든 비밀번호일 경우
			if(memberService.deleteMember(loginUser, session) > 0) {
				
				session.removeAttribute("loginUser");
				session.setAttribute("alertMsg", "잘가라~~");
				return "redirect:/";
				
			} else {
				session.setAttribute("alertMsg", "관리자에게 문의하세요~ 안됐어요~ 까비~");
				return "common/error_page";
			}
			
			
		} else {
			session.setAttribute("alertMsg", "비밀번호가 일치하지 않습니다.");
			return "redirect:mypage.me";
		}
		*/
		
		memberService.deleteMember(userPwd, session);
		session.removeAttribute("loginUser");
		session.setAttribute("alertMsg", "잘가시오~");
		//mv.setViewName("redirect:/");
		return mv.setViewNameAndData("redirect:/", null);
	}
	
	@ResponseBody
	@GetMapping(value="idcheck")
	public String checkId(String userId) {
		
		//String result = memberService.checkId(userId);
		return memberService.checkId(userId);
	}
	
	
	// key, value가 몇개가 넘어올 지 모르기때문에 아래 코드는 한개만 담을 수 있어서 좀 애매하다~
	// board notice 등등 사용할 것 같다.	=> 템플릿도 괜찮은데 한개밖에 한담아서 좀 애매
	private ModelAndView setViewNameAndData(String viewName, String key, Object data) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(viewName);
		if(key != null && data != null) {
			mv.addObject(key, data);
		}
		return mv;
	}
	
}