Java中的Comparable和Comparator对于集合的排序非常有用。

一个类实现了Comparable(可比较的)之后就说明这个类是可以进行比较的.

Java提供了一些内置方法来对 基本类型数组（primitive types array）
或 包装类型数组(Wrapper classes array)或 列表(list) 进行排序。

 在这里，我们将首先学习如何对 基本类型和包装类型  的 (数组 or 列表) 进行排序，
 然后我们将使用java.lang.Comparable和
java.util.Comparator接口对 自定义类的 (数组 or 列表)进行排序。

 如果我们想在任何 自定义类 中使用Arrays or Collections的排序方法，
 那么我们的自定义类就要实现Comparable接口。
 
Comparable接口具有compareTo(T o)方法，该方法由排序方法使用，
您可以检查任何Wrapper，String或Date类来确认这一点。

我们应该以这样的方式覆盖此方法：
	如果“this”对象小于，等于或大于作为参数传递的对象，则返回负整数，零或正整数。

//如您所见，Employees数组按id按升序排序, ascending order.

But, in most real life scenarios, we want sorting based 
on different parameters.
但是，在大多数现实生活场景中，我们希望基于不同的参数进行排序。 
例如，作为首席执行官，我想根据薪资对员工进行分类，人力资源部门希望根据年龄对他们进行分类。

这时我们需要使用 Java Comparator接口的情形，
因为Comparable.compareTo（Object o）方法实现只能基于一个字段进行排序，
我们无法选择要对其进行排序的字段。 

Comparator(比较器):

Comparator接口的compare(Object o1, Object o2) 方法需要被实现，
此方法接受2个Object参数，它应该以这样的方式实现：

如果第一个参数小于第二个参数，则返回负int，
如果它们相等则返回零 如果第一个参数大于第二个参数，则为正int。

Comparable和Comparator接口使用Generics进行编译时类型检查。

所以现在我们知道如果我们想要对java对象数组或列表进行排序，
我们需要实现java Comparable接口来提供默认排序，
我们应该实现java Comparator接口来提供不同的排序方式。

我们还可以创建实现Comparator接口的单独类，然后使用它

java.lang.Comparable和java.util.Comparator
是强大的接口，可用于在java中提供排序对象。

Comparable vs(versus) Comparator:

1. Comparable interface用于提供单一的排序方式,
而Comparator接口用于提供不同的排序方式。

2. 对于使用Comparable，Class需要实现它，而对于使用Comparator，
我们不需要在类中进行任何更改。

3. Comparable接口在java.lang包中，而Comparator接口在java.util包中。

4. 我们不需要在客户端进行任何代码更改以使用Comparable，
Arrays.sort（）或Collection.sort（）方法自动使用类的compareTo（）方法。
而对于Comparator，客户端需要提供Comparator类以在compare（）方法中使用。

5. 带有Comparator参数的Collections.sort（）方法遵循策略模式