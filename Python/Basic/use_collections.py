#!/usr/bin/env python
# -*- coding: utf-8 -*-

from collections import namedtuple

# namedtuple('名称', [属性list])
# namedtuple是一个函数，它用来创建一个自定义的tuple类，并且给这个类个名字，并且规定了tuple元素的个数，
# 并可以用属性而不是索引来引用tuple的某个元素。它具备tuple的不变性

b = namedtuple('ball', ('b1', 'b2')) #生成一个命名元祖类
bb = b(11, 22) # 生成一个命名元祖的实例
print(bb.b1)
print(bb.b2)
print(bb[0])
print(bb[1])
print(type(bb))
print(type(b))
print(isinstance(b, tuple)) # False
print(isinstance(b, type)) # True
print(isinstance(bb, tuple)) # True
print(isinstance(bb, b)) # True


#使用list存储数据时，按索引访问元素很快，但是插入和删除元素就很慢了，
#因为list是线性存储，数据量大的时候，插入和删除效率很低。
# deque是为了高效实现插入和删除操作的双向列表，适合用于队列和栈：

from collections import  deque
q = deque(['x', 'y', 1, True])
q.append('z')
q.appendleft('l')
print(q)
# deque除了实现list的append()和pop()外，
# 还支持appendleft()和popleft()，这样就可以非常高效地往头部添加或删除元素。


# 使用dict时，如果引用的Key不存在，就会抛出KeyError。
# 如果希望key不存在时，返回一个默认值，就可以用defaultdict：
from collections import defaultdict
d = defaultdict(lambda: 'N/K')
d['key1'] = 'abc'
print(d['key1'])
print(d['key2'])
# 注意默认值是调用函数返回的，而函数在创建defaultdict对象时传入。defaultdict的其他行为跟dict是完全一样的。



# 使用dict时，Key是无序的。在对dict做迭代时，我们无法确定Key的顺序。
# 如果要保持Key的顺序，可以用OrderedDict： 
from collections import OrderedDict
d = dict([('a', 1), ('b', 2), (3, 'c'), ('d', 4), ('e', 5)])
print(d)
od = OrderedDict([('a', 1), ('b', 2), (3, 'c'), ('d', 4), ('e', 5)])
print(od)
# 注意，OrderedDict的Key会按照插入的顺序排列，不是Key本身排序：

orderdict = OrderedDict()
orderdict['z'] = 1
orderdict['y'] = 2
orderdict['x'] = 100
print(list(orderdict.keys()))