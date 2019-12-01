package com.spi;

import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

// 解析spring的org.springframework.web.SpringServletContainerInitializer

/**
 * @desc: 类的描述: Spring应用一启动(tomcat启动的时候,就会去扫描当前应用下导入的jar包的META-INF/services),就会找到
 * javax.servlet.ServletContainerInitializer这个文件中的内容,该内容就是ServletContainerInitializer实现类的
 * 全类名路径,也就是ServletContainerInitializer,然后会调用该实现的onStartup()方法,并且我们可以在ServletContainerInitializer
 * 的实现类上标注@HandlesTypes,配置WebApplicationInitializer接口,那么所有WebApplicationInitializer的实现类
 * 都会被传递到onStartup()方法的入参中,然后判断传递进来的WebApplicationInitializer的实现不是接口,不是抽象类,那么
 * 就会通过反射调用生成的对象.
 */
@HandlesTypes(WebApplicationInitializer.class)
public class SpringServletContainerInitializer implements ServletContainerInitializer {

    /**
     * 容器启动的时候会调用该方法,并且传入@HandlesTypes(WebApplicationInitializer.class)
     * 类型的所有子类作为入参
     * @param webAppInitializerClasses 我们感兴趣的类的集合
     * @param servletContext 我们应用的上下文对象
     * @throws ServletException
     */
    @Override
    public void onStartup(Set<Class<?>> webAppInitializerClasses, ServletContext servletContext)
            throws ServletException {

        List<WebApplicationInitializer> initializers = new LinkedList<WebApplicationInitializer>();
        // 传入的兴趣类WebApplicationInitializer的所有的子类
        if (webAppInitializerClasses != null) {
            // 进行循环感兴趣的类
            for (Class<?> waiClass : webAppInitializerClasses) {
                // 判断感兴趣的类,不是接口,不是抽象类
                if (!waiClass.isInterface() && !Modifier.isAbstract(waiClass.getModifiers()) &&
                        WebApplicationInitializer.class.isAssignableFrom(waiClass)) {
                    try {
                        // 通过反射创建感兴趣的类的实例,并且加入到initializers
                        initializers.add((WebApplicationInitializer) waiClass.newInstance());
                    }
                    catch (Throwable ex) {
                        throw new ServletException("Failed to instantiate WebApplicationInitializer class", ex);
                    }
                }
            }
        }

        if (initializers.isEmpty()) {
            servletContext.log("No Spring WebApplicationInitializer types detected on classpath");
            return;
        }
        // 若我们的WebApplicationInitializer的实现类 实现了Ordered接口 或者标注了@Order注解,会进行排序
        AnnotationAwareOrderComparator.sort(initializers);
        servletContext.log("Spring WebApplicationInitializers detected on classpath: " + initializers);
        // 依次循环调用我们的感兴趣的类的实例的onStartup()方法.
        for (WebApplicationInitializer initializer : initializers) {
            initializer.onStartup(servletContext);
        }
    }

}
