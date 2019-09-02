Java泛型是jdk1.5的一个新特性，以便在使用Collection类时删除type-casting(消除在使用集合类时常见的ClassCastException风险)，并在编译时提供类型检查

 我们可以使用泛型来创建泛型类型接口和类，我们也可以将它与方法一起使用。

泛型，这是在运行时避免ClassCastException

在创建列表时，我们已指定列表中的元素类型为String。 
因此，如果我们尝试在列表中添加任何其他类型的对象，程序将抛出编译时错误。

泛型类型是在类型上参数化的类或接口。 

E – Element (used extensively by the Java Collections Framework, for example ArrayList, Set etc.)
K – Key (Used in Map)
N – Number
T – Type
V – Value (Used in Map)
S,U,V etc. – 2nd, 3rd, 4th types

// T is meant to be a Type
// E is meant to be an Element (List<E>: a list of Elements)
// K is Key (in a Map<K,V>)
// V is Value (as a return value or mapped value)
// N - Number

Java泛型方法
有时我们不希望对整个类进行参数化，在这种情况下我们可以创建java泛型方法。
由于构造函数是一种特殊的方法，我们也可以在构造函数中使用泛型类型。


Java Generics Bounded Type Parameters(Java泛型有界类型参数):
假设我们要限制可在方法中传入的对象的类型，在一个方法中比较2个对象，我们要确保接受的
2个对象都是Caomparables，也就是2个对象都是可比较的，这个时候就要声明有界
类型参数:
 要声明有界类型参数，请列出类型参数的名称，
 然后是extends关键字，后跟其上限，类似于下面的方法:
 
public static <T extends Comparable<T>> int compare(T t1, T t2){
		return t1.compareTo(t2);
}

这些方法的调用类似于无界方法, 如果我们尝试使用任何不可比较的类，
它会抛出编译时错误。

有界类型参数可以与方法以及类和接口一起使用。

Java Generics也支持多个边界，即<T扩展A＆B＆C>。 
在这种情况下，A可以是接口或类。 如果A是类，那么B和C应该是接口。
 我们不能在多个边界中拥有多个类。
 
Java Generics supports multiple bounds also, 
i.e <T extends A & B & C>. In this case A can be an interface or class. 
If A is class then B and C should be interfaces. 
We can’t have more than one class in multiple bounds.
 
// 使用泛型通配符可以进行子类型化
//	List<? extends Integer> intList = new ArrayList<>();
//	List<? extends Number>  numList = intList;  
// OK. List<? extends Integer> is a subtype of List<? extends Number>

泛型不支持子类型：List<Number> numbers = new ArrayList<Integer>(); will not compile,
我们不能创建泛型数组：List<Integer>[] array = new ArrayList<Integer>[10]

List<Long> listLong = new ArrayList<Long>();
listLong.add(Long.valueOf(10));
List<Number> listNumbers = listLong; // compiler error
listNumbers.add(Double.valueOf(1.23));

正如您从上面的代码中可以看到，如果泛型支持子类型，我们可以轻松地将Double添加到Long列表中，
这会在遍历Long列表时在运行时导致ClassCastException。

### 为什么引入泛型?

1. 为了解决元素存储的安全性问题
2.  解决获取数据元素时，需要类型强转的问题

例如，一个存储年龄的ArrayList如果创建的时候不使用泛型，那么任何类型的元素就都可添加到集合中，
导致类型不安全，其次在遍历的时候需要强转，如果不小心在Integer类型的集合中放入一个String类型的，
那么在遍历就会出现一个类型转换异常.

