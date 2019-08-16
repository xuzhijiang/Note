package org.java.core.base.annotations.processor;

import org.java.core.base.annotations.commonMethodTest.MethodInfoAnnotation;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

/**
 * 编译这3个java文件得到字节码文件AnnotationObject.class：
 *
 * javac -d classes -encoding UTF-8 A_FULL_PATH.java B_FULL_PATH.java C_FULL_PATH.java
 *
 * 使用javap查看编译后的信息：
 * javap -v -classpath classes org.java.core.base.annotations.AnnotationObject，会打印出注解的visible信息：
 * 其中classes为类文件路径
 */

/**
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
@SupportedAnnotationTypes({"org.java.core.base.annotations.commonMethodTest.MethodInfoAnnotation"})
public class MethodInfoProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Messager messager = processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE, "start to use PrintProcessor ..");

        Set<? extends Element> rootElements = roundEnv.getRootElements();
        System.out.println("size: " + rootElements.size());
        messager.printMessage(Diagnostic.Kind.NOTE, "root classes: ");
        for (Element root : rootElements) {
            messager.printMessage(Diagnostic.Kind.NOTE, "1111 "  + root.toString());
        }

        messager.printMessage(Diagnostic.Kind.NOTE, "annotation: ");
        for (TypeElement te : annotations) {
            messager.printMessage(Diagnostic.Kind.NOTE, "22222 " + te.toString());
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(te);
            for (Element ele : elements) {
                MethodInfoAnnotation annotation = ele.getAnnotation(MethodInfoAnnotation.class);
                System.out.println("authon: " + annotation.author());
                messager.printMessage(Diagnostic.Kind.NOTE, "3333 " + ele.toString());
            }
        }

        return true;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}
