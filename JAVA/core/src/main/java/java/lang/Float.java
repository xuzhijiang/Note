package java.lang;

/*
public class Float {
    // 运行会报错: Error: Main method not found in class java.lang.Float
    // 原因: 双亲委派机制导致,最终会加载jdk的java.lang.Float,而不是我们自己的java.lang.Float
    // jdk的java.lang.Float中没有main方法,所以就提示上面的错误
    public static void main(String[] args) {
        System.out.println("定义一个和jdk中包名和类名都一样的java.lang.Float");
    }
}
*/
