package org.java.core.base.util;

public class UtilsTest {

    public static final int FIXED_HOUR = 3;
    public static final int FIXED_MINUTE = 0;
    public static final int FIXED_SECOND = 0;

    public static void main(String[] args) {
        Utils.testRandom();

        Utils.toMillis(24, 0, 0);
        Utils.toMillis(3, 0, 0);

        Utils.testRegularExpression();
    }

}
