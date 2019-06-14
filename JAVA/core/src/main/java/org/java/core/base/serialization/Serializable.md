# Serializable

序列化就是将一个对象转换成字节序列(字节流)，方便存储(保存到文件或数据库)和传输(通过网络发送),Serialization在java 1.1被引入,反序列化是转化对象流为Java对象过程.

## Serializable特点

- 如果你想要一个类对象序列化，你只需做实现java.io.Serializable接口.
它是一个标记接口，没有字段或者方法的实现.否则抛java.io.NotSerializableException异常，
- 不会对静态变量进行序列化，因为序列化只是保存对象的状态，静态变量属于类的状态。
- Serialization在Java中是由ObjectInputStream和ObjectOuputStream实现，transient 关键字可以使一些属性不会被序列化。(transient: 暂时的)

>ArrayList 中存储数据的数组 elementData 是用 transient 修饰的，因为这个数组是动态扩展的，并不是所有的空间都被使用，因此就不需要所有的内容都被序列化。通过重写序列化和反序列化方法，使得可以只序列化数组中有内容的那部分数据。

```java
private transient Object[] elementData;
```

## serialVersionUID的作用：
 
它由ObjectOutputStream和ObjectInputStream类用于写入和读取对象操作。 虽然拥有这个字段不是强制性的，但你应该保留它。 否则，以后你修改类的结构，反序列化的时候就会失败.

## 如何生成serialVersionUID

可以使用serialver命令，在控制台生成serialVersionUID:
serialver -classpath . com.journaldev.serialization.Employee(bin/下的类名)

请注意，让程序自动生成serial version id不是必需的，我们可以自动给它分配我们想要的值，
它只是让反序列化的过程中知道生成的对象所对应的类和序列化对象的类是相同的类(serialVersionUID,version版本相同)，可以被反序列化就行了.

## 如何对序列化过程中的信息进行加密和解密

>例如我们有一些敏感信息在object中，在保存/检索之前，我们要对他们加密/解密，这里有4个方面我们提供的去改变序列化行为.如果类中存在这些方法，则他们用于序列化目的:
 
1. readObject(ObjectInputStream ois): 如果这个方法存在于类中，ObjectInputStream 的readObject方法将用这个方法从流中读取类.`(见: org.java.core.base.serialization.SubClass)`
2. writeObject(ObjectOutputStream oos): 如果这个方法存在于类中，ObjectInputStream 的writeObject方法将用这个方法将对象写入流.`(见: org.java.core.base.serialization.SubClass)`
3. Object writeReplace(): 如果存在此方法，则在序列化过程之后调用此方法，并将返回的对象序列化到流中。
4. Object readResolve(): 如果存在此方法，则在反序列化过程之后，将调用此方法以将最终对象返回给调用者程序。

>通常实现上述方法的时候，它保持为私有的，以便子类不能覆盖他们,他们用于序列化目的而已，保持他们私有
避免任何安全隐患

## 序列化在Java中有一些缺点:

1. 在不破坏java序列化过程的情况下，类结构不能改变很多。 因此，即使我们以后不需要某些变量，我们也需要保留它们以便向后兼容。
2. 序列化造成严重的安全风险，攻击者可以改变流的序列，对系统造成伤害，例如，用户角色被序列化，攻击者更改流值以使其成为管理员并运行恶意代码。

### 如何解决序列化过程的隐患

> Java Serialization Proxy模式是一种通过Serialization实现更高安全性的方法。 在此模式中，
 内部私有静态类用作序列化目的的代理类。此类的设计方式是维护主类的状态。 通过正确实现readResolve（）
 和writeReplace（）方法来实现此模式。见: org.java.core.base.serialization.Data
