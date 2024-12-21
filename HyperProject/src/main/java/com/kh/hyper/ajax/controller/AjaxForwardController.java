package com.kh.hyper.ajax.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AjaxForwardController {

	@GetMapping("/ajax-study")
	public String studing() {
		return "ajax/study";
	}
	
	
	
}
