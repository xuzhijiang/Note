# hashlib提供了常用的摘要算法,MD5, SHA1等
# 摘要算法又称哈希算法、散列算法
# 摘要算法就是通过摘要函数对任意长度的数据data计算出固定长度的摘要digest
# 摘要函数是一个单向函数，计算f(data)很容易，但通过digest反推data却非常困难
# 摘要算法不是加密算法，不能用于加密(因为无法通过摘要反推明文），只能用于防篡改

import hashlib

md5 = hashlib.md5()
md5.update('aaaaaa bbbbb ccccc'.encode('utf-8'))
print(md5.hexdigest())#32位的16进制字符串


sha1 = hashlib.sha1()
sha1.update('aaaaa bbbb'.encode('utf-8'))
sha1.update('cccc'.encode('utf-8'))
print(sha1.hexdigest())#40位的16进制字符
# 比SHA1更安全的算法是SHA256和SHA512，不过越安全的算法不仅越慢，而且摘要长度更长。

# 摘要算法的应用:
# 网站保存口令的方式是不存储用户的明文口令，而是存储用户口令的摘要

db = {
    'michael': 'e10adc3949ba59abbe56e057f20f883e',
    'bob': '878ef96e86145580c38c87f0410ad153',
    'alice': '99b1c2188db85afee403b1536010c2c9'
}

def login(user, password):
	md5 = hashlib.md5()
	md5.update(password.encode('utf-8'))
	return True if md5.hexdigest() == db[user] else False

assert login('michael', '123456')
assert login('bob', 'abc999')
assert login('alice', 'alice2008')
assert not login('michael', '1234567')
assert not login('bob', '123456')
assert not login('alice', 'Alice2008')
print('ok')

# 对简单口令加强保护
# 通过对原始口令加一个复杂字符串来实现，俗称“加盐”,避免简单口令被破解
# 通过把登录名作为Salt的一部分来计算MD5，从而实现相同口令的用户也存储不同的MD5
import random

def get_md5(s):
	return hashlib.md5(s.encode('utf-8')).hexdigest()

class User(object):

	def __init__(self, username, password):
		self.username = username
		self.salt = ''.join([chr(random.randint(48, 122)) for i in range(20)])
		self.password = get_md5(password + self.salt)

db = {
    'michael': User('michael', '123456'),
    'bob': User('bob', 'abc999'),
    'alice': User('alice', 'alice2008')
}

def login(username, password):
	user = db[username]
	return user.password == get_md5(password + user.salt)

assert login('michael', '123456')
assert login('bob', 'abc999')
assert login('alice', 'alice2008')
assert not login('michael', '1234567')
assert not login('bob', '123456')
assert not login('alice', 'Alice2008')
print('ok')
