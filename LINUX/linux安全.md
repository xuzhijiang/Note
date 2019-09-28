# 如何解决: There were 49 failed login attempts？

目前已有的解决办法如下：

1. 使用 ssh-keygen，禁用密码登陆
2. 使用PAM模块，参考HowTo: Configure Linux To Track and Log Failed Login Attempt Records，其实就是登陆尝试次数设置和延时

我的解决思路如下：

1. 通过脚本获取尝试登陆失败的IP
2. 将获取的IP写入到/etc/hosts.deny文件，进行屏蔽
3. 使用inotify-tools，监控/var/log/secure文件，来实时更新/etc/hosts.deny文件

- [http://www.novicex.cn/post/y20.html](http://www.novicex.cn/post/y20.html)