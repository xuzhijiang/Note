Serialization在JDK1.1被引入，是Java核心的重要特点之一.

Serialization在Java中允许我们把一个对象转化成流，然后我们
就可以通过网络发送或者把它保存到文件或者存储在数据库里面以便以后使用.

Deserialization是一个转化对象流为实际的将要在程序中使用的Java对象过程.
Java中的序列化起初看起来很容易使用，但它带来了一些简单的安全性和完整性问题，
我们将在本文的后面部分介绍。

如果你想要一个类对象序列化，你只需做实现java.io.Serializable接口.
Serializable在Java中是一个标记接口，没有字段或者方法的实现.他就像一个
选择参加的 过程，通过它我们是我们的类序列化.

Serialization在Java中是由ObjectInputStream和ObjectOuputStream实现，
所以我们需要的是一个包装器包装他们，把它门保存到文件或通过网络发送.

静态变量也不会被序列化，因为它属于类而不是对象.
使用transient关键字可以不序列化某一个成员变量.

cmd输入以下命令可以为一个实现Serialiable生成serialVersionUID:
serialver -classpath . com.journaldev.serialization.Employee(bin/下的类名)

Note that it’s not required that the serial version is 
generated from this program itself, we can assign this 
value as we want. It just need to be there to let deserialization 
process know that the new class is the new version of the same 
class and should be deserialized of possible.
请注意，让程序自动生成serial version id不是必需的，我们可以自动给它分配我们想要的值，
它只是让反序列化的过程中知道新类是相同类的新版本，应该被反序列化就行了.

如果对类做以下3中改变：

1. Adding new variables to the class
增加一个新的变量给类
2. Changing the variables from transient to non-transient, 
for serialization it’s like having a new field.把变量从transient变成non-transient.对于序列化他像有了一个新字段
3. Changing the variable from static to non-static, 
for serialization it’s like having a new field.把变量从静态的变为非静态的，对于序列化它像有了一个新字段

But for all these changes to work, the java class should have serialVersionUID defined for the class
对于这些改变要生效，Java class必须要有serialVersionID定义在类中

 * 我们已经看到，序列化在java中是自动的，我们所要做的就是实现序列化接口，
 * The implementation is present in the ObjectInputStream and ObjectOutputStream classes
 * 该实现存在于ObjectInputStream和ObjectOutputStream中，但是如果我门想改变我们
 * 正在保存数据的方式，例如我们有一些敏感信息在object中，在保存/检索之前，我们要对他们加密/解密，
 * 这里有4个方面我们提供的去改变序列化行为.如果类中存在这些方法，则他们用于序列化目的.
 
1. readObject(ObjectInputStream ois): If this method is present in the class, 
ObjectInputStream readObject() method will use this method for reading the object from stream.
如果这个方法存在于类中，ObjectInputStream 的readObject方法将用这个方法从流中读取类.

2. writeObject(ObjectOutputStream oos): If this method is present in the class, 
ObjectOutputStream writeObject() method will use this method for writing the 
object to stream. 
如果这个方法存在于类中，ObjectInputStream 的writeObject方法将用这个方法将对象写入流.

One of the common usage is to obscure the object variables 
to maintain data integrity.通用用法之一是隐藏对象变量以维持数据完整性.

3. Object writeReplace(): If this method is present, then after serialization 
process this method is called and the object returned is serialized to the stream.
如果存在此方法，则在序列化过程之后调用此方法，并将返回的对象序列化到流中。

4. Object readResolve(): If this method is present, then after 
deserialization process, this method is called to return the final 
object to the caller program.
如果存在此方法，则在反序列化过程之后，将调用此方法以将最终对象返回给调用者程序。

One of the usage of this method is to implement Singleton pattern with Serialized classes. 
Read more at Serialization and Singleton.

Usually while implementing above methods, it’s kept as private 
so that subclasses can’t override them(通常实现上述方法的时候，它保持为私有的，以便子类不能覆盖他们). They are meant for serialization 
purpose only and keeping them private avoids any security issue.(他们用于序列化目的而已，保持他们私有
避免任何安全隐患)

Serialization in java comes with some serious pitfalls such as:
序列化在Java中有一些严重的缺点:

1. The class structure can’t be changed a lot without breaking 
the java serialization process. So even though we don’t need some 
variables later on, we need to keep them just for backward compatibility.
在不破坏java序列化过程的情况下，类结构不能改变很多。 因此，即使我们以后不需要某些变量，我们也需要保留它们以便向后兼容。


2. Serialization causes huge security risks, an attacker can change 
the stream sequence and cause harm to the system. For example, user 
role is serialized and an attacker change the stream value to make 
it admin and run malicious code.
序列化造成严重的安全风险，攻击者可以改变流的序列，对系统造成伤害，例如，用户角色被序列化，攻击者更改流值以使其成为管理员并运行恶意代码。

Java Serialization Proxy pattern is a way to achieve greater 
security with Serialization. In this pattern, an inner private
 static class is used as a proxy class for serialization purpose. 
 This class is designed in the way to maintain the state of the main 
 class. This pattern is implemented by properly implementing readResolve() 
 and writeReplace() methods.
 Java Serialization Proxy模式是一种通过Serialization实现更高安全性的方法。 在此模式中，
 内部私有静态类用作序列化目的的代理类。此类的设计方式是维护主类的状态。 通过正确实现readResolve（）
 和writeReplace（）方法来实现此模式。

 