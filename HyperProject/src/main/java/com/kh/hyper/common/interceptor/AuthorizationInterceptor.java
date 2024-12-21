package com.kh.hyper.common.interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kh.hyper.member.model.vo.Member;
public class AuthorizationInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("loginUser");
		String boardWriter = request.getParameter("boardWriter");
		
		
		
		if(member != null && member.getUserId().equals(boardWriter)) {
			return true;
		} else {
			session.setAttribute("alertMsg", "권한이 없어요!");
			response.sendRedirect(request.getContextPath());
			return false;
		}
	}
}