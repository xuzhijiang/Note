package spring.security.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    private final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping("/")
    public String index() {
        logger.info("access index /");
        return "index";
    }

    @GetMapping("/home")
    public String home() {
        return "/home";
    }

    @GetMapping("/admin")
    public String admin() {
        return "/admin";
    }

    @GetMapping("/about")
    public String about() {
        return "/about";
    }

    @GetMapping("/user")
    public String user() {
        return "/user";
    }

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

//    @GetMapping("/login0")
//    String login0() {
//        return "/login_0";
//    }

    @GetMapping("/api")
    @ResponseBody
    public String[] api() {
        return "Spring Boot Security".split(" ");
    }

    // 以user的 身份登录,然后访问/admin/下的内容,就会跳到这里.
    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }

}