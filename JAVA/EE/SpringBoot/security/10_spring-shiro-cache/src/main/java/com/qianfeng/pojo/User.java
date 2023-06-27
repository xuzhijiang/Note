package com.qianfeng.pojo;


import com.qianfeng.dao.UserDAO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private String salt;
    public static void main(String[] args) {
        Class<? extends UserDAO> aClass = UserDAO.class;
        System.out.println(aClass.getCanonicalName());
        System.out.println(aClass.getSimpleName());
        System.out.println(1);
    }
}
