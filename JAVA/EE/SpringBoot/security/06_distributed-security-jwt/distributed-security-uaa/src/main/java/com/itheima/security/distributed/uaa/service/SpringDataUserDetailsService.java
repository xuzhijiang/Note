package com.itheima.security.distributed.uaa.service;

import com.alibaba.fastjson.JSON;
import com.itheima.security.distributed.uaa.dao.UserDao;
import com.itheima.security.distributed.uaa.model.UserDto;
import com.itheima.security.distributed.uaa.model.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpringDataUserDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    //根据 账号查询用户信息
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //将来连接数据库根据账号查询用户信息
        UserDto userDto = userDao.getUserByUsername(username);
        if(userDto == null){
            //如果用户查不到，返回null，由provider来抛出异常
            return null;
        }

        //根据用户的id查询用户的权限
        List<String> permissions = userDao.findPermissionsByUserId(userDto.getId());
        //将permissions转成数组
        String[] permissionArray = new String[permissions.size()];
        permissions.toArray(permissionArray);

        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userDto, userVo);
        // 将userVo转成json,
        String jsonString = JSON.toJSONString(userVo);
        UserDetails userDetails = User.withUsername(jsonString)
                .password(userDto.getPassword())
                .authorities(permissionArray)
                .build();

        return userDetails;
    }
}
