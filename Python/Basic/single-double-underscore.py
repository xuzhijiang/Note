class MyClass:
	def __init__(self):
		self._semiprivate = 'semiprivate'
		self.__superprivate = 'superprivate'
		self.__bar__ = 'foo'#不建议这么命名

obj = MyClass()
print(obj._semiprivate)
print('\r\n>>>>>>>>>>>>>\r\n')
print(obj._MyClass__superprivate)
print('\r\n>>>>>>>>>>>>>\r\n')
print(obj.__bar__)
print('\r\n>>>>>>>>>>>>>\r\n')
print(obj.__dict__)
print('\r\n>>>>>>>>>>>>>\r\n')
print(obj.__superprivate)
