package org.java.core.base.annotations;

/**
 * 编译这3个java文件得到字节码文件AnnotationObject.class：
 *
 * javac -d classes -encoding UTF-8 src\main\java\org\java\core\base\annotations\AnnotationObject.java src\main\java\org\java\core\base\annotations\MyClassAnno.java src\main\java\org\java\core\base\annotations\Header.java
 *
 * 其中保留policy为CLASS的注解编译完后不可见，而策略为RUNTIME的注解编译后可见。
 *
 * 同样，我们可以使用javap查看编译后的信息：
 * javap -v -classpath classes org.java.core.base.annotations.AnnotationObject，会打印出注解的visible信息：
 * 其中classes为类文件路径
 */
@MyClassAnno(name = "obj")
@Header(code = 200)
public class AnnotationObject {

    private String val;

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}