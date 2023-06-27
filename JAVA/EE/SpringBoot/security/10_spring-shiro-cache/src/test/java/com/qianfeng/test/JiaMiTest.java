package com.qianfeng.test;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;

import java.util.UUID;

/**
 * @ClassName JiaMiTest
 * @Description: TODO
 * @Author 臧红久
 * @Date 2019/10/10
 * @Version V1.0
 **/
public class JiaMiTest {
    public static void main(String[] args) {
        String a = "123";
        String s = new Md5Hash(a).toBase64();
        String s2 = new Md5Hash(a).toString();
        System.out.println(s);
        System.out.println(s2);
        System.out.println("=============");
        String salt = UUID.randomUUID().toString();
        String salt2 = "b4255e9d-c8c9-4b70-aded-5f3bfb699d0b";
//        String salt2 = UUID.randomUUID().toString();
        System.out.println("salt: " + salt);
        System.out.println("salt2: " + salt2);
        String s3 = new Md5Hash(a, salt, 10000).toBase64();
        String s4 = new Sha256Hash(a, salt2, 10000).toBase64();
        System.out.println(s3);
        System.out.println(s4);
    }
}
