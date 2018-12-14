# Python语言特性

## 1 Python的函数参数传递

看两个例子:

```python
a = 1
def fun(a):
    a = 2
fun(a)
print a  # 1
```

```python
a = []
def fun(a):
    a.append(1)
fun(a)
print a  # [1]
```

```python
a = 1
def fun(a):
    print "Address: ",id(a)   # Address: 41322472
    # number对象是不可变的，所以新创建一个int类型的对象，
    # 然后形参a指向这个新创建的对象,而函数外的变量a依然指向1
    a = 2
    print "Address: ",id(a), id(2)   # Address: 41322448 41322448
print "Address: ",id(a), id(1)  # Address: 41322472 41322472
fun(a)
print a  # 1
```

```python
a = []
def fun(a):
    print "func_in",id(a)  # func_in 53629256
    a.append(1)
print "func_out",id(a)     # func_out 53629256
fun(a)
print a  # [1]
```

(对比java理解)在python中,类型是属于对象的，而不是变量，变量是没有类型的，变量指向的对象是有类型的，python中所有的一切都是对象，而对象有两种,“可变对象”（mutable）与“不可变对象”（immutable）对象。在python中，string, tuple, 和number是不可更改的对象，而 list, dict, set 等则是可以修改的对象。修改不可变对象（str、tuple）需要开辟新的空间,修改可变对象（list等）不需要开辟新的空间

## 2 Python中的元类(metaclass)

这个非常的不常用,但是像ORM这种复杂的结构还是会需要的,详情请看:http://stackoverflow.com/questions/100003/what-is-a-metaclass-in-python

## 3 @staticmethod和@classmethod

```python
def foo(x):
    print "executing foo(%s)"%(x)

class A(object):
    def foo(self,x):
        print "executing foo(%s,%s)"%(self,x)

    @classmethod
    def class_foo(cls,x):
        print "executing class_foo(%s,%s)"%(cls,x)

    @staticmethod
    def static_foo(x):
        print "executing static_foo(%s)"%x

param = 3
a=A()
a.foo(param)
A.class_foo(param)
```

| \\      | 实例方法  | @classmethod  | @staticmethod  |
| :------ | :------- | :------------- | :-------------- |
| a = A() | a.foo(x) | a.class_foo(x) | a.static_foo(x) |
| A       | 不可用    | A.class_foo(x) | A.static_foo(x) |

## 4 类变量和实例变量

**类变量：**

> ​	是可在类的所有实例之间共享的值（也就是说，它们不是单独分配给每个实例的）。例如下例中，num_of_instance 就是类变量，用于跟踪存在着多少个Test 的实例。

**实例变量：**

> 实例化之后，每个实例单独拥有的变量。

```python
class Test(object):  
    num_of_instance = 0  
    def __init__(self, name):  
        self.name = name  
        Test.num_of_instance += 1  
```

> 补充的例子

```python
class Person:
    name="aaa"

p1=Person()
p2=Person()
p1.name="bbb"
print p1.name  # bbb
print p2.name  # aaa
print Person.name  # aaa
```
因为相同名称的实例属性将屏蔽掉类属性,这里`p1.name="bbb"`是实例调用了实例属性,这其实和上面第一个问题一样,就是函数传参的问题,`p1.name`一开始是指向的类属性`name="aaa"`,但是在实例的作用域里把类属性的引用改变了,就变成了一个实例属性,self.name不再引用Person的类属性name了.

可以看看下面的例子:

```python
class Person:
    name=[]

p1=Person()
p2=Person()
p1.name.append(1)
print p1.name  # [1]
print p2.name  # [1]
print Person.name  # [1]
```

参考:http://stackoverflow.com/questions/6470428/catch-multiple-exceptions-in-one-line-except-block

## 6 字典推导式

可能你见过列表推导时,却没有见过字典推导式,在2.7中才加入的:

```python
d = {key: value for (key, value) in iterable}
```

## 7 Python中单下划线和双下划线

```python
class MyClass():
    def __init__(self):
            self.__superprivate = "Hello"
             self._semiprivate = ", world!"

mc = MyClass()
print mc.__superprivate
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
AttributeError: myClass instance has no attribute '__superprivate'
>>> print mc._semiprivate
, world!
>>> print mc.__dict__
{'_MyClass__superprivate': 'Hello', '_semiprivate': ', world!'}
```

* 以单下划线开头_x，表示这是一个保护成员，只有类对象和子类对象自己能访问到这些变量。以单下划线开头的变量和函数被默认当作是内部函数，使用from module improt \*时不会被获取，但是使用import module可以获取

* 以单下划线结尾, 例如class_,仅仅是为了区别该名称与关键词

* 双下划线开头__x，表示为私有成员，只允许类本身访问，子类也不行。但是python的私有方法并不是一个真正的私有方法，因为它还是能够被外部访问的，只不过是不能被简单的直接调用了,在文本上被替换为_class__method

* 双下划线开头，双下划线结尾__x__。一种约定，Python内部的名字，用来区别其他用户自定义的命名,以防冲突,他不是私有成员。是一些 Python 的“魔术”对象，表示这是一个特殊成员，例如：定义类的时候，若是添加__init__方法，那么在创建类的实例的时候，实例会自动调用这个方法，一般用来对实例的属性进行初使化，Python不建议将自己命名的方法写为这种形式。

详情见:http://stackoverflow.com/questions/1301346/the-meaning-of-a-single-and-a-double-underscore-before-an-object-name-in-python

## 8 字符串格式化:%和.format

### %

```python
print("hi there %s" % name)
```

但是,如果name恰好是(1,2,3),它将会抛出一个TypeError异常.为了保证它总是正确的,你必须这样做:

```python
print("hi there %s" % (name,))
```
### .format

.format没有%那样的问题

```python
value = 'arg'
'value is {0}'.format(value)
```

## 9 迭代器(iterable)和生成器(generator)

这个是stackoverflow里python排名第一的问题,值得一看: http://stackoverflow.com/questions/231767/what-does-the-yield-keyword-do-in-python

这里有个关于生成器的创建问题面试官有考：
问： 将列表生成式中[]改成() 之后数据结构是否改变？ 
答案：改变，从(迭代器)列表变为生成器

```python
>>> L = [x*x for x in range(10)]
>>> L
[0, 1, 4, 9, 16, 25, 36, 49, 64, 81]
>>> g = (x*x for x in range(10))
>>> g
<generator object <genexpr> at 0x0000028F8B774200>
next(g) #可以通过next()函数获得generator的下一个返回值
# 没有更多的元素时，抛出StopIteration的错误。所以我们一般不用next()
```
通过列表生成式，可以直接创建一个列表。但是，受到内存限制，列表容量肯定是有限的。而且，创建一个包含百万元素的列表，不仅是占用很大的内存空间，如：我们只需要访问前面的几个元素，后面大部分元素所占的空间都是浪费的。因此，没有必要创建完整的列表（节省大量内存空间）。在Python中，我们可以采用生成器：边循环，边计算的机制—>generator

generator也是可迭代对象，因此可以这么调用
```python
for v in g:
  print(v)
```
可以把函数改成generator，只需要把return改成yield即可，遇到yield的时候就中断返回值，
下次继续执行，也是通过for循环来迭代这样的generator,对于函数改成的generator来说，遇到return语句或者执行到函数体最后一行语句，就是结束generator的指令，for循环随之结束.
普通函数和generator函数，普通函数调用直接返回结果,generator函数的“调用”实际返回一个generator对象(通过next()方法获得生成器的下一个返回值).

### generator分类

* 不包含yield关键字的generator
* 带yield的generator function

#### generator

一个函数执行到某一个位置产生一个值，然后它被冻结，再次被唤醒的时候还是
从这个位置继续去执行，那么每次执行的时候就可能产生一个数据，这样这个函数
不停的执行就产生了源源不断的数据，这样的函数就叫generator.

generator一般和循环语句一起使用,generator相比一次列出所有内容的advantages

* 更节省存储空间
* 响应更加迅速
* 使用更加灵活

### 迭代器(Iterator)

凡是可作用于next()函数的对象都是Iterator(迭代器)类型，它们表示一个惰性计算的序列；

迭代器(Iterator)和可迭代对象(Iterable)的区别?

可以直接作用于for循环的对象统称为可迭代对象：Iterable

Note: 以通过iter()函数获得一个Iterator对象

```python
from collections import Iterable
print(isinstance([], Iterable))# True
print(isinstance('abc', Iterable))# True

from collections import Iterator
print(isinstance([], Iterator))#False
isinstance({}, Iterator)#False
```

## 10 `*args` and `**kwargs`

用`*args`和`**kwargs`只是为了方便并没有强制使用它们.

当你不确定你的函数里将要传递多少参数时你可以用`*args`.例如,它可以传递任意数量的参数:

```python
>>> def print_everything(*args):
        for count, thing in enumerate(args):
...         print '{0}. {1}'.format(count, thing)
...
>>> print_everything('apple', 'banana', 'cabbage')
0. apple
1. banana
2. cabbage
```

相似的,`**kwargs`允许你使用没有事先定义的参数名:

```python
>>> def table_things(**kwargs):
...     for name, value in kwargs.items():
...         print '{0} = {1}'.format(name, value)
...
>>> table_things(apple = 'fruit', cabbage = 'vegetable')
cabbage = vegetable
apple = fruit
```

你也可以混着用.命名参数首先获得参数值然后所有的其他参数都传递给`*args`和`**kwargs`.命名参数在列表的最前端.例如:

```
def table_things(titlestring, **kwargs)
```

`*args`和`**kwargs`可以同时在函数的定义中,但是`*args`必须在`**kwargs`前面.

当调用函数时你也可以用`*`和`**`语法.例如:

```python
>>> def print_three_things(a, b, c):
...     print 'a = {0}, b = {1}, c = {2}'.format(a,b,c)
...
>>> mylist = ['aardvark', 'baboon', 'cat']
>>> print_three_things(*mylist)

a = aardvark, b = baboon, c = cat
```

就像你看到的一样,它可以传递列表(或者元组)的每一项并把它们解包.注意必须与它们在函数里的参数相吻合.当然,你也可以在函数定义或者函数调用时用*.

http://stackoverflow.com/questions/3394835/args-and-kwargs

## 11 面向切面编程AOP和装饰器

这种在运行时，动态地将代码切入到类的指定方法、指定位置上的编程思想就是面向切面的编程

装饰器是一个很著名的设计模式，经常被用于有切面需求的场景，较为经典的有插入日志、性能测试、事务处理等。装饰器是解决这类问题的绝佳设计，有了装饰器，我们就可以抽离出大量函数中与函数功能本身无关的雷同代码并继续重用。概括的讲，**装饰器的作用就是为已经存在的对象添加额外的功能。**

有多个装饰器的时候，最下面的装饰器(紧挨def关键字的)先起作用

```python
def makebold(fn):
    def wrapped():
        return "<b>" + fn() + "</b>"
    return wrapped

def makeitalic(fn):
    def wrapped():
        return "<i>" + fn() + "</i>"
    return wrapped

@makebold
@makeitalic
def hello():
    return "hello world"

print hello() ## returns "<b><i>hello world</i></b>"
```

## 12 鸭子类型(file-like Object)

file-like Object不要求从特定类继承，只要写个对应的鸭子方法就行

“当看到一只鸟走起来像鸭子、游泳起来像鸭子、叫起来也像鸭子，那么这只鸟就可以被称为鸭子。”

我们并不关心对象到底是不是鸭子，只关心行为。

比如在python中，有很多file-like的东西，比如StringIO(就是在内存中创建的file-like Object，常用作临时缓冲),GzipFile,socket。它们有很多相同的方法，我们把它们当作文件使用。

鸭子类型在动态语言中经常使用，非常灵活，使得python不想java那样专门去弄一大堆的设计模式。

## 14 新式类和旧式类

> 一个旧式类的深度优先的例子

```python
class A():
    def foo1(self):
        print ("A")
class B(A):
    def foo2(self):
        pass
class C(A):
    def foo1(self):
        print ("C")
class D(B, C):
    pass

d = D()
d.foo1()

# A
```

**按照经典类的查找顺序`从左到右深度优先`的规则，在访问`d.foo1()`的时候,D这个类是没有的..那么往上查找,先找到B,里面没有,深度优先,访问A,找到了foo1(),所以这时候调用的是A的foo1()，从而导致C重写的foo1()被绕过**

## 15 `__new__`和`__init__`的区别

这个`__new__`确实很少见到,先做了解吧.

1. `__new__`方法会返回一个创建的实例,而`__init__`什么都不返回.
2. 只有在`__new__`返回一个cls的实例时后面的`__init__`才能被调用.
3. 当创建一个新实例时调用`__new__`,初始化一个实例时用`__init__`.
4. 当你需要控制一个新实例初始化的时候才是用__new__.
5. __new__是实例创建的第一步，然后才返回类的实例
6. 我们通常不要override __new__方法

[stackoverflow](http://stackoverflow.com/questions/674304/pythons-use-of-new-and-init)

ps: `__metaclass__`是创建类时起作用.所以我们可以分别使用`__metaclass__`,`__new__`和`__init__`来分别在类创建,实例创建和实例初始化的时候做一些小手脚.

## 16 单例模式

> ​	单例模式是一种常用的软件设计模式。在它的核心结构中只包含一个被称为单例类的特殊类。通过单例模式可以保证系统中一个类只有一个实例而且该实例易于外界访问，从而方便对实例个数的控制并节约系统资源。如果希望在系统中某个类的对象只能存在一个，单例模式是最好的解决方案。
>
> `__new__()`在`__init__()`之前被调用，用于生成实例对象。利用这个方法和类的属性的特点可以实现设计模式的单例模式。单例模式是指创建唯一对象
**这个绝对常考啊.绝对要记住1~2个方法,当时面试官是让手写的.**

### 1 使用`__new__`方法

```python
class Singleton(object):
    def __new__(cls, *args, **kw):
        if not hasattr(cls, '_instance'):
            orig = super(Singleton, cls)
            cls._instance = orig.__new__(cls, *args, **kw)
        return cls._instance

class MyClass(Singleton):
    a = 1
```

### 2 共享属性

创建实例时把所有实例的`__dict__`指向同一个字典,这样它们具有相同的属性和方法.

```python

class Borg(object):
    _state = {}
    def __new__(cls, *args, **kw):
        ob = super(Borg, cls).__new__(cls, *args, **kw)
        ob.__dict__ = cls._state
        return ob

class MyClass2(Borg):
    a = 1
```

### 3 装饰器版本

```python
def singleton(cls):
    instances = {}
    def getinstance(*args, **kw):
        if cls not in instances:
            instances[cls] = cls(*args, **kw)
        return instances[cls]
    return getinstance

@singleton
class MyClass:
  ...
```

### 4 import方法

作为python的模块是天然的单例模式

```python
# mysingleton.py
class My_Singleton(object):
    def foo(self):
        pass

my_singleton = My_Singleton()

# to use
from mysingleton import my_singleton

my_singleton.foo()

```

## 17 Python中的作用域

Python中，一个变量的作用域总是由在代码中被赋值的地方所决定的。

当 Python 遇到一个变量的话他会按照这样的顺序进行搜索：

本地作用域（Local）→当前作用域被嵌入的本地作用域（Enclosing locals）→全局/模块作用域（Global）→内置模块作用域（Built-in）

## 18 GIL线程全局锁

线程全局锁(Global Interpreter Lock),即Python为了保证线程安全而采取的独立线程运行的限制,说白了就是一个核只能在同一时间运行一个线程.**对于io密集型任务，python的多线程起到作用，但对于cpu密集型任务，python的多线程几乎占不到任何优势，还有可能因为争夺资源而变慢。**

见[Python 最难的问题](http://www.oschina.net/translate/pythons-hardest-problem)

解决办法就是多进程和下面的协程(协程也只是单CPU,但是能减小切换代价提升性能).

## 22 Python函数式编程

python中函数式编程支持:

filter 函数的功能相当于过滤器。调用一个布尔函数`bool_func`来迭代遍历每个seq中的元素；返回一个使`bool_seq`返回值为true的元素的序列。

```python
>>>a = [1,2,3,4,5,6,7]
>>>b = filter(lambda x: x > 5, a)
>>>print b
>>>[6,7]
```

map函数是对一个序列的每个项依次执行函数，下面是对一个序列每个项都乘以2：

```python
>>> a = map(lambda x:x*2,[1,2,3])
>>> list(a)
[2, 4, 6]
```

reduce函数是对一个序列的每个项迭代调用函数，下面是求3的阶乘：

```python
>>> reduce(lambda x,y:x*y,range(1,4))
6
```

## 23 Python里的拷贝

引用和copy(),deepcopy()的区别

```python
import copy
a = [1, 2, 3, 4, ['a', 'b']]  #原始对象

b = a  #赋值，传对象的引用
c = copy.copy(a)
d = copy.deepcopy(a)

a.append(5)  #修改对象a
a[4].append('c')  #修改对象a中的['a', 'b']数组对象
a[0] = 100

print 'a = ', a
print 'b = ', b
print 'c = ', c
print 'd = ', d

输出结果：
a =  [100, 2, 3, 4, ['a', 'b', 'c'], 5]
b =  [100, 2, 3, 4, ['a', 'b', 'c'], 5]
c =  [1, 2, 3, 4, ['a', 'b', 'c']]
d =  [1, 2, 3, 4, ['a', 'b']]
```

 * 如果是一个不可变对象，浅拷贝和深拷贝效果一样，拷贝出来的对象都是指向和源对象相同的地址(可以用id()方法查看)，因为他们拷贝的是不可变对象

* 但是如果拷贝的是可变对象，比如拷贝的是list,浅拷贝拷贝出来的对象地址就是一个新地址，会新开辟一块空间,而且容器内的元素不论是不可变对象，还是不可变对象，地址依然指向源对象元素的地址，也就是浅拷贝是在另一块地址中创建一个新的变量或容器，但是容器内的元素的地址均是源对象的元素的地址的拷贝。也就是说新的容器中指向了旧的元素（ 新瓶装旧酒 ）。

* 如果是拷贝list，深拷贝，拷贝出来的对象地址就是一个新地址，会新开辟一块空间，容器内如果是不可变对象，地址依然和源对象中元素的地址一样，如果是可变对象，地址就是一个全新的地址，这个就是和浅拷贝的区别.

## 27 read,readline和readlines

* read        读取整个文件
* readline    读取下一行,使用生成器方法
* readlines   读取整个文件到一个迭代器以供我们遍历
