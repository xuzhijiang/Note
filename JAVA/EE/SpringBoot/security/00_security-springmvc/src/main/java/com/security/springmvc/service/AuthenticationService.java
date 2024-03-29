package com.security.springmvc.service;

import com.security.springmvc.model.AuthenticationRequest;
import com.security.springmvc.model.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class AuthenticationService {

    /**
     * 用户认证，校验用户身份信息是否合法
     *
     * @param authenticationRequest 用户认证请求, 包含前端上传上来的账号和密码
     * @return 认证成功后,返回用户信息
     */
    public UserDto authentication(AuthenticationRequest authenticationRequest) throws Exception{
        // 校验参数是否为空
        if (authenticationRequest == null
                || StringUtils.isEmpty(authenticationRequest.getUsername())
                || StringUtils.isEmpty(authenticationRequest.getPassword())) {
            throw new RuntimeException("账号和密码为空");
        }

        // 根据username去查询数据库,这里测试程序采用模拟方法
        UserDto user = getUserDto(authenticationRequest.getUsername());
        //判断用户是否为空
        if (user == null) {
            throw new RuntimeException("查询不到该用户");
        }

        // 拿到查询出的用户, 校验密码
        if (!authenticationRequest.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("账号或密码错误");
        }

        //认证通过，返回用户身份信息
        return user;
    }

    // 根据账号(username)查询用户信息
    private UserDto getUserDto(String userName){
        return userMap.get(userName);
    }

    // 用户信息(这个相当于是数据库中存放的用户信息)
    private Map<String,UserDto> userMap = new HashMap<>();

    @PostConstruct
    public void init() {
        Set<String> authorities1 = new HashSet<>();
        authorities1.add("p1");//这个p1我们人为让它和/r/r1对应
        Set<String> authorities2 = new HashSet<>();
        authorities2.add("p2");//这个p2我们人为让它和/r/r2对应
        userMap.put("zhangsan",new UserDto("1010","zhangsan","123","张三","133443",authorities1));
        userMap.put("lisi",new UserDto("1011","lisi","456","李四","144553",authorities2));
    }

}
