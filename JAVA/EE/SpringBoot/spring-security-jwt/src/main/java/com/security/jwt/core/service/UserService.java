package com.security.jwt.core.service;

import com.security.jwt.core.domain.User;
import com.security.jwt.core.repository.UserRepository;
import com.security.jwt.core.valueobject.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserView getUserByUserName(String userName){
        UserView userView = new UserView();
        User user = userRepository.findByUserName(userName);
        userView.setUserName(user.getUserName());
        userView.setUserDesc(user.getUserDescription());
        List<String> roleCodes = new ArrayList<>();
        user.getRoles().stream().forEach(role -> roleCodes.add(role.getRoleCode()));
        userView.setRoleCodes(roleCodes);
        return userView;
    }

    /**
     * Get {@link UserDetails} by username.
     * @return
     */
    @Transactional
    public UserDetails getUserDetailByUserName(String username){
        User user = this.userRepository.findByUserName(username);
        if(user == null){
            //throw exception inform front end not this user
            throw new UsernameNotFoundException("user + " + username + "not found.");
        }
        // 获得这个用户的角色列表
        List<String> roleList = this.userRepository.queryUserOwnedRoleCodes(username);

        List<GrantedAuthority> authorities = roleList
                .stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
        // 把username,password,以及这个用户所拥有的权限封装到spring-security框架提供的User中,以便后续使用
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
    }

}
