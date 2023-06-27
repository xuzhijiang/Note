package com.spring.session.redis.core.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable{
    private String id;
    private String account;
    private String password;
    private String name;
    private List<String> roles;
}
