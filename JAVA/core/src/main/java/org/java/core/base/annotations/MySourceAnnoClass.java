package org.java.core.base.annotations;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Set;

/**
 * SOURCE这个policy表示注解保留在源代码中，但是编译的时候会被编译器所丢弃。
 * 由于在编译的过程中这个注解还被保留着，所以在编译过程中可以针对这个policy进行一些操作。
 * 比如在自动生成java代码的场景下使用。最常见的就是lombok的使用了，
 * 可以自动生成field的get和set方法以及toString方法，构造器等；消除了冗长的java代码。
 *
 * SOURCE这个policy可以使用jdk中的javax.annotation.processing.*包
 * 中的processor处理器进行注解的处理过程。
 *
 * 1. 先使用javac编译SimpleObj2和MyProcessor(classes为字节码的存放文件夹，要先创建出来,注意windows和linux写法不同)：
 * javac -d classes -encoding UTF-8 src\main\java\org\java\core\base\annotations\MySourceAnnoClass.java src\main\java\org\java\core\base\annotations\SimpleObj2.java src\main\java\org\java\core\base\annotations\MyProcessor.java
 *
 * 2. 再使用javac中的processor参数处理：
 * javac -cp classes -processor org.java.core.base.annotations.MyProcessor -d classes src\main\java\org\java\core\base\annotations\SimpleObj2.java
 *
 * -cp: 指定查找用户类文件和注释处理程序的位置
 * -d: 指定放置生成的类文件的位置
 * -processor: 处理器类的qualified name
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface MySourceAnnoClass {
    String name();
}
