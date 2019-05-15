适配器设计模式(Adapter Design Pattern):
	
	适配器设计模式的一个重要现实例子是移动充电器。
移动电池需要3伏充电才能充电，但普通插座produces 120V(美国）或240V(印度）,
 因此，移动充电器可作为移动充电插座和墙壁插座之间的适配器。
 
使用适配器设计模式使得两个不相关的接口可以一起工作。 连接这些不相关接口的对象称为适配器。

在实现Adapter模式时，有两种方法 - 类适配器和对象适配器 - 但是这两种方法都产生相同的结果。

类适配器 - 此形式使用java继承并扩展源接口
对象适配器 - 此形式使用Java Composition，而适配器包含源对象。

JDK中的适配器设计模式示例:

java.util.Arrays#asList()
java.io.InputStreamReader(InputStream) (returns a Reader)
java.io.OutputStreamWriter(OutputStream) (returns a Writer)

-----------------------------------------------

