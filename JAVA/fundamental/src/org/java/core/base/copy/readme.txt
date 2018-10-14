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

