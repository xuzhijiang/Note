package org.java.core.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpUtils {

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

}
