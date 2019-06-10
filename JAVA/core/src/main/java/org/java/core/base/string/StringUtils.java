package org.java.core.base.string;

import org.junit.Test;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class StringUtils {

    @Test
    public void stringToChar() {
        String str = "xabcdxxbbbbbbxxxxxxxxxccccccc";

        char[] charArr = str.toCharArray();
        System.out.println(charArr.length);
        System.out.println("String转化成char数组: " + Arrays.toString(charArr));

        char c = str.charAt(2);
        System.out.println(c);

        // 拷贝String字符串到字符数组.
        char[] arr = new char[7];
        str.getChars(0, 7, arr, 0);
        System.out.println(arr);
    }

    @Test
    public void stringToByteArray() throws Exception{
        String str = "abcdefg你";
        // getBytes方法将string编码为一个bytes序列(也就是一个字节数组),
        // 用的是平台默认的字符集编码
        byte[] byteArr = str.getBytes();
        System.out.println(Arrays.toString(byteArr));

        // 使用指定的字符集编码
        byte[] byteArr2 = str.getBytes(StandardCharsets.UTF_8.name());
        System.out.println(Arrays.toString(byteArr2));

        // 得到某一个char的byte值
        String s = "A";
        System.out.print(Arrays.toString(s.getBytes()));// 65
    }

    @Test
    public void stringPool() {
        String s1 = "Cat";
        String s2 = "Cat";
        String s3 = new String("Cat");

        System.out.println("s1 == s2 :" + (s1 == s2)); // true
        System.out.println("s1 == s3 :" + (s1 == s3)); // false
    }

    @Test
    public void substringTest() {
        String str = "123.56789.com";
        System.out.println("Last 4 char String: " + str.substring(str.length() - 4));
        System.out.println("First 4 char String: " + str.substring(0, 4));
        System.out.println("website name: " + str.substring(4, 9));
    }

    @Test
    public void charArrayToString() {
        char[] charArray = {'A', 'B', 'C'};
        System.out.println(new String(charArray));
    }

    @Test
    public void byteArrayToString() {
        // 你注意到当创建byte数组的时候，我提供了char，
        // 这是奏效的，因为自动装箱的原因，字符'P'会被转化成byte数组中的80
        //	因为自动装箱和char'P'在字节数组中被转换为80。
        byte[] byteArray = {'P', 'A', 'N', 'K', 'A', 'J'};
        byte[] byteArray1 = { 80, 65, 78, 75, 65, 74 };

        String str = new String(byteArray);
        String str2 = new String(byteArray1);

        System.out.println(str);
        System.out.println(str2);

        // String还有构造函数，提供字节数组和Charset作为参数
		// The charset to be used to decode the bytes
        // charset用于解码字节
        str = new String(byteArray, StandardCharsets.UTF_8);
        System.out.println(str);

        byte[] byteArray2 = {80, 65, 78, 75, 65, 74};
        String str3 = new String(byteArray2, 0, 3, StandardCharsets.UTF_8);
        System.out.println(str3);
    }

    @Test
    public void stringCompare() {
        String str = "ABC";

        // 比较是基于每个字符在String中的Unicode值
        System.out.println(str.compareTo("DEF"));
        System.out.println(str.compareToIgnoreCase("abc"));

        char a = 'A';
        char d = 'D';
        System.out.println(a-d);
    }

    /**
     * public String[] split(String regex):基于提供的正则表达式分割为数组
     */
    @Test
    public void stringSplit() {
        String line = "I am a java developer";

        String[] words = line.split(" ");
        // 限制最多拆成2组.
        String[] twoWords = line.split(" ", 2);

        System.out.println("String split with delimiter: " + Arrays.toString(words));
        System.out.println("String split into two: " + Arrays.toString(twoWords));

        // 如果正则表达式与输入字符串的任何部分都不匹配，那么结果数组只有一个元素，即该字符串
        // split string delimited with special characters
        String wordsWithNumbers = "I|am|a|java|developer";
        String[] numbers = wordsWithNumbers.split("\\|ss");
        System.out.println("String split with special character: " + Arrays.toString(numbers));

        // 末尾的空字符是不算的，第一个空字符是算的.
        String s = "abcaada";
        System.out.println(Arrays.toString(s.split("a")));
    }
}
