 Java Object类附带了native clone（）方法，该方法返回现有实例的副本。

如何使用clone方法？
 
	 要使用java对象克隆方法，我们必须实现标记接口java.lang.Cloneable.
(他只是一个标记接口，接口里面没有任何方法要实现)	 
 ，以便它不会在运行时抛出CloneNotSupportedException。
  对象克隆也是受保护(protected)的方法，因此我们必须重写它以与其他类一起使用。

Notice: 要使用clone方法，我们必须implements Cloneable接口.

Shallow Cloning:

Default implementation of java clone object is using shallow copy, 
something like below using reflection.
java clone对象的默认实现是使用浅拷贝, 类似下面使用反射。

Deep Cloning:
在深度克隆中，我们必须逐个复制字段。 我们可以覆盖下面的克隆方法进行深度克隆。

1. 仅当您的类具有primitives and immutable variables.时，才使用默认的Object clone（）方法。
请注意，这也适用于继承。您必须检查所有要扩展的类，直到the Object level.。

2. 如果您的类主要由可变对象组成，您还可以定义复制构造函数。

3. 通过在重写克隆方法中调用super.clone（）来利用Object clone（）方法，然后对深度复制可变字段进行必要的更改。

4. 如果您的类是可序列化的，那么您也可以使用序列化进行克隆。 
然而，它会带来性能损失，因此在使用此方法进行克隆之前，请进行一些基准测试。

我希望您对对象克隆方法有一些了解，以及如何正确覆盖它而不会产生任何不利影响。 without any adverse effect.