package com.tk.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 创建一个通用的父级接口: 主要作用是让 DAO 层的接口继承该接口，以达到使用 tk.mybatis 的目的
 *
 * 自己的 Mapper
 * 特别注意，该接口不能springIoC容器被扫描到，否则会出错
 * <p>Title: MyMapper</p>
 * <p>Description: tk.mybatis 是在 MyBatis 框架的基础上提供了很多工具，让开发更加高效</p>
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}