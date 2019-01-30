package com.jinxuliang.springbootconfig.profile;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

//发布阶段使用的Bean
@Component
@Profile("PROD")
public class ProdBean implements IProfileBean {
}

