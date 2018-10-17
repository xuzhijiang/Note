Java Enum在Java 1.5中作为一种新类型引入，其字段由固定的常量集组成。
Java Enum was introduced in Java 1.5 as a new type whose fields consists of fixed set of constants. 

我们可以创建directions(方向)为Java Enum，其中固定字段为EAST，WEST，NORTH和SOUTH。

 We will also learn using Java Enum valueOf, enum values, 
 EnumSet and EnumMap with examples.
 
 public abstract class Enum<E extends Enum<E>>
 implements Comparable<E>, Serializable {}

 Java enum关键字用于创建枚举类型。
 
 让我们看看java enum如何比java类中的常规常量字段更好。
 
 以下是Java中Enums的一些重要观点:
 
1. 所有java enum都隐式extends了java.lang.Enum类，Enum类扩展了Object类
并实现了Serializable和Comparable接口。所以我们不能在枚举中扩展任何类。
2. 由于enum是一个关键字，我们不能用它来结束包名，例如com.java.enum不是有效的包名。
3. Enum可以实现接口。如上面的枚举示例所示，它正在实现Closeable接口。
4. Enum构造函数始终是私有的。可以改成public试试，会提示非法，only private is permitted.
5. 我们无法使用new运算符创建枚举实例。
6. 我们可以在java enum中声明抽象方法，然后所有枚举字段都必须实现抽象方法。在上面的例子中，getDetail（）是抽象方法，所有枚举字段都实现了它。
7. 我们可以在枚举中定义一个方法，枚举字段也可以覆盖它们。例如，toString（）方法在枚举和枚举字段中定义，START已覆盖它。
8. Java枚举字段具有命名空间，我们只能将枚举字段与类名一起使用，如ThreadStates.START
9. 枚举可以在switch语句中使用
10. 我们可以扩展现有的枚举而不会破坏任何现有的功能。 例如，我们可以在ThreadStates枚举中添加一个新字段NEW，而不会影响任何现有功能。
11. 由于枚举字段是常量，因此java best practice是用block letters and underscore for spaces来表示空格。 例如EAST，WEST，EAST_DIRECTION等。
12. 枚举常量是隐式静态(static)和最终的(final)
13. 枚举常量是最终的，但它的变量仍然可以改变。 例如，我们可以使用setPriority（）方法来更改枚举常量的优先级
14. 由于枚举常量是最终的，我们可以使用“==”和equals（）方法安全地比较它们。 两者都会有相同的结果。

Java Enum功能说明:

1. 