#### \_\_getattr__

> 我们可以覆盖这个\_\_getattr__方法,如果属性可以找到(from self.\_\_dict__[name])就不调用次方法,否则如果没有找到对应的属性就调用这个方法

#### self[key]

> 一般对象不能这么使用self[key] = value，只有dict，或者其子类才能这么使用，否则会抛出('obj' object does not support item assignment)

#### \_\_setattr__

> 如果我们覆盖了parent class的这个方法，在使用x.y为实例动态增加属性或者设置属性值的时候就会调用我们自己的\_\_setattr__,当然我们如果还需要parent class中此方法的功能，可以在此方法中调用super(subclass, self).\_\_setattr__(name, value)

> If \_\_setattr__() wants to assign to an instance attribute, it should not simply execute self.name = value — this would cause a recursive call to itself.Instead, it should insert the value in the dictionary of instance attributes, e.g., self.\_\_dict__[name] = value.parent class的\_\_setattr__做的工作其实就是insert value in the dictionary of instance attributes.

```python
>>> # this example uses __setattr__ to dynamically change attribute value to uppercase
>>> class Frob:
...     def __setattr__(self, name, value):
...         self.__dict__[name] = value.upper()
...
>>> f = Frob()
>>> f.bamf = "bamf"
>>> f.bamf
'BAMF'
```

#### dict

> 原生的dict不支持x.y = value这样的使用，提示'dict' object has no attribute 'b'

#### getattr(self, key, None)

> 如果这个对象没有key属性(或者获取key属性对应的值的时候抛出异常)，就返回默认的None,如下代码:

```python
def __getattr__(self, key):
        print('__getattr__')
        try:
            return self[key]
        except KeyError:
            print('here')
            raise AttributeError(r"'Field' object has no attribute %s" % key)

    def getValue(self, key):
        return getattr(self, key, None)
```

#### 什么时候调用\_\_setattr__

```python
def __init__(self, name, column_type, default, primary_key):
        self.name = name
        self.column_type = column_type
        self.primary_key = primary_key
        self.default = default
```

> 如上述代码，self.name = name的过程就就会自动调用\_\_setattr__，如果我们自己覆盖了parent class 的\_\_setattr__,就调用我们自己的

#### Python Formatter

* %015d: 整数一共占15位，不够的补0

#### class decorator

[参考](https://krzysztofzuraw.com/blog/2016/python-class-decorators.html)

```python
class decorator(object):

	def __init__(self, func):
		print('call init: func name: %s' % func.__name__)
		self.func = func

	def __call__(self, *args):
		print('call {func} with args: {args}'.format(func=self.func.__name__, args=args))
		self.func(*args)

@decorator
def foo(x, y):
	return x, y

# foo = decorator(foo)

if __name__ == '__main__':
	foo(1, 2)
	# foo(1, 2) <=> decorator(foo).__call__((1, 2))
```

#### Importing * from a Package

[官方文档](https://docs.python.org/3/tutorial/modules.html)

```shell
sound/                          Top-level package
      __init__.py               Initialize the sound package
      formats/                  Subpackage for file format conversions
              __init__.py
              wavread.py
      effects/                  Subpackage for sound effects
              __init__.py
              echo.py
              surround.py
              reverse.py
              other.py
              ...
      filters/                  Subpackage for filters
              __init__.py
              equalizer.py
```

```shell
from sound import effects
```

> 先从package sound `__init__.py`中导入effects，如果没有，
再假定他是sound目录下的一个模块(也就是假定sound目录下存在一个effects.py文件)，尝试去加载它:

>  when using syntax like import item.subitem.subsubitem,
 each item except for the last must be a package;
 the last item can be a module or a package but can’t be a
 class or function or variable defined in the previous item:

```shell
import sound.effects.echo
```

> import语句使用以下约定：如果包的__init__.py代码定义了名为__all__的列表，
则它将被视为遇到包import *时应导入的模块名称列表,文件sound / effects / __ init__.py可能包含以下代码：

```shell
__all__ = ["echo", "surround", "reverse"]
from sound.effects import *
dir()
```

> 即使sound / effects / __ init__.py中除了这3个模块还有其他模块，也不会被导入,只会导入__all__列表中的module，
加入有一个d.py在sound/effects下，如果__all__中包含了d，那么d模块也会被导入，或者如果effects/__init__.py中
也包含了d，优先导入effects/__init__.py中的d

> 使用from sound.effects import *,如果effects包的__init__.py中定义了__all__，就导入__all__列表里面的module
names, 如果没有定义__all__,就把__init__.py中的所有函数，变量，class都导入,注意不会导入effects下的模块(也就是
effects下定义的py文件)

> 如果未定义__all__，则from sound.effects import *中的语句不会将包sound.effects
中的所有子模块导入当前名称空间;它只确保已导入包sound.effects(可能在__init__.py中运行任何初始化代码），
然后导入包中定义的任何名称。这包括__init__.py定义的任何名称(以及显式加载的子模块,也就是__init__中import语句加载的
模块）。它还包括由以前的import语句显式加载的包的任何子模块。考虑以下代码：

```shell
import sound.effects.echo
import sound.effects.surround
from sound.effects import *
In this example, the echo and surround modules are imported in the current namespace
 because they are defined in the sound.effects package when the from...import
 statement is executed. (This also works when __all__ is defined.)
```

> 如果在from sound.effects import *之前执行过import sound.effects.echo，以及import sound.effects.surround，
那么，from sound.effects import *,之后dir(),会包含echo和surround,即使__init__.py中的__all__不包含echo和surround,

> 相对导入基于当前模块的名称。由于主模块的名称始终为“__main__”

#### __getitem__

> 如果CacheHandler没有实现\_\_getitem__,将raise 
'cache' object is not subscriptable,实现了getitem方法后，
如果没有找到'default'键，就默认调用getitem

```python
class CacheHandler():
  def __getitem__(self, name):
    return 'value'

cache = CacheHandler()
print(cache['default'])
```

### Install module and uninstall module

```shell
pip freeze > requirements.txt
pip install -r requirements.txt
```

### 获取对象的类型(自省)

运行时能够获得对象的类型:

比如type(),dir(),getattr(),hasattr(),isinstance().

```python
a = [1,2,3]
b = {'a':1,'b':2,'c':3}
c = True
print type(a),type(b),type(c) # <class 'list'> <class 'dict'> <class 'bool'>
print isinstance(a, list)  # True
```