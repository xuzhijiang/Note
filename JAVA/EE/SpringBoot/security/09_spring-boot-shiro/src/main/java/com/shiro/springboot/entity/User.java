package com.shiro.springboot.entity;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class User implements Serializable {
    /**
     * id
     */
    @Id
    @GeneratedValue
    private Integer uid;
    /**
     * 帐号
     */
    @Column(unique =true)
    private String username;
    /**
     * 昵称或者真实姓名
     */
    private String name;
    /**
     * 加盐加密之后的密码
     */
    private String password;
    /**
     * 盐值
     */
    private String salt;
    /**
     * 用户状态
     * 0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户
     * 1:正常状态
     * 2：用户被锁定.
     */
    private byte state;
    /**
     * 拥有的权限
     */
    private String perms;
}