package com.jinxuliang.springbootconfig.profile;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

//开发阶段使用的Bean
@Component
@Profile("DEV")
public class DevBean implements IProfileBean {
}

