#!/usr/bin/env python
# -*- encoding: utf-8 -*-

class MyClass:

	def method(self):
		return 'instance method called', self

	@classmethod
	def classmethod(cls):
		return 'class method called', cls

	@staticmethod
	def staticmethod():
		return 'static method called.'
# (but of course instance methods can accept more than just one parameter).
obj = MyClass()
print(type(obj.method()))
print(obj.method())
print(MyClass.method(obj))
# By the way, instance methods can also access the class itself 
# through the self.__class__ attribute. 
print('\r\n>>>>>>>>>>>\r\n')
print(obj.classmethod())
print('\r\n>>>>>>>>>>>\r\n')
print(obj.staticmethod())
# Now, let’s take a look at what happens when we attempt to 
# call these methods on the class itself - without creating an 
# object instance beforehand:
print('\r\n>>>>>>>>>>>\r\n')
print(MyClass.classmethod())
print('\r\n>>>>>>>>>>>\r\n')
print(MyClass.staticmethod())
print('\r\n>>>>>>>>>>>\r\n')
print(MyClass.method())

# A nice and clean way to do that is by using class methods
# as factory functions for the different kinds of pizzas we can create:
class Pizza:
    def __init__(self, ingredients):
        self.ingredients = ingredients

    def __repr__(self):
        return f'Pizza({self.ingredients!r})'

    @classmethod
    def margherita(cls):
        return cls(['mozzarella', 'tomatoes'])

    @classmethod
    def prosciutto(cls):
        return cls(['mozzarella', 'tomatoes', 'ham'])

# As you can see, we can use the factory functions to 
# create new Pizza objects that are configured the way we want them.
print('\r\n>>>>>>>>>>>\r\n')
print(Pizza.margherita())
print('\r\n>>>>>>>>>>>\r\n')
print(Pizza.prosciutto())
print('\r\n>>>>>>>>>>>\r\n')
# When To Use Static Methods
import math

class Pizza:
    def __init__(self, radius, ingredients):
        self.radius = radius
        self.ingredients = ingredients

    def __repr__(self):
        return (f'Pizza({self.radius!r}, '
                f'{self.ingredients!r})')

    def area(self):
        return self.circle_area(self.radius)

    @staticmethod
    def circle_area(r):
        return r ** 2 * math.pi

