package com.kh.hyper.common;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component	// 컨트롤러도 아니고 서비스도 아니고 맵도 아니니깐 애매할 땐 Component해서 빈등록하기
public class ModelAndViewUtil {
	
	/*
	public ModelAndViewUtil() {	// 인스턴스화
		
	}
	*/
	
	
	public ModelAndView setViewNameAndData(String viewName, Map<String, Object> modelData) {
		// static의 장점 리소스가 적게 든다 / 의존관계가 명확하지 않아서 단점
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName(viewName);
		if(modelData != null) {
			mv.addAllObjects(modelData);	// 여러개의 키 밸류값을 ModelAndView에 담다는 법
		}
		
		return mv;
	}
	
	
	
}
