#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os

print('Process (%s) starts...' % os.getpid())
# Only works on Linux/Unix/Mac
pid = os.fork()

if pid == 0:
	print('I am (%s) a child process, my parent is (%s)' % (os.getpid(), os.getppid()))
else:
	print('I (%s) just created a child process (%s)' % (os.getpid(), pid))