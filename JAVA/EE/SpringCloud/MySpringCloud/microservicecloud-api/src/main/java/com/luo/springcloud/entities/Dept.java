package com.luo.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 当我们每次都需要创建一个实体类的getter，setter，toString和构造器等方法时，
 * 如果增加一个字段就要重新生成方法，为了简化这种重复的操作，我们在前面的pom中引入了lombok，
 * 同样的实体类，使用方法如下
 *
 * lombok注解使用(需要安装lombok插件):
 * @Setter：注解在属性上；为属性提供 setting 方法
 * @Getter：注解在属性上；为属性提供 getting 方法
 * @Log4j ：注解在类上；为类提供一个 属性名为log 的 log4j 日志对象
 */
@SuppressWarnings("serial")
@AllArgsConstructor//注解在类上；为类提供一个全参的构造方法
@NoArgsConstructor//注解在类上；为类提供一个无参的构造方法
@Data//@Data ：注解在类上；提供类所有属性的 getting 和 setting 方法，此外还提供了equals、canEqual
@Accessors(chain=true)//可以使用链式写法
public class Dept implements Serializable{//实体类必须实现Serializable接口

    private Long deptno; // 主键
    private String dname; // 部门名称
    // 来自那个数据库，因为微服务可以一个服务对应一个数据库，同一个信息被存储到不同的数据库
    private String db_source;

    /**
     * lombok测试
     * @param args
     */
    public static void main(String[] args) {
        Dept dept = new Dept();
        dept.setDeptno(12L).setDname("开发部").setDb_source("DB01");
    }
}