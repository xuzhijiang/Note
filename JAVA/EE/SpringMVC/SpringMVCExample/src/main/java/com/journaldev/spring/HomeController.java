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

/*
1. HomeController被创建，定义一个home()方法，通过添加loginPage（）和login（）方法对它进行了一些扩展。
*/

/**
 * Handles requests for the application home page.
 */

// @Controller注释用于表示它是一个Web控制器类。
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	
//	3. @RequestMapping与类classes和方法methods一起使用，
//	将客户端请求重定向到特定的处理程序方法(handler method)。 
//	请注意，处理程序(handler method)方法返回String，这应该是要用作响应的视图页面的名称(the name of view page)。
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";//会去render home.jsp
	}
	
//	4. 请注意，login()方法将在client HTTP方法作为POST的时候被调用，
//	因此我们在这里期待一些表单数据。 所以我们有User模型类，它使用@Validated注释被标记为进行验证。
	// 访问http://localhost:8080/SpringMVCExample/login/，(get请求)然后会由这个loginPage处理
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Locale locale, Model model) {
		return "login";//会去render login.jsp
	}
	
//	3. 正如您所看到的，我们有三个方法(home,loginPage,login)返回不同的字符串，因此我们需要创建具有相同名称的JSP页面。
//	5. 每个方法都包含Model作为参数，我们可以设置稍后在JSP响应页面中使用的属性。
	// 接受http://localhost:8080/SpringMVCExample/home/发来的post提交，然后
	// render user.jsp返回.
	@RequestMapping(value = "/login/home", method = RequestMethod.POST)
	public String login(@Validated User user, Model model) {
		logger.info("userName is {}.", user.getUserName());
		model.addAttribute("userName", user.getUserName());
		return "user";
	}
	
}
