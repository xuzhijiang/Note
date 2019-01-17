构建器模式(Builder Pattern)

Builder pattern被引入解决一些Factory和Abstract Factory设计模式的一些问题。
这些问题是当对象包含很多属性的时候引起的

当对象包含许多属性的时候，Factory和Abstract Factory设计模式存在3个主要问题:

1. 从客户端程序传递到Factory类的参数太多，可能容易出错，因为大多数情况下，
参数的类型是相同的，而从客户端来说，很难维护参数的顺序。

2. 一些参数可能是可选的，但在Factory模式中，我们被迫需要发送所有参数，那些可选的参数需要作为null传入

3. 如果对象很重并且它的创建很复杂，那么所有这些复杂性都将成为Factory类的一部分.

使用Builder pattern我们可以通过提供具有必需参数的构造函数，
然后使用不同的setter方法来设置可选参数来解决大量参数的问题。
这种方法的问题是，除非明确设置所有属性，否则Object状态将不一致。

Builder模式通过提供 逐步构建对象的方法 并 提供实际返回final object的方法，
解决了大量可选参数和不一致状态的问题。 

如果使用Builder pattern:

1. 创建一个静态嵌套Builder类,然后将所有参数从外部类复制到Builder类。

2. 我们应该遵循命名约定，如果类名是Computer，那么构建器类应该命名为ComputerBuilder。

3. Java Builder类应该有一个公共构造函数，其中包含所有必需的属性作为参数。

4. Java Builder类应具有设置可选参数的方法，并且应在设置可选属性后返回相同的Builder对象。

5. 最后一步是在构建器类中提供一个build（）方法，该方法将返回客户端程序所需的Object。

6. 我们需要在Class中有一个  使用Builder类 作为参数  的私有构造器。

Notice: 

1. Computer类只有getter方法, 没有public的构造器，因此获取Computer对象的唯一方法是通过ComputerBuilder类。

请注意，这是我的观点，我觉得设计模式是指导我们，
但最终我们必须决定在我们的项目中实现它是否真的有益。我坚信KISS原则。

JDK中的Builder设计模式示例：
java.lang.StringBuilder#append() (unsynchronized)
java.lang.StringBuffer#append() (synchronized)
