package com.springboot.autoconfiguration.core.selector;

import com.springboot.autoconfiguration.core.autoconfig.MyEnableAutoConfig;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

public class MyImportSelector implements ImportSelector {

    private Class<?> getSpringFactoriesLoaderFactoryClass() {
        return MyEnableAutoConfig.class;
    }

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        List<String> configurations = getCandidateConfigurations();
        System.out.println("configurations: " + configurations);
        // 数组里面是一个一个的全路径的类
        // 这里是com.spring.autoconfiguration.config.SpringRedisConfig
        // 这个类就会被加载到IoC容器中
        return StringUtils.toStringArray(configurations);
    }

    protected List<String> getCandidateConfigurations() {
        // 第一个参数: 加载的类型,也就是MyEnableAutoConfig.class (对应META-INF/spring.factories中的key)
        List<String> configurations = SpringFactoriesLoader.loadFactoryNames(getSpringFactoriesLoaderFactoryClass(), MyImportSelector.class.getClassLoader());
        // SpringFactoriesLoader.loadFactoryNames这个方法回去读取META-INF/spring.factories中的内容
        // 把META-INF/spring.factories中的内容读取出来后,
        // 封装成LinkedMultiValueMap<com.autoconfiguration.config.SpringRedisConfig, com.autoconfiguration.component.MyEnableAutoConfig>
        // 注意: com.autoconfiguration.component.MyEnableAutoConfig是这个LinkedMultiValueMap的key,
        // com.autoconfiguration.component.MyEnableAutoConfig是value.这个设计很巧妙
        // 说白了LinkedMultiValueMap的key和value就是META-INF/spring.factories中的value和key.(value和key正好相反)
        // 看视频回忆: https://www.bilibili.com/video/av69399635?p=4
        Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring.factories. If you "
        + "are using a custom packaging, make sure that file is correct.");
        return  configurations;
    }
}
