package org.java.core.advanced.SystemClass;

import org.junit.Test;

import java.util.Arrays;
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

        //  jvm搜索类的顺序为：Bootstrap，Extension，User。

        // Bootstrap中的路径是jvm自带的jar或zip文件，jvm首先搜索这些包文件，
        // 用System.getProperty("sun.boot.class.path")可得到搜索路径。
        String bootstrapPath = System.getProperty("sun.boot.class.path");
        System.out.println("Bootstrap path: " + bootstrapPath);
        String[] bootstrapPathArray = bootstrapPath.split(";");
        System.out.println("bootstrapPathArray start-----------------------");
        for (String path : bootstrapPathArray) {
            System.out.println("bootstrapPathArray element: " + path);
        }
        System.out.println("bootstrapPathArray end-----------------------");

        // Extension是位于JRE_HOME/lib/ext目录下的jar文件，jvm在搜索完Bootstrap后就搜索该目录下的jar文件，用System.getProperty("java.ext.dirs")可得到搜索路径。
        String extensionPath = System.getProperty("java.ext.dirs");
        System.out.println("Extension path: " +  extensionPath);

        // User搜索顺序为当前路径.、CLASSPATH、-classpath，jvm最后搜索这些目录(当前路径，CLASSPATH, -classpath)，
        // 用System.getProperty("java.class.path")可得到搜索路径
        String applicationPath = System.getProperty("java.class.path");
        System.out.println("Application path: " +  applicationPath);
    }
}
