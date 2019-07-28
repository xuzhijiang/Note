package com.di.bean.impl;

import com.di.bean.IOptionalBean;

//如果注释掉@Component，则UserOptionalBean中的相应字段将不会被注入值,会得到一个null
//@Component
public class OptionalBean implements IOptionalBean {}
