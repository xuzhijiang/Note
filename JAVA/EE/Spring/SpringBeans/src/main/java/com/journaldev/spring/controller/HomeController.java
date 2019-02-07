package com.journaldev.spring.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.journaldev.spring.beans.MyAnnotatedBean;
import com.journaldev.spring.beans.MyBean;

// Spring IoC Controller Class
@Controller
@Scope("request")
public class HomeController {

	private MyBean myBean;

	private MyAnnotatedBean myAnnotatedBean;

	// 我们将通过WebApplicationContext容器将Spring bean注入此控制器类。
	@Autowired
	public void setMyBean(MyBean myBean) {
		this.myBean = myBean;
	}

	@Autowired
	public void setMyAnnotatedBean(MyAnnotatedBean obj) {
		this.myAnnotatedBean = obj;
	}

	// 1. HomeController类将处理应用程序home page的HTTP请求。
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		System.out.println("MyBean hashcode="+myBean.hashCode());
		System.out.println("MyAnnotatedBean hashcode="+myAnnotatedBean.hashCode());

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate );

		return "home";
	}

}
