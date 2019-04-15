package org.springboot.starter.log.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

// 配置类
// @ConfigurationProperties(prefix = "mylog")注解是获取yaml文件中的配置
// 其中prefix = "mylog"表示获取yaml中前缀为person的值
// 即：通过@ConfigurationProperties加载properties文件内的配置，通过prefix属性指定
//properties的配置的前缀
@ConfigurationProperties(prefix = "mylog")
public class LogProperties {

    private String exclude;

    private String[] excludeArr;

    @PostConstruct
    public void init(){
        this.excludeArr = StringUtils.split(exclude, ",");
    }

    public String getExclude(){
        return exclude;
    }

    public void setExclude(String exclude){
        this.exclude = exclude;
    }

    public String[] getExcludeArr() {
        return excludeArr;
    }

}
