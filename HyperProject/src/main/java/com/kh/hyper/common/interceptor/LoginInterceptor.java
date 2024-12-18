package com.kh.hyper.common.interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
public class LoginInterceptor extends HandlerInterceptorAdapter{
	/*
	 * InterCeptor(정확히는 HandlerInterceptor)
	 *
	 * RequestHandler가 호출되기 전, 실행한 후 가로채서 실행할 내용을 작성 가능
	 *
	 * preHandler (전 처리) : 핸들러 호출 전 낚아챔
	 * postHandler (후 처리) : 요청 처리 후 낚아챔
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
			
		// true : 기존 요청 호출대로 Handler를 정상수행 ==> Controller에 있는 메소드 호출
		// false : Handler호출 안함 ~ 끝 ~
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("loginUser") != null) {
			return true;
		} else {
			session.setAttribute("alertMsg", "권한이 없어요!");
			response.sendRedirect(request.getContextPath());
			return false;
		}
	}
}