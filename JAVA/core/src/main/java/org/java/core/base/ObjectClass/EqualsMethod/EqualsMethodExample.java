package org.java.core.base.ObjectClass.EqualsMethod;

public class EqualsMethodExample {

    public static void main(java.lang.String[] args) {
        java.lang.Object obj1 = new java.lang.Object();
        java.lang.Object obj2 = new java.lang.Object();
        System.out.println(obj1.equals(obj2));//false

        // String类也是继承了Object类，按理说比较的是地址值也，应该为false，怎么会为true,
        // 因为String类重写了equals()方法.
        java.lang.String str1 = new java.lang.String("AA");
        java.lang.String str2 = new java.lang.String("AA");
        System.out.println(str1.equals(str2));//true
        // 其实不仅String类重写了equals()方法，还有包装类，File类，Date类都重写了
        // Object类的equals()方法，比较两个对象的“实体内容”是否相同，
        // 如果我们自己定义的类，希望两个对象的属性值都相同的情况下返回true，
        // 就需要重写equals()方法.下面的Person类尝试重写equals方法.

        // 除float和double外的原始数据类型 : 使用 ==

        // float类型: 使用Float.foatToIntBits转换成int类型，然后使用==比较
        float f1 = 200.1f;
        float f2 = 100.3F;
        float f3 = 100.3f;
        System.out.println("f1 == f2: " + (Float.floatToIntBits(f1) == Float.floatToIntBits(f2)));
        System.out.println("f3 == f2: " + (Float.floatToIntBits(f3) == Float.floatToIntBits(f2)));
        System.out.println("f3 == f2: " + (f2 == f3));


        // double类型: 使用Double.doubleToLongBit转换成long类型，然后使用==比较

        // 数组域(数组的比较) : 使用 Arrays.equals
    }
}

/**
 * 以下类为Object中的部分源码.
 */
class Object {

    /**
     * equals(Object obj)处理引用类型变量，在源码中发现还是比较的还是地址值
     * @param obj
     * @return
     */
    public boolean equals(Object obj) { return (this == obj); }

}

class String {

    /** The value is used for character storage. */
    private final char value[] = new char[10];

    public boolean equals(java.lang.Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof java.lang.String) {
            // 比较内容,具体看String源码
        }
        return false;
    }

}

class Person {
    private String name;//姓名

    private String sex;//性别

    private int age;//年龄

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        if (age != other.age)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (sex == null) {
            if (other.sex != null)
                return false;
        } else if (!sex.equals(other.sex))
            return false;
        return true;
    }
}