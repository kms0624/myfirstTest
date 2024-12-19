package com.kh.hyper.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

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
//@RequestMapping(value="member")
public class MemberController {

	private final MemberService memberService;
	//private final BCryptPasswordEncoder passwordEncoder;
	//private final ModelAndViewUtil mv;
	
	@PostMapping("login.me")
	public ModelAndView login(Member member, HttpSession session, ModelAndView mv) {
		
		//System.out.println(passwordEncoder.encode(member.getUserPwd()));
		
		Member loginMember = memberService.login(member);
		if(loginMember != null && passwordEncoder.matches(member.getUserPwd(), loginMember.getUserPwd())) {
			System.out.println(loginMember);
			session.setAttribute("loginUser", loginMember);
			session.setAttribute("alertMsg", "로그인에 성공했습니다.");
			mv.setViewName("redirect:/");
		} else {
			mv.addObject("failMsg", "로그인에 실패하셨습니다.").setViewName("common/error_page");
		}
		return mv;
	}
	
	@GetMapping("logout.me")
	public String logout(HttpSession session) {
		session.removeAttribute("loginUser");
		return "redirect:/";
	}
	
	@GetMapping("join.me")
	public String join() {
		return "member/enroll_form";
	}
	
	@GetMapping("sign-up.me")
	public ModelAndView signUp(Member member, ModelAndView mv, HttpSession session) {

		String encPwd = passwordEncoder.encode(member.getUserPwd());
		
		member.setUserPwd(encPwd);
		int result = memberService.join(member);
		
		if(result > 0) {
			session.setAttribute("alertMsg", "회원가입에 성공했습니다.");
			mv.setViewName("redirect:/");
		} else {
			mv.addObject("failMsg", "회원가입실패").setViewName("common/error_page");
		}
		return mv;
	}
	
	@GetMapping("myPage.me")
	public String myPage() {
		return "member/my_page";
	}
	
	@PostMapping("update.me")
	public ModelAndView update(Member member, ModelAndView mv, HttpSession session) {
		int result = memberService.updateMember(member);
		
		if(result > 0) {
			session.setAttribute("loginUser", memberService.login(member));
			session.setAttribute("alertMsg", "회원정보수정에 성공했습니다.");
			mv.setViewName("redirect:myPage.me");
		} else {
			mv.addObject("failMsg", "회원정보수정에 실패했습니다.").setViewName("common/error_page");
		}
		return mv;
	}
	
	@PostMapping("delete.me")
	public String delete(Member member, HttpSession session) {
		
		Member loginUser = ((Member)session.getAttribute("loginUser"));
		if(passwordEncoder.matches(member.getUserPwd(), loginUser.getUserPwd())) {
			if(memberService.deleteMember(loginUser) > 0) {
				
				session.removeAttribute("loginUser");
				session.setAttribute("alertMsg", "탈퇴성공!");
				return "redirect:/";
			} else {
				session.setAttribute("failMsg", "탈퇴 실패했습니다. 관리자에게 문의바랍니다.");
				return "common/error_page";
			}
		} else {
			session.setAttribute("alertMsg", "비밀번호를 잘 못 입력하셨습니다.");
			return "redirect:myPage.me";
		}
	}
}
