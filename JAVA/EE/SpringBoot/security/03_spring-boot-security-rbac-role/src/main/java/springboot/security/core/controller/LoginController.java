package springboot.security.core.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/403")
    public String error403() {
        return "403";
    }

    // 在SecurityConfig中配置了访问/admin 需要的角色
    @ResponseBody
    @GetMapping("/admin")
    public String admin() {
        return "我是管理员: " + getUsername();
    }

    /**
     * 测试资源1
     */
    @ResponseBody
    @GetMapping(value = "/r/r1", produces = {"text/plain;charset=utf-8"})
    @PreAuthorize(("hasRole('ADMIN')")) //拥有ADMIN角色才可以访问
    public String r1() {
        return getUsername() + "访问资源1";
    }

    /**
     * 测试资源2
     */
    @ResponseBody
    @GetMapping(value = "/r/r2",produces = {"text/plain;charset=UTF-8"})
    @PreAuthorize(("hasAnyRole('USER')")) //拥有USER角色才可以访问
    public String r2(){
        return getUsername()+" 访问资源2";
    }

    //获取当前用户信息
    public String getUsername() {
        String username = null;
        //当前认证通过的用户身份
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 获取用户身份
        Object principal = authentication.getPrincipal();
        if (principal == null) {
            username = "匿名";
        }
        if (principal instanceof UserDetails) {
            UserDetails userDetails  = (UserDetails) principal;
            username = userDetails.getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

}
