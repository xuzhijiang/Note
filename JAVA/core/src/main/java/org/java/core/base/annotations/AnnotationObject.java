package org.java.core.base.annotations;

/**
 * 编译这3个java文件得到字节码文件AnnotationObject.class：
 *
 * javac -d classes -encoding UTF-8 src\main\java\org\java\core\base\annotations\AnnotationObject.java src\main\java\org\java\core\base\annotations\MyClassAnno.java src\main\java\org\java\core\base\annotations\Header.java
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