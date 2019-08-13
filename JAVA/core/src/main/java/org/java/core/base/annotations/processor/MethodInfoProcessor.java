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
