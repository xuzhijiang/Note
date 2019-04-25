package com.jinxuliang.dependency_inject.bean;

import org.springframework.stereotype.Component;

//如果注释掉@Component，则UserOptionalBean中的相应字段将得到一个null
//@Component
public class OptionalBean implements IOptionalBean {
}
