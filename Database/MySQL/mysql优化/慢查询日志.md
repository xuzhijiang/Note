# SQL排查 - 慢查询日志

如果你一次性执行了20个sql,你怎么知道哪个sql执行的比较烂?怎么把烂的sql挑出来?我们可以通过慢查询日志找出来.

MySQL提供的一种日志记录，用于记录MySQL种响应时间超过阀值的SQL语句 （long_query_time，默认10秒,这个默认10秒太长,我们一般要改）,慢查询日志默认是关闭的；建议：开发调优是 打开，而 最终部署时关闭。

---
	检查是否开启了 慢查询日志 ：   show variables like '%slow_query_log%';

	临时开启：
		set global slow_query_log = 1;  --在内存种开启
		show variables like '%slow_query_log%';
		exit;//退出sql session
		service mysql restart //重启sql后,刚刚的设置就失效了

	永久开启：
		/etc/my.cnf 中追加配置：
		vi /etc/my.cnf 
		[mysqld]
		slow_query_log=1
		slow_query_log_file=/var/lib/mysql/localhost-slow.log // 日志存放路径

	慢查询阀值：
		show variables like '%long_query_time%';

	临时设置阀值：
		set global long_query_time = 5; --设置完毕后，重新登陆后起效 （不需要重启服务）

	永久设置阀值：

		/etc/my.cnf 中追加配置：
		vi /etc/my.cnf 
		[mysqld]
		long_query_time=3 // 需要重启mysql
		service mysql restart

    模拟慢sql:
	select sleep(4); //休眠4秒
	--查询超过阀值的SQL：  show global status like '%slow_queries%' ;
	
	(1)一旦sql执行超过了临界时间,慢sql就会自动被记录在了日志中，因此可以通过日志 查看具体的慢SQL。
	cat /var/lib/mysql/localhost-slow.log // 这个 目录/var/lib/mysql/可能无法创建成功日志,可以换到/tmp/下

	(2)通过mysqldumpslow工具查看慢SQL日志,可以通过一些过滤条件 快速查找出需要定位的慢SQL
	mysqldumpslow --help
	s：指定排序方式
	r:逆序排序 (reverse)
	l:锁定时间 
	g:正则匹配模式		

    // 数据构建
	select sleep(4);
	select sleep(5);
    select sleep(3);
    select sleep(3);
    
	--获取返回记录最多的3个SQL
		mysqldumpslow -s r -t 3  /var/lib/mysql/localhost-slow.log

	--获取访问次数最多的3个SQL
		mysqldumpslow -s c -t 3 /var/lib/mysql/localhost-slow.log

	--按照时间排序，前10条包含left join查询语句的SQL
		mysqldumpslow -s t -t 10 -g "left join" /var/lib/mysql/localhost-slow.log
	
	语法：
		mysqldumpslow 各种参数  慢查询日志的文件
---
