from multiprocessing import Process
import os

def run_proc(name):
	print('child prcess %s (%s) running....' % (name, os.getpid()))


if __name__ == '__main__':
	print('parent process (%s) running....' % os.getpid())
	p = Process(target=run_proc, args=('test',))
	print('child process will start')
	p.start()
	# join()方法可以等待子进程结束后再继续往下运行，通常用于进程间的同步。
	p.join()
	print('child process ends')