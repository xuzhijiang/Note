package com.journaldev.spring.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

//首先，使用Spring的@Controller注释开发Login Controller。

//代码说明： - 我们在“LoginController”中定义了三种方法来处理三种不同类型的客户端请求

//welcomePage（）将处理所有使用“/”URI的客户端请求。
//homePage（）将处理使用“/homePage”URI的所有客户端请求。
//loginPage（）将处理使用“/loginPage”URI的所有客户端请求。
//在loginPage（）中，我们负责处理错误和注销消息。
@Controller
public class LoginController {

	@RequestMapping(value = { "/"}, method = RequestMethod.GET)
	public ModelAndView welcomePage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("welcomePage");
		return model;
	}

	@RequestMapping(value = { "/homePage"}, method = RequestMethod.GET)
	public ModelAndView homePage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("homePage");
		return model;
	}
	
	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	public ModelAndView loginPage(@RequestParam(value = "error",required = false) String error,
	@RequestParam(value = "logout",	required = false) String logout) {
		
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid Credentials provided.");
		}

		if (logout != null) {
			model.addObject("message", "Logged out from JournalDEV successfully.");
		}

		model.setViewName("loginPage");
		return model;
	}

}