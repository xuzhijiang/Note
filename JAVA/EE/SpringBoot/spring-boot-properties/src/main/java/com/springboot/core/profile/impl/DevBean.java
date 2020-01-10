package com.springboot.core.profile.impl;

import com.springboot.core.profile.IProfileBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

//开发阶段使用的Bean
@Component
@Profile("DEV")
public class DevBean implements IProfileBean {
}
