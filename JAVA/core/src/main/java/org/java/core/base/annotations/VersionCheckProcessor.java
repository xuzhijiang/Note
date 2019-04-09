package org.java.core.base.annotations;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

// 自定义注解处理器就是一个普通的Java类,因此也可以在注解处理器上面添加注解
@SupportedAnnotationTypes({"org.java.core.base.annotations.VersionCheckAnnotation"})//注解处理器能支持的注解类型,对应getSupportedAnnotationTypes()方法
@SupportedSourceVersion(SourceVersion.RELEASE_8)//注解处理器支持的Java版本,对应getSupportedSourceVersion()这个方法
public class VersionCheckProcessor extends AbstractProcessor {

    Messager messager;

    // 其中init方法主要是从processingEnv中初始化一些有用的工具
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
    }

    // process是对注解处理的核心逻辑。此处我们在init中初始化一个用于打印信息的Messager
    /**
     *
     * @param annotations 第一个参数表示此注解处理器需要处理的所有注解，
     *                    在本案例中只有一个VersionCheckAnnotation
     * @param roundEnv roundEnv包含了一些有用的信息和工具，
     *                 我们可以通过roundEnv获取到被某一个注解标注的元素
     *                 获取到元素以后也可以调用具体的元素api获取元素上对应的注解实例，
     *                 获取到了注解实例就能到注解的参数。
     *                 比如我们这里可以获取到所有被VersionCheckAnnotation标注的所有方法(也就是元素)，
     *                 获取到方法(element)以后我们就可以知道标注在这个方法(element)上的具体注解，
     *                 进而获取注解的参数major_version和minor_version，
     *                 然后判断若minor_version>major_version则使用Messager提示错误
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("start process....");
        //遍历被VersionCheckAnnotation标记的元素
        for(Element element : roundEnv.getElementsAnnotatedWith(VersionCheckAnnotation.class)){
            //获取当前元素上的注解实例
            VersionCheckAnnotation versionCheckAnnotation = element.getAnnotation(VersionCheckAnnotation.class);
            int major_version = versionCheckAnnotation.major_version();
            int minor_version = versionCheckAnnotation.minor_version();
            if(major_version<minor_version){
                messager.printMessage(Diagnostic.Kind.ERROR,"major_version should bigger than minor_version",element);
            }
        }
        //此处布尔值表示传入的一组注解是否是本注解处理器声明的，若是返回true，其他的处理器将不再对这些注解进行处理
        return true;
    }

}
