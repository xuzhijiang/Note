package org.java.core.base.util;

import org.junit.Test;

public class MathUtils {

    /**
     * java中double和float精度丢失问题?
     * 这是java和其它计算机语言都会出现的问题,float和double类型主要是为了科学计算和工程计算而设计的,
     * 它们并没有提供完全精确的结果，所以我们不应该用于精确计算的场合。float和double类型尤其不适合用于货币运算.
     * 超过精度能表示的范围就会产生误差。产生误差不是因为数的大小，而是因为数的精度。因此，产生的结果接近但不等于想要的结果
     * 在金融计算中，必须要使用BigDecimal，double和float都不适合.
     *
     * 我们输入的十进制数字会先转换成二进制，进行运算后再转换为十进制输出。
     * double和float提供了快速的运算，然而问题在于转换为二进制的时候，有些数字不能完全转换，
     * 只能无限接近于原本的值，这就导致了你看到的不正确的结果.
     *
     * BigDecimal可以表示任意精度的小数，并对它们进行计算。但要小心使用 BigDecimal(double) 构造函数，
     * 因为它会在计算的过程中产生舍入误差。最好要使用基于整数或 String 的构造函数来创建BigDecimal对象.
     *
     * float和double只能用来做科学计算或者是工程计算，在商业计算等精确计算中，我们要用java.math.BigDecimal。
     */
    @Test
    public void testFloatAndDouble() {
        float f = 20014999;
        double d = f;
        double d2 = 20014999;
        System.out.println("f=" + f);
        System.out.println("d=" + d);
        System.out.println("d2=" + d2);

        System.out.println(0.1+0.2);
        System.out.println(BigDecimalUtils.add(0.1, 0.2));
    }

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
     * ^: 异或运算: 0^0=0； 0^1=1； 1^0=1； 1^1=0
     * 异或的意思就是：不同 才可以得1，否则为0
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
