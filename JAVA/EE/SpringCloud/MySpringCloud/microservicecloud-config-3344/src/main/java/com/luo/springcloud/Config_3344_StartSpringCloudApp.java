package com.luo.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * /{application}/{profile}[/{label}]
 * /{application}-{profile}.yml
 * /{label}/{application}-{profile}.yml
 * /{application}-{profile}.properties
 * /{label}/{application}-{profile}.properties
 *
 * 上面的url会映射到git仓库(git仓库名字在application.yml中配置了)下的{application}-{profile}.properties对应的配置文件，
 * 其中application对应的是git仓库下的yml的文件名，{label}对应Git上不同的分支，默认为master。
 * profile对应的是对应的git仓库中的yml中的profiles,比如，要访问git仓库中，master分支下的，aaa.yml中的的dev环境，
 * 就可以访问这个url：http://localhost:1201/aaa/dev/master
 *
 * access: http://localhost:3344/cloud-config/dev/
 * access: http://localhost:3344/cloud-config/test/
 *
 * http://localhost:3344/app/dev/master -> app-dev.yml
 * http://localhost:3344/app-dev.yml
 * http://localhost:3344/app-dev.properties
 *
 */
@SpringBootApplication
@EnableConfigServer
public class Config_3344_StartSpringCloudApp {

    public static void main(String[] args){
        SpringApplication.run(Config_3344_StartSpringCloudApp.class, args);
    }

}
