package com.spring.session.redis.core.service;

import com.spring.session.redis.core.common.BaseResponse;
import com.spring.session.redis.core.bean.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private HashMap<String, User> map;

    @PostConstruct
    public void init() {
        map = new HashMap<>();
        map.put("user1", new User("1", "user1", "password", "aaa", Arrays.asList("admin", "vip", "user")));
        map.put("user2", new User("2", "user2", "password", "bbb", Arrays.asList("admin")));
    }

    public User findByAccount(String account) {
        return map.get(account);
    }

    /**
     * Get {@link UserDetails} by username.
     * @return
     */
    @Transactional
    public UserDetails getUserDetailByUserName(String username){
        User user = findByAccount(username);
        if(user == null){
            throw new UsernameNotFoundException("user + " + username + "not found.");
        }

        // 获得这个用户的角色列表
        List<String> roleList = queryUserOwnedRoleCodes(username);

        List<GrantedAuthority> authorities = roleList
                .stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());

        // 把username,password,以及这个用户所拥有的权限封装到spring-security框架提供的User中,以便后续使用
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
    }

    private List<String> queryUserOwnedRoleCodes(String username) {
        User user = findByAccount(username);
        return user.getRoles();
    }

}
