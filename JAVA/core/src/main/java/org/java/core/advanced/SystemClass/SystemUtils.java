package org.java.core.advanced.SystemClass;

import org.junit.Test;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class SystemUtils {

    /**
     * 打印系统环境变量
     */
    @Test
    public void printSystemEnv() {
        Map<String, String> envMap = System.getenv();
        Set<String> keys = envMap.keySet();

        for(String key: keys) {
            System.out.println("Key: " + key + ", value: " + envMap.get(key));
        }

        // 获取指定的环境变量的值
        String pathValue = System.getenv("PATH");
        System.out.println("$PATH="+pathValue);
    }

    @Test
    public void printSystemProperties() {
        // System类包含了获取系统属性列表的有用的方法。
        // 可以获取指定的属性，设置系统属性以及情况任何存在的属性.
        Properties props = System.getProperties();
        Set<Object> keySet = props.keySet();
        for(Object obj : keySet) {
            String key = (String) obj;
            System.out.println("{"+obj+"="+System.getProperty(key)+"}");
        }

        // Get Specific Property
        System.out.println(System.getProperty("user.country"));

        // Clear property
        System.clearProperty("user.country");
        System.out.println(System.getProperty("user.country")); //print null

        //Set System property
        System.setProperty("user.country", "IN");
        System.out.println(System.getProperty("user.country")); //prints "IN"
    }
}
