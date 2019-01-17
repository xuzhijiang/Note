(Factory design pattern)工厂模式在JDK和像Spring，Struts框架中广泛使用.

 请注意，此模式也称为工厂方法设计模式。Factory Method Design Pattern.

工厂设计模式的优点:

1. 工厂设计模式提供了接口而不是实现的代码方法。
2. 工厂模式从客户端代码中删除实际实现类的实例化。工厂模式使我们的代码更健壮，耦合更少，易于扩展。
例如，我们可以轻松更改PC类实现，因为客户端程序不知道这一点。

JDK中的工厂设计模式示例:
java.util.Calendar，ResourceBundle和
NumberFormat getInstance（）方法使用Factory模式。

包装类中的valueOf（）方法，如Boolean，Integer等。
valueOf() method in wrapper classes like Boolean, Integer etc.