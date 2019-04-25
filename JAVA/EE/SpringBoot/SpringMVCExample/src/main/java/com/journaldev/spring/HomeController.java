package com.journaldev.spring;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//	每个方法都包含Model作为参数，我们可以设置稍后在JSP响应页面中使用的属性。
/**
 * Handles requests for the application home page.
 */
// @Controller注释用于表示它是一个Web控制器类。
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate);
		
		return "home";//会去render home.jsp
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Locale locale, Model model) {
		return "login";//会去render login.jsp
	}
	

	//	因此我们在这里期待一些表单数据。 所以我们有User模型类，它使用@Validated注释被标记为进行验证。
	@RequestMapping(value = "/login/home", method = RequestMethod.POST)
	public String login(@Validated User user, Model model) {
		logger.info("userName is {}.", user.getUserName());
		model.addAttribute("userName", user.getUserName());
		return "user";// render user.jsp返回.
	}
	
}
