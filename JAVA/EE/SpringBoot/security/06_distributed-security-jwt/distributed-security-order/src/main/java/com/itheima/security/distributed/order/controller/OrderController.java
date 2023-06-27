package com.itheima.security.distributed.order.controller;

import com.itheima.security.distributed.order.model.UserDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @GetMapping(value = "/r1")
    // 会从UsernamePasswordAuthenticationToken拿到用户的权限(TokenAuthenticationFilter封装了UsernamePasswordAuthenticationToken已经),看看有没有权限访问
    @PreAuthorize("hasAuthority('p1')")//拥有p1权限方可访问此url
    public String r1(){
        // 可以获取用户身份信息
        UserDTO userDTO = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDTO.getFullname()+"访问资源1";
    }
}