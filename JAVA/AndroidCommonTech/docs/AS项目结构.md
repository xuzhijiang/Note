# AS项目结构

- 最外层的目录AndroidCommonTech是我们创建这个项目的时候，要求我们输入项目名称，AndroidCommonTech是项目名称，我们的项目要取一个能表达产品的名称，AndroidCommonTech展开之后是我们完整的项目结构
- app是AndroidCommonTech这个项目的一个module,在AS当中，分为了Project(项目)，Module（模块）两种概念，我们创建项目的时候会默认创建一个模块，这里的app就是我们的一个module.
- app/libs: 存放项目用到的jar
- app/src: 存放项目源码以及资源文件
- app/build.gradle: app这个module构建使用的gradle脚本.
- app/build: app这个module生成的东西都存放在这个目录下
- /gradle: 存放构建AndroidCommonTech这个项目用到的东西
- /build.gradle: 构建AndroidCommonTech这个项目的脚本
- External Libraries: 显示项目所依赖的所有类库。
- local.properties: 这个文件一定不要放到版本控制系统中，因为它包含了特定于本地配置的信息.
- settings.gradle: 包含了整个AndroidCommonTech项目所有的module.

## APK的签名

每个apk都要有自己的签名，否则如果被其他人知道了apk的包名后，其他人的apk就可以直接覆盖了自己的apk，但是如果我们的apk是有签名的，那么到时候会校验其他人的apk的签名是否一致，不一致就不会被覆盖.

可以使用as生成签名文件，要记住生成签名文件时候的密码，在后续的开发和维护中，会用到这个密码的。

## AndroidStudio使用遇到的问题

1. AS如何关联SDK源码?

打开C:\Users\a\.AndroidStudio3.2\config\options\jdk.table.xml,然后找到对应的SDK，然后在
sourcePath标签中:

```
<sourcePath>
  <root type="composite">
	<!-- <root url="file://$USER_HOME$/Library/Android/sdk/sources/android-28" type="simple" />  
	url中的Android Api版本改成想要关联的版本，比如android-26，android-28
	-->
	<root url="file://$USER_HOME$/Library/Android/sdk/sources/android-28" type="simple" />
  </root>
</sourcePath>
```