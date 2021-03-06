# 目录结构

**常见目录说明：**

- **/bin：** 存放二进制可执行文件(ls,cat,mkdir等)，常用命令一般都在这里；
- **/etc：**  存放系统管理和配置文件；
- **/home：**  存放所有用户文件的根目录，是用户主目录的基点，比如用户user的主目录就是/home/user，可以用~user表示；
- **/usr ：** 用于存放系统应用程序；
- **/opt：** 额外安装的可选应用程序包所放置的位置。一般情况下，我们可以把tomcat等都安装到这里；
- **/proc：**  虚拟文件系统目录，是系统内存的映射。可直接访问这个目录来获取系统信息；
- **/root：**  超级用户（系统管理员）的主目录（特权阶级^o^）；
- **/sbin:**  存放二进制可执行文件，只有root才能访问。这里存放的是系统管理员使用的系统级别的管理命令和程序。如ifconfig等；
- **/dev：** 用于存放设备文件；
- **/mnt：** 系统管理员安装临时文件系统的安装点，系统提供这个目录是让用户临时挂载其他的文件系统；
- **/boot：**  存放用于系统引导时使用的各种文件；
- **/lib ：**      存放着和系统运行相关的库文件 ；
- **/tmp：** 用于存放各种临时文件，是公用的临时文件存储点；
- **/var：** 用于存放运行时需要改变数据的文件，比方说各种服务的日志文件等；
- **/lost+found：**  这个目录平时是空的，系统非正常关机而留下“无家可归”的文件（windows下叫什么.chk）就在这里。

# linux系统下bin的差异

1. sbin: The 's' in sbin means 'system'. Therefore, system binaries reside in sbin directories.
2. /bin: /bin和/sbin,用于在mounted较大的分区例如/usr等分区之前需要的在小的分区上使用的二进制命令，
目前，它主要用作关键程序(如/bin/sh）的标准位置，以及需要在单用户模式下可用的基本命令二进制文件.
3. /usr/bin: 普通用户程序.(由包`管理器`管理的二进制程序)
4. /usr/sbin: /usr/sbin与/usr/bin具有相同的关系，和/sbin与/bin一样。
5. /usr/local/bin(安装需要的权限:superuser (root) privileges required.): 用于不由`包管理器`管理的普通用户程序，例如，本地编译的包(例如使用make编译，并且使用make install安装到/usr/local/bin下的redis本地包的本地编译包)。 您不应将它们安装到/usr/bin中，因为将来在`包管理器`管理升级它所管理的软件包的时候，可能会在没有警告的情况下修改或删除它们。
6. /usr/local/sbin/

>/sbin: /sbin，与/bin不同，用于mount /usr等分区之前所需的系统管理程序(普通用户通常不使用）,基本系统二进制文件(system bin),
superuser (root) privileges required(要求超级用户权限).

用户自己手动安装的软件都是装到/usr/local/下的,例如安装nginx: /usr/local/nginx
