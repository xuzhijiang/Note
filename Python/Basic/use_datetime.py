from datetime import datetime, re

# 获取当前datetime
now = datetime.now()
print(now)
print(type(now))
print('\n>>>>>>>>>>\n')

# 指定日期和时间
dt = datetime(2018, 8, 17, 12, 20, 35)
print(dt)
print(type(datetime))
print('\n>>>>>>>>>\n')

# datetime转换为timestamp
# timestamp = 0 = 1970-1-1 00:00:00 UTC+0:00
# timestamp = 0 = 1970-1-1 08:00:00 UTC+8:00
dt = datetime(2018, 8, 17, 12, 20, 3, 400000)
print(dt.timestamp())
# Python的timestamp是一个浮点数,也就是unit is second
# 某些编程语言(如Java和JavaScript）的timestamp使用整数表示毫秒数，也就是unit is millisecond
# js: (new Date()).getTime()

# timestamp转换为datetime
t = 1429417200.0
print(datetime.fromtimestamp(t))
# 注意到timestamp是一个浮点数，它没有时区的概念，而datetime是有时区的。上述转换是在timestamp和本地时间做转换。
# 本地时间是指当前操作系统设定的时区。例如北京时区是东8区，则本地时间：
# 2015-04-19 12:20:00 UTC+8:00(Beijing)
# 2015-04-19 04:20:00 UTC+0:00(格林威治标准时间)

# timestamp也可以直接被转换到UTC标准时区的时间：
print(datetime.utcfromtimestamp(t))
print('\n>>>>>>>>>\n')

# str转换为datetime
cday = datetime.strptime('2015-6-1 18:19:59', '%Y-%m-%d %H:%M:%S')
print(cday)

# datetime转换为str
print(datetime.now().strftime('%a, %b %d %H:%M'))
print('\n>>>>>>>>>>>>\n')

# datetime加减
from datetime import timedelta

now = datetime.now()
print(now + timedelta(days=1))
print(now - timedelta(days=3, hours=13))
print('\n>>>>>>>>>>>>>>>\n')

# 本地时间转换为UTC时间
# 一个datetime类型有一个时区属性tzinfo，但是默认为None，所以无法区分这个datetime到底是哪个时区，除非强行给datetime设置一个时区：
from datetime import timezone
print(timedelta(hours=8))
print(type(timedelta(hours=8)))
tz_utc_8 = timezone(timedelta(hours=8))# 创建时区UTC+8:00
dt = datetime.now().replace(tzinfo=tz_utc_8)# 强制设置为UTC+8:00
print(dt)
print(type(dt))
print(datetime.now().strftime('%a, %b %H:%S'))
print(dt.strftime('%a, %b %H:%S'))
print('\n>>>>>>>>>>\n')

# 时区转换
print(timezone.utc)
print(type(timezone.utc))
utc_dt = datetime.utcnow().replace(tzinfo=timezone.utc)
print(utc_dt)
# astimezone()将转换时区为北京时间:
bj_dt = utc_dt.astimezone(timezone(timedelta(hours=8)))
print(bj_dt)
# astimezone()将转换时区为东京时间:
tokyo_dt = utc_dt.astimezone(timezone(timedelta(hours=9)))
print(tokyo_dt)
# astimezone()将bj_dt转换时区为东京时间:
tokyo_dt2 = bj_dt.astimezone(timezone(timedelta(hours=9)))
print(tokyo_dt2)
# 注：不是必须从UTC+0:00时区转换到其他时区，任何带时区的datetime都可以正确转换，例如上述bj_dt到tokyo_dt的转换。

# datetime表示的时间需要时区信息才能确定一个特定的时间，否则只能视为本地时间。

# 如果要存储datetime，最佳方法是将其转换为timestamp再存储，因为timestamp的值与时区完全无关。

# 假设你获取了用户输入的日期和时间如2015-1-21 9:01:30，以及一个时区信息如UTC+5:00，均是str，请编写一个函数将其转换为timestamp：
print('\n>>>>>>>>>>>>\n')

dt_str = '2015-1-21 9:01:30'
tz_str = 'UTC+5:00'

def str2datetime(str):
	dt = datetime.strptime(str, '%Y-%m-%d %H:%M:%S')
	print(type(dt))
	print(dt)
	return dt

def to_timestamp(dt_str, tz_str):
	dt = str2datetime(dt_str)
	tz_str = tz_str.split('+')[1].split(':')[0]
	ts = dt.replace(tzinfo=timezone(timedelta(hours=int(tz_str)))).timestamp()
	print('ts: %s' % ts)
	return ts

t1 = to_timestamp('2015-6-1 08:10:30', 'UTC+7:00')
assert t1 == 1433121030.0

t2 = to_timestamp('2015-5-31 16:10:30', 'UTC-09:00')
assert t2 == 1433121030.0, t2

print('ok')