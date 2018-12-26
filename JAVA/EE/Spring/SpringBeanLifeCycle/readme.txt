今天我们将研究Spring Bean生命周期。 Spring Bean是任何Spring应用程序中最重要的部分。

1. Spring ApplicationContext负责初始化spring bean配置文件中定义的Spring Beans。

2. Spring Context还负责bean中的注入依赖，可以通过setter或构造函数方法，
也可以通过spring autowiring(自动装配)实现依赖注入.

有时我们想要初始化bean类中的资源，例如在任何客户端请求之前初始化时创建数据库连接或验证第三方服务。 
Spring框架提供了不同的方法，通过它我们可以在spring bean生命周期中提供后初始化和预破坏方法。
(post-initialization and pre-destroy methods)

让我们编写一个简单的Spring应用程序来展示上述配置对spring bean生命周期管理的使用。
在Spring Tool Suite中创建一个Spring Maven项目.
