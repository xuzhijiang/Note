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

    /**
     * 移位运算
     * << : 左移运算符，num << 1,相当于num乘以2
     * >> : 右移运算符，num >> 1,相当于num除以2
     * >>> : 无符号右移，忽略了符号位扩展，0补最高位，空位都以0补齐
     */
    @Test
    public void testShift() {
        int num = 100;
        //原始数二进制
        System.out.println(Integer.toBinaryString(num));
        //左移一位
        int left = num << 1;
        System.out.println(Integer.toBinaryString(left));
        // 有符号的右移一位(符号位是什么就补充什么)
        int right =  num >> 1;
        System.out.println(Integer.toBinaryString(right));
        // 无符号右移一位(最高位都填充0)
        int unsignedRight = num >>> 1;
        System.out.println(Integer.toBinaryString(unsignedRight));
    }
}
