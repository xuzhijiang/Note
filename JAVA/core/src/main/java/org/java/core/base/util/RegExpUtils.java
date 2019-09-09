package org.java.core.base.util;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpUtils {

    // java中在字符串中表示一个\要转移,也就是\\表示一个\, 例如\\d{1,3}表示\d{1,3}
    // .也要进行转移,也就是\.表示.
    // 下面的这个字符其实是: ^(\d{1,3}).(\d{1,3}).(\d{1,3}).(\d{1,3})
    public static final String IP_REGULAR_EXPRESS = "^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})";

    public static void testRegularExpression() {
        Pattern pattern = Pattern.compile(IP_REGULAR_EXPRESS);
        Matcher matcher = pattern.matcher("172.16.8.1 2ms 2ms 0ms");
        try {
            if (matcher != null) {
                System.out.println("-----matcher.find(): " + matcher.find());
                System.out.println("-----" + matcher.group());
                System.out.println(matcher.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        String str = "var hq_str_sh600657=\"信达地产,4.070,4.050,4.070,4.100,4.020,4.070,4.080,5499485,22302372.000,7900,4.070,9700,4.060,56100,4.050,20000,4.040,56800,4.030,77100,4.080,107000,4.090,240400,4.100,191800,4.110,106100,4.120,2019-09-06,15:00:03,00,\";\n" + "var hq_str_sh600302=\"标准股份,4.840,4.820,4.840,4.920,4.800,4.830,4.840,1968400,9553605.000,18300,4.830,37600,4.820,3500,4.810,19600,4.800,26000,4.790,3000,4.840,2000,4.850,5700,4.860,49600,4.870,36200,4.880,2019-09-06,15:00:00,00,\";";

        String[] line = str.split(";");
        String result = line[0].trim().replaceAll("^var\\D+|\"", "").replace("=", ",");
        System.out.println(result);
        System.out.println("length: " + result.split(",").length);
        System.out.println(result.split(",")[5].replaceAll("[^0-9|\\.|\\-]", ""));
        System.out.println("4.100".replaceAll("[^0-9|\\.|\\-]", ""));
        System.out.println("4.1-00".replaceAll("[^0-9|\\.|\\-]", ""));
        System.out.println("4.1-0-aaaa0".replaceAll("[^0-9|\\.|\\-]", ""));

        System.out.println("double: " +Double.valueOf("4.100"));
        // 去掉小数点以及小数点以后的所有字符
        System.out.println("2016.7777".replaceAll("\\..*", ""));
    }
}
