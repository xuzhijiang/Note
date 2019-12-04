package spring.source.servlet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.util.LambdaSafe;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.util.Assert;

public class WebServerFactoryCustomizerBeanPostProcessor
        implements BeanPostProcessor, BeanFactoryAware {

    private ListableBeanFactory beanFactory;

    private List<WebServerFactoryCustomizer<?>> customizers;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        Assert.isInstanceOf(ListableBeanFactory.class, beanFactory,
                "WebServerCustomizerBeanPostProcessor can only be used "
                        + "with a ListableBeanFactory");
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        // 在Spring容器中寻找WebServerFactory类型的bean，
        // SpringBoot内部的3种内置Servlet"容器工厂"都实现了这个接口(比如TomcatServletWebServerFactory)，
        // 该接口的作用就是进行Servlet容器的配置
        // 比如添加Servlet初始化器addInitializers、添加错误页addErrorPages、设置session超时时间setSessionTimeout、
        // 设置端口setPort等等
        if (bean instanceof WebServerFactory) {
            postProcessBeforeInitialization((WebServerFactory) bean);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }

    @SuppressWarnings("unchecked")
    private void postProcessBeforeInitialization(WebServerFactory webServerFactory) {
        // 遍历获取的每个定制化器，并调用customize方法进行一些定制
        // 比如TomcatServletWebServerFactoryCustomizer类,我们以ServletWebServerFactoryCustomizer为例进行学习.
        LambdaSafe
                .callbacks(WebServerFactoryCustomizer.class, getCustomizers(),
                        webServerFactory)
                .withLogger(WebServerFactoryCustomizerBeanPostProcessor.class)
                .invoke((customizer) -> customizer.customize(webServerFactory));
    }

    private Collection<WebServerFactoryCustomizer<?>> getCustomizers() {
        if (this.customizers == null) {
            // Look up does not include the parent context
            this.customizers = new ArrayList<>(getWebServerFactoryCustomizerBeans());
            // 定制化器做排序
            this.customizers.sort(AnnotationAwareOrderComparator.INSTANCE);
            // 设置定制化器到属性中
            this.customizers = Collections.unmodifiableList(this.customizers);
        }
        return this.customizers;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Collection<WebServerFactoryCustomizer<?>> getWebServerFactoryCustomizerBeans() {
        // 找出Spring容器中WebServerFactoryCustomizer类型的bean
        return (Collection) this.beanFactory
                .getBeansOfType(WebServerFactoryCustomizer.class, false, false).values();
    }

}
