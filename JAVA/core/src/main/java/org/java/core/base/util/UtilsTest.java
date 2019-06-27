package org.java.core.base.util;

public class UtilsTest {

    public static final int FIXED_HOUR = 3;
    public static final int FIXED_MINUTE = 0;
    public static final int FIXED_SECOND = 0;

    public static void main(String[] args) {
        Utils.testRandom();

        Utils.fixedTimeToMillis(24, 0, 0);
        Utils.fixedTimeToMillis(3, 0, 0);

        Utils.testRegularExpression();
    }

}
