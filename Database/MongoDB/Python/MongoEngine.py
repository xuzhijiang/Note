#!/usr/bin/bash python3
# -*- encoding: utf-8 -*-

import datetime
from mongoengine import *


connect('mongoengine_test', host='localhost', port=27017)

class Post(Document):
	title = StringField(required=True, max_length=200)
	content = StringField(required=True)
	author = StringField(required=True, max_length=50)
	published = DateTimeField(default=datetime.datetime.now)

post_1 = Post(title='title1', content='content1', author='author1')
post_1.save()
print(post_1.title)
post_1.title = 'replace title1'
post_1.save()
print(post_1.title)
