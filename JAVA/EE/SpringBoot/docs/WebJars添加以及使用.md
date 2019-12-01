# WebJars基础

现代网页设计，离不开各种各样的Web前端UI库。各种UI库通常都会包容多个文件(比如.css， .js，字体，图标等）。

Spring Boot支持一种被称为webjar的技术，将一些前端框架(比如Bootstrap）打包为单个文件， 从而在Thymeleaf模板中可以作为一个整体进行引用。

使用WebJars打包的资源，其URL以“/webjars/”打头，也就是说，所有指向/webjars/的HTTP请求，都会被导向这个包中所包容的静态资源文件。

## 获取webjar的maven依赖(以Bootstrap为例）

要想在Spring Boot Web项目中使用诸如Bootstrap等前端框架，需要访问webjars或
MavenRepository网站去搜索其Maven依赖声明。

添加完依赖之后，可以看到相关的js文件、 css样式表和字体等静态
资源，都被放到了jar包中，并且准备好了在视图模板文件中访问。

>http://www.webjars.org/, https://mvnrepository.com

## 如何引用WebJars包？

特别注意其包名：`resources.webjars.jquery.1.11.1，`这实际上就是文件夹名，你可以这样看：所有jquery文件位于resources文件夹下的
/webjars/jquery/1.11.1下。

现在，就可以在网页中使用以下URL引用到它们：
```html
<script src="/webjars/jquery/1.11.1/jquery.min.js">
</script>
```

# SpringBoot静态资源映射规则

>在默认的情况下，SpringBoot会加载classpath下的/static、/public、/resources 、 /META-INF/resources目录下的静态资源供用户进行访问，个人感觉 static可能更具有说明力。我们可以在项目的src/main/resources目录下建立static文件夹.注意在我们访问的URL中并不会包含"static"这个单词， static是所有静态资源的根目录，就相当于常规J2EE开发下的 webapp目录一样，因此 url是不需要包含的。

~~~java
public void addResourceHandlers(ResourceHandlerRegistry registry) {
            if (!this.resourceProperties.isAddMappings()) {
                logger.debug("Default resource handling disabled");
            } else {
                Duration cachePeriod = this.resourceProperties.getCache().getPeriod();
                CacheControl cacheControl = this.resourceProperties.getCache().getCachecontrol().toHttpCacheControl();
                if (!registry.hasMappingForPattern("/webjars/**")) {
                    this.customizeResourceHandlerRegistration(registry.addResourceHandler(new String[]{"/webjars/**"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/webjars/"}).setCachePeriod(this.getSeconds(cachePeriod)).setCacheControl(cacheControl));
                }

                String staticPathPattern = this.mvcProperties.getStaticPathPattern();
                if (!registry.hasMappingForPattern(staticPathPattern)) {
                    this.customizeResourceHandlerRegistration(registry.addResourceHandler(new String[]{staticPathPattern}).addResourceLocations(getResourceLocations(this.resourceProperties.getStaticLocations())).setCachePeriod(this.getSeconds(cachePeriod)).setCacheControl(cacheControl));
                }

            }
        }

~~~

1、所有的/webjars/**,都去classpath:/META-INF/resources/webjars/找资源

webjars:以jar包的方式引入资源，网页搜索webjars,选择maven的方式引入，例如引入jquery

~~~xml
<dependency>
  <groupId>org.webjars</groupId>
  <artifactId>jquery</artifactId>
  <version>3.3.1</version>
</dependency>
~~~

访问jQuery.js的路径为：localhost:8080/webjars/jquery/3.3.1/jquery.js

2.“/**”访问当前项目的任何资源，（静态资源的文件夹）

~~~java
"classpath:/META-INF/resources/",
"classpath:/resources/",
"classpath:/static/",
"classpath:/public/"
"/":当前项目的根路径
~~~
