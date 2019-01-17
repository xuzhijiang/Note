java.lang.System

System class is final and all of it’s members and methods are static so that we can’t 
subclass and override it’s behavior through inheritance.
系统类是final的，它的所有成员和方法都是静态的，所以我们不能成为它的子类，并通过继承覆盖它的行为。
(如果一个类是final的，就代表不能继承这个类)

System class in java doesn’t provide any public constructors.
 So we can’t instantiate this class 
(for argument sake, we can instantiate it using Java Reflection) 
and that’s why all of it’s methods are static.
System class在java中不提供任何的public构造器，因此我们不可以实例化这个类，那就是为什么它所有的
方法都是静态的。


