# Object的方法

- public final native Class<?> getClass()
- public native int hashCode()
- public boolean equals(Object obj)
- protected native Object clone() throws CloneNotSupportedException
- public String toString()
- public final native void notify()
- public final native void notifyAll()
- public final native void wait(long timeout) throws InterruptedException
- public final void wait(long timeout, int nanos) throws InterruptedException
- public final void wait() throws InterruptedException
- protected void finalize() throws Throwable {}

# hashCode()方法

hashCode方法也是一个native方法。该方法返回对象的哈希码，主要使用在哈希表中，比如JDK中的HashMap。

>哈希码的通用约定如下：

- 如果2个对象使用equals方法进行比较并且相同的话，那么这2个对象的hashCode方法的值也必须相等.
- 在一个对象没有被改变的前提下，无论这个对象被调用多少次，hashCode方法都会返回相同的整数值
- 两个对象有相同的hashcode值，它们也不一定是equals相等的
- equals 方法被覆盖过，则 hashCode 方法也必须被覆盖
- 如果两个对象根据equals(Object)方法比较并不相等，则不要求在每个对象上调用hashCode都必须产生不同的结果。 但是应该意识到，为不相等的对象生成不同的hashcode结果可能会提高散列表的性能。
- 默认情况下，对象的哈希码是通过将该对象的内部地址转换成一个整数来实现的。

    如果2个对象使用equals方法进行比较并且相同的话，那么这2个对象的hashCode方法的值也必须相等,
    假如equals返回true,但是hashcode不相等的话,map中会存放2条相同的记录.

```java
// String的hashCode方法实现如下， 计算方法是 s[0]31^(n-1) + s[1]31^(n-2) + … + s[n-1]，
// 其中s[0]表示字符串的第一个字符，n表示字符串长度：
// 比如”fo”的hashCode = 102*31^1 + 111 = 3273， “foo”的hashCode = 102*31^2 + 111 * 31^1 + 111 = 101574 (‘f’的ascii码为102, ‘o’的ascii码为111)
public final class String{
    public int hashCode() {
        int h = hash;
        if (h == 0 && value.length > 0) {
            char val[] = value;
    
            for (int i = 0; i < value.length; i++) {
                h = 31 * h + val[i];
            }
            hash = h;
        }
        return h;
    }   
}
```

>hashCode在哈希表HashMap中的应用：

```java
// Student类，只重写了hashCode方法
public static class Student {

    private String name;
    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
    
}

Map<Student, String> map = new HashMap<Student, String>();
Student stu1 = new Student("fo", 11);
Student stu2 = new Student("fo", 22);
map.put(stu1, "fo");
map.put(stu2, "fo");

// 上面这段代码中，map中有2个元素stu1和stu2。但是这2个元素是在哈希表数组项中的同一个位置上，也就是在同一串链表中。 
// 既然stu1和stu2的hashCode相同，那么为什么两条数据都插到map里了呢 ？(也就是既然stu1和stu2的hashCode相同，map中应该只有一条数据)，
// 这是因为map判断重复数据的条件是两个对象(stu1和stu2)的哈希码相同并且(两个对象是同一个对象 or 两个对象相等[equals为true])。
```

>所以再给Student重写equals方法，这样只比较name的话，这样map就只有1个元素了

```java
// 对于上面的stu1和stu2来说，equals返回true，所以stu1和stu2的hashCode相同,
// 且equals为true,所以map判断stu1和stu2重复.所以只存储一条数据.
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Student student = (Student) o;
    return this.name.equals(student.name);
}
```

>这个例子直接说明了:如果根据equals方法，得到两个对象不相等，那么这2个对象的hashCode值可以相同。但是，2个不相等的(equals为false)对象的hashCode值不同的话可以提高哈希表的性能。 –> 上面例子一开始没有重写equals方法，导致两个对象不相等，但是这两个对象的hashCode值一样，所以导致这两个对象在同一串链表中，影响性能。

>当然，还有第三种情况，那就是equals方法相等，但是hashCode的值不相等.这种情况也就是违反了通用约定的第二点：
第二点：如果2个对象使用equals方法进行比较并且相同的话，那么这2个对象的hashCode方法的值也必须相等。 违反这一点产生的后果就是如果一个stu1实例是`new Student(“fo”, 11)`，stu2实例是`new Student(“fo”, 11)`，这2个实例是相等的[equals为true]，但是他们的hashCode不一样(如果不重写Object的hashCode，那么stu1和stu2的hashCode就是和2个对象的地址相关 ，所以hashCode不一样)，这样是导致哈希表中都会存入stu1实例和stu2实例，但是实际情况下，stu1和stu2是重复数据，只允许存在一条数据在哈希表中。所以这一点是非常重点的.

>`再强调一下：如果2个对象的equals方法相等，那么他们的hashCode值也必须相等(`否则会有重复数据存放在map中`)，反之，如果2个对象hashCode值相等，但是equals不相等，这样会影响性能(`会存入到同一串链表中`)，所以还是建议2个方法都一起重写。`

# clone方法

>创建并返回当前对象的一份拷贝。一般情况下，对于任何对象 x，表达式 x.clone() != x 为true，x.clone().getClass() == x.getClass() 也为true。

```java
// 由于Object类本身没有实现Cloneable接口，所以不重写clone方法并且进行调用的话会发生CloneNotSupportedException异常。
public class Object {
    // Object类的clone方法是一个protected的native方法
    protected native Object clone() throws CloneNotSupportedException;
}
```

# toString方法


```java
// Object对象的默认实现，即输出类的名字@实例的哈希码的16进制：
public String toString() {
    return getClass().getName() + "@" + Integer.toHexString(hashCode());
}
```

>toString方法的结果应该是一个简明但易于读懂的字符串。建议Object所有的子类都重写这个方法。

# wait() throws InterruptedException方法

    只能用在同步方法或者同步控制块中使用，否则会在运行时抛出 IllegalMonitorStateException
    wait方法相当于放弃了当前线程对对象监视器的所有者(也就是说释放了对象的锁)

    是一个native方法，也是final的，不允许子类重写。
    wait方法会让当前线程等待直到另外一个线程调用对象的notify或notifyAll方法，或者超过参数设置的timeout超时时间
    
    如果当前线程在等待之前或在等待时被任何其他线程中断，则会抛出InterruptedException异常
 
>假如一个线程叫T,在T线程中调用某一个对象锁的wait方法会让T线程释放当前的对象锁。出于线程调度目的，线程T是不可用并处于休眠状态，直到发生以下四件事中的任意一件：

1. 其他某个线程调用此对象的notify方法，并且线程T碰巧被任选为被唤醒的线程
2. 其他某个线程调用此对象的notifyAll方法
3. 其他某个线程调用Thread.interrupt方法中断线程T
4. 时间到了参数设置的超时时间。如果timeout参数为0，则不会超时，会一直进行等待

---

    之后，线程T会重新被OS调度。然后，该线程以常规方式与其他线程竞争，拉获得锁；
    
    一旦在此获得锁，线程T从wait方法的调用中返回,然后接着继续执行.

    在没有被通知、中断或超时的情况下，线程还可能会发生一个所谓的虚假唤醒 (spurious wakeup),所以要使用while循环来避免虚假唤醒.

# notify方法

    是一个native方法，并且也是final的，不允许子类重写

    唤醒一个在对象监视器上等待的线程。如果所有的线程都在此对象上等待，那么只会选择一个线程。
    选择是任意性的

    被唤醒的线程才可以继续处理。被唤醒的线程将以常规方式与在该对象上主动同步的其他所有线程进行竞争。

# notifyAll方法

    跟notify一样，唯一的区别就是会唤醒在此对象监视器上等待的所有线程，而不是一个线程。
    同样，如果当前线程不是对象监视器的所有者，那么调用notifyAll同样会发生IllegalMonitorStateException异常。

# finalize方法

```java
// finalize方法是一个protected方法，Object类的默认实现是不进行任何操作。
protected void finalize() throws Throwable { }
```

>该方法的作用是实例被垃圾回收器回收的时候触发的操作，就好比 “死前的最后一波挣扎”。

直接写个弱引用例子：`org.java.core.advanced.jvm.reference.FinalizeTest`