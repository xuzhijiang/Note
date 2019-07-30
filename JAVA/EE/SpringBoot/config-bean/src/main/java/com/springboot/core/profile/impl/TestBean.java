package com.springboot.core.profile.impl;

import com.springboot.core.profile.IProfileBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

// 测试阶段使用的bean
@Component
@Profile("TEST")
public class TestBean implements IProfileBean {
}
