package com.security.jwt.core.controller;

import com.security.jwt.core.service.UserService;
import com.security.jwt.core.valueobject.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/user")
    // 在Controller方法加上具体权限限制
    // 用@PreAuthorize("hasAuthority('role')")，进行方法级别验证登录user的是否有足够的权限访问该方法，
    // 这里举例用的是admin权限。
    // hasAuthority在spring中的源码，主要是在authentication中拿到当前user所拥有的role,
    // 然后再check是否包含有访问这个方法需要的role。
    @PreAuthorize("hasAuthority('admin')")
    public UserView getUserByName(@RequestParam("userName") String userName) {
        return userService.getUserByUserName(userName);
    }

}
