# python2中有range，和xrange
# python3中没有了xrange函数，剩下的range其实就是python2中xrange.
# xrange内存性能更好

for i in range(0, 20):
	print("i: %s" % i)

# note: python2中，range creates a list, so if you do range(1, 10000000) 
# it creates a list in memory with 9999999 elements.
# xrange is a sequence object that evaluates lazily.