### linux系统下bin的差异

1. sbin: The 's' in sbin means 'system'. Therefore, system binaries reside in sbin directories.
2. /bin: /bin和/sbin,用于在mounted较大的分区例如/usr等分区之前需要的在小的分区上使用的二进制命令，
目前，它主要用作关键程序(如/bin/sh）的标准位置，以及需要在单用户模式下可用的基本命令二进制文件.
3. /usr/bin: 普通用户程序.(由包`管理器`管理的二进制程序)
4. /usr/sbin: /usr/sbin与/usr/bin具有相同的关系，和/sbin与/bin一样。
5. /usr/local/bin(安装需要的权限:superuser (root) privileges required.): 用于不由`包管理器`管理的普通用户程序，例如，本地编译的包(例如使用make编译，并且使用make install安装到/usr/local/bin下的redis本地包的本地编译包)。 您不应将它们安装到/usr/bin中，因为将来在`包管理器`管理升级它所管理的软件包的时候，可能会在没有警告的情况下修改或删除它们。
6. /usr/local/sbin/

>/sbin: /sbin，与/bin不同，用于mount /usr等分区之前所需的系统管理程序(普通用户通常不使用）,基本系统二进制文件(system bin),
superuser (root) privileges required(要求超级用户权限).