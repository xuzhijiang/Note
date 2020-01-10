package com.springboot.core.profile.impl;

import com.springboot.core.profile.IProfileBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

//生产阶段使用的Bean
@Component
@Profile("PROD")
public class ProdBean implements IProfileBean {
}
