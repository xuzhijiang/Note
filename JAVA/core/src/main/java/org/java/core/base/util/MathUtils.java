package org.java.core.base.util;

import org.junit.Test;

public class MathUtils {

    /**
     * 输出浮点数在虚拟机的实际表示
     */
    @Test
    public void printFloatRawIntBits() {
        float a = -5;
        // 原码,反码,补码
        // 输出-5的补码，即虚拟机内部实际表示
        System.out.println(Integer.toBinaryString(Float.floatToRawIntBits(a)));
    }

    /**
     * 负整形数在Jvm中的表示
     */
    @Test
    public void mathTest01() {
        int a = -10;
        for (int i = 0; i < 32; i++){
            // 0x80000000
            // 0100,0000,0000,0000,0000,0000,0000,0000
            int t = (a & 0x80000000 >>> i) >>> (31 - i);
            System.out.print(t);
        }
        System.out.println();
        System.out.println(Integer.toBinaryString(a));
    }

    @Test
    public void IntToHexString() {
        MathUtils obj = new MathUtils();
        System.out.println(obj);
        // 一个对象,默认的toString()打印就是这个对象的 类名 + 对象的hashCode的16进制的字符串.
        System.out.println(getClass().getName() + "@" + Integer.toHexString(obj.hashCode()));
    }
}
