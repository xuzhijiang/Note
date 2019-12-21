# 为什么在springboot中配置了@EnableWebMvc,静态文件就用不了?

    为什么在springboot中配置了@EnableWebMvc,有些功能就不起作用了?

![](../pics/EnableWebMvc对于springboot的副作用01.png)

![](../pics/EnableWebMvc对于springboot的副作用02.png)

![](../pics/EnableWebMvc对于springboot的副作用03.png)

![](../pics/EnableWebMvc对于springboot的副作用04.png)

---

![](../pics/spring-boot-web-start的作用.png)

![](../pics/从Spring迁移到SpringBoot-web-start.png)

![](../pics/SpringBoot如何完全控制SpringMVC.png)

    在springboot项目中,如果在@Configuration注解上添加@EnableWebMvc注解,
    就不能加载css样式了.(不能处理静态文件了)
