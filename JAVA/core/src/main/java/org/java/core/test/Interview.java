package org.java.core.test;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Interview {

    public static void main(String[] args) throws IOException {
//        HashSet<Student> set = new HashSet<>();
//        set.add(new Student("xzj", 27));
//        set.add(new Student("xzj", 27));
//        System.out.println(set);

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                createConn();
            }).start();
        }

        System.in.read();
    }

    private static void createConn() {
        try {
            URL url = new URL("http://www.jd.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Student{
    String name;
    Integer age;
    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Student)) return false;

        Student s = (Student) obj;
        if (s.age == this.age && this.name.equals(s.name)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + ((name == null) ? 0 : name.hashCode());
        result = result * 31 + ((age == null) ? 0 : age.hashCode());
        return result;
    }
}
