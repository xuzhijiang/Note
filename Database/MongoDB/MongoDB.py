#!/usr/bin/bash python3
# -*- encoding: utf-8 -*-

from pymongo import MongoClient


# client = MongoClient() #default host and port: localhost:27017
# client = MongoClient('localhost', 27017)
client = MongoClient('mongodb://localhost:27017')

# db = client['pymongo_test']
db = client.pymongo_test

posts = db.posts
post_data1 = {
	'title': 'Python and MongoDb',
	'content': 'learn MongoDb in Python',
	'author': 'xzj'
}

one_result = posts.insert_one(post_data1)
print('One post: {0}'.format(one_result.inserted_id))

post_data2 = {
	'title': 'title2',
	'content': 'content2',
	'author': 'author2'
}

post_data3 = {
	'title': 'title3',
	'content': 'content3',
	'author': 'author3'
}

post_data4 = {
	'title': 'title2',
	'content': 'content4',
	'author': 'author4'
}

many_result = posts.insert_many([post_data2, post_data3, post_data4])
print('Many post: {0}'.format(many_result.inserted_ids))

title3_post = posts.find_one({'title': 'title3'})
print(title3_post)

title2_post = posts.find({'title': 'title2'})
print(title2_post)

for post in title2_post:
    print(post)
