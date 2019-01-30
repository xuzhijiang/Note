本模块介绍Spring Boot Web开发技术，主要包容两部分：

1. Spring Boot MVC应用
2. Spring Boot RESTful Service开发

本讲将会展示Spring Boot与Spring Boot MVC之间的区别

## Spring Boot MVC概述

### 技术全局视图

                  HTTP/HTTPS
Clients     <------------------->   Spring Boot App (REST API)

>Spring Boot MVC是实现上述移动互联网后端应用的主流开发框架之一.

### Java平台Web开发技术栈的转移

从过去的SSH(Spring Framework + Structs + Hibernate) 到
现在的SSM(Spring Boot + Spring Boot MVC + Mybatis)

### 学习Spring Boot MVC技术之前提

#### 学习阶段

1. Java SE面向对象编程技能
2. 计算机网络中的HTTP协议
3. Tomcat服务器的配置和使用
4. Spring Framework
5. Java Servlet

#### 实战阶段

1. 特定业务领域知识
2. 面向对象系统分析与设计
3. 基本的运维知识与技术
4. 信息安全相关技术

Spring Boot MVC是Spring技术家族中用于快速开发Web应用的一个框架，其中的MVC是Model-View-Controller的缩写

Spring Boot MVC = Spring框架 + Servlet + JSP
>Spring MVC的技术依赖关系（注：仅适用于Servlet技术栈）

### Spring Boot与Spring Boot MVC

1. 早在Spring Boot之前， Spring MVC就己经存在。
2. Spring Boot本质上不过是简化了原有Spring MVC应用的“启动（Boot）”和“配置（Config）”过程罢了，所以，“自动化”后的Spring MVC就被称为是“SpringBoot MVC”，不加Boot的，通常指传统的Spring MVC。
3. Spring Boot不是取代了早期的Spring MVC，而是对SpringFramework原有技术的改进和升级，与Spring MVC这种基于SpringFramework的上层应用框架不是一个层面上的东西。

### 了解一下Spring Boot MVC应用的分层架构图

开发Spring Boot MVC应用需要用到缓存、数据库存取、视图渲染等相关技术。

### Spring MVC是如何响应HTTP请求的？

要理解左图，需要先掌握Java传统Web开发技术（Servlet），并且熟练掌握Spring Framework
的基础知识

### 技术的当前进展

1. 可以使用传统的Java Servlet实现Spring MVC的所有功能（适用于Spring Boot 2.0以前的版本）
2. 从Spring Boot 2.0开始， Spring Web开发技术进行了扩展和演化，现在同时支持传统的Servlet技术栈和新增的Reactive技术栈，现在两者都可以用于开发当前流行的“前后端分离”的Web应用，另外，后者更适合于开发微服务.

### 初识Spring Boot MVC


