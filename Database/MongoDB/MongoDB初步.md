### 运行MongoDB服务器

我们需要创建一个目录,MongoDB服务器将使用这个目录存储其所有数据. MongoDB默认数据目录路径是c:\data\db。 

>请注意，如果该目录不存在，MongoDB将尝试创建它。 但事先创造它是件好事。

`C:\>md \data\db`

运行MongoDB:`C:\>C:\MongoDB\bin\mongod.exe`

这个MongoDB启动主数据库进程。 控制台结果中的等待连接消息确定mongod.exe进程已完成。

您可以使用mongod.exe的-dbpath选项为数据文件使用不同的路径:

`C:\>C:\MongoDB\bin\mongod.exe --dbpath D:\test\mongodb\data`

如果您的路径包含空格，则需要使用双引号:`C:\>C:\MongoDB\bin\mongod.exe --dbpath "D:\test\mongo db data"
`

通过使用mongo.exe连接mongod.exe.

为MongoDB日志创建一个配置文件:

`echo logpath=C:\MongoDB-logs\mongo.log > C:\MongoDB\mongod.conf`

把MongoDB配置为Windows服务，以便在系统启动并运行后立即启动,而无需保持命令提示符处于打开状态。

`C:\>C:\MongoDB\bin\mongod.exe --config C:\MongoDB\mongod.conf --install`

卸载MongodB:`C:\>C:\MongoDB\bin\mongod.exe --config C:\MongoDB\mongod.conf --remove`(注意要admin权限)
