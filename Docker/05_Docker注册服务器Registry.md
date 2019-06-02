# 访问 Docker 仓库

一个容易混淆的概念是注册服务器（Registry）。实际上`注册服务器`是管理仓库的具体服务器，每个`注册服务器`上可以有多个仓库，而每个仓库下面有多个镜像。从这方面来说，仓库可以被认为是一个具体的项目或目录。

>例如对于仓库地址 dl.dockerpool.com/ubuntu 来说，dl.dockerpool.com 是注册服务器地址，ubuntu 是仓库名,dl.dockerpool.com可以有多个仓库.

大部分时候，并不需要严格区分这两者的概念。