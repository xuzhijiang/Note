package org.java.core.base.annotations;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.lang.annotation.Annotation;
import java.util.Set;

// 创建对应的Processor：
@SupportedAnnotationTypes({"org.java.core.base.annotations.MySourceAnnoClass"})
public class MyProcessor extends AbstractProcessor {

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
                MySourceAnnoClass annotation = ele.getAnnotation(MySourceAnnoClass.class);
                System.out.println("name: " + annotation.name());
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
