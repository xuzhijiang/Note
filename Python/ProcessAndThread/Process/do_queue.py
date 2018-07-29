from multiprocessing import Process, Queue
import os, time, random

def write(q):
	print('process %s write' % os.getpid())
	for i in ['A', 'B', 'C']:
		q.put(i)
		print('write %s into queue.' % i)
		time.sleep(random.random())


def read(q):
	print('process %s read' % os.getpid())
	while True:
		v = q.get(True)
		print('get value from queue: %s' % v)


if __name__ == '__main__':
	print('parent process %s' % os.getpid())
	q = Queue()
	pw = Process(target=write, args=(q,))
	pr = Process(target=read, args=(q,))
	pw.start()
	pr.start()
	# 等待pw结束:
	pw.join()
	pr.terminate()