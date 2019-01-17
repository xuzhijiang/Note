抽象工厂模式类似于工厂模式，它是工厂的工厂。

如果您熟悉java中的工厂设计模式，您会注意到我们有一个Factory类，
它根据提供的输入返回不同的子类，工厂类使用if-else或switch语句来实现这一点。

抽象工厂设计模式的好处:

1. 抽象工厂设计模式提供了接口而不是实现的代码方法。
2. 抽象工厂模式是“工厂的工厂”，可以轻松扩展以容纳更多产品，例如我们可以添加另一个子类Laptop电脑和工厂LaptopFactory。
3. 抽象工厂模式是健壮的，并避免工厂模式的条件逻辑。

JDK中的抽象工厂设计模式示例:
1. javax.xml.parsers.DocumentBuilderFactory#newInstance()
2. javax.xml.transform.TransformerFactory#newInstance()
3. javax.xml.xpath.XPathFactory#newInstance()