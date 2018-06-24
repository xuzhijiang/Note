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
