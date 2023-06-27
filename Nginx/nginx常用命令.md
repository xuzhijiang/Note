# nginx常用命令

```shell script
# 查看nginx常用命令参数
./nginx -h

# 显示 nginx 的版本
./nginx -v

# 启动nginx
./nginx
# 一旦启动后,通过ps -ef查看,你将会看到master进程和worker进程.
ps -ef | grep -i nginx

# 停止nginx
./nginx -s quit 
# 优雅的停止
./nginx -s stop

# 重新加载配置文件
./nginx -s reload
```