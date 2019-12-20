# 常用的命令

```shell script
# shell脚本开头必须要有这个
#!/bin/bash

# 退出
exit 0

# 切换到上一次操作的目录
cd -

#导入环境变量,说白了就是执行local.env这个shell脚本,这个脚本中可能有函数,可能有变量,source完之后,就可以使用了
source ./local.env
# 针对一个shell脚本，使用". script.sh", "bash script.sh", "source script.sh"效果都是一致的

#读取当前时间
CURRENT_TIME=`date +%Y%m%d%H%M%S`
echo $CURRENT_TIME

# 输出结果: 当输入当前命令的时候,用户所在的目录
echo $PWD
```

# 函数

    返回值: 如果函数执行过程中要返回,可以直接: return 0, 而且不用加引号

```shell
# $1 $2 $3分别对应run, stop, restart,可以直接在catalina.sh中使用.
catalina.sh run stop restart
```

```shell script
# 打印日志函数
function log_msg()
{
	# $0表示本身
	# $@表示: 除了$0后的所有的参数
	# #号表示从左侧开始匹配字符，之后删除所匹配的字符
	# .号表示匹配点号
	# ?表示匹配任何一个字符
	# aaa/ 可以匹配 ???/, bb/ 不可以匹配 ???/
	echo "${0#./} : $@"
}

# 调用函数,可以发现双引号中间是可以引用变量的
log_msg "$PWD"

TARGET="XXX"
log_msg "target = $TARGET"
```

```shell script
create_path(){
    # #检查目录是否存在，不存在则创建
	if [ ! -d "$1" ]
	then
		mkdir $1
	fi
    # 如果$2为-del,则删除$1下面所有文件
	if [ "$2" == "-del" ]
	then
		rm $1/* -rf
	fi
}

# 调用
create_path target -del
```

```shell script
# 初始化变量
OLD_BUILD_VER=
PUBLISH_DIR=/opt/shell

#创建日志文件
create_log(){
	create_path $PUBLISH_DIR/log
	logFile="$PUBLISH_DIR/log/$1.log"
	touch $logFile
	oldLogFile="$PUBLISH_DIR/log/oldLog.txt"
    # 获取某个文件的md5
	md5=`md5sum /opt/apache-tomcat-8.5.45.tar.gz|awk -F ' ' '{print $1}'`
	mv $logFile $oldLogFile
	
	echo "VERSION: $1" > $logFile
	echo "MD5:$md5" >> $logFile
}

create_log filename
```

## 函数综合练习

```shell script
#!/bin/bash

function log_msg(){
        echo "${0#./} : $@"
}

log_msg "$PWD"
TARGET="XXX"
log_msg "target = $TARGET"

create_path(){
    # #检查目录是否存在，不存在则创建
        if [ ! -d "$1" ]
        then
                mkdir $1
        fi
    # 如果$2为-del,则删除$1下面所有文件
        if [ "$2" == "-del" ]
        then
                rm $1/* -rf
        fi
}

# 调用
create_path target -del
          
OLD_BUILD_VER=
PUBLISH_DIR=/opt/shell

#创建日志文件
create_log(){ 
        create_path $PUBLISH_DIR/log
        logFile="$PUBLISH_DIR/log/$1.log"
        touch $logFile
        oldLogFile="$PUBLISH_DIR/log/oldLog.txt"
    # 获取某个文件的md5
        md5=`md5sum /opt/apache-tomcat-8.5.45.tar.gz|awk -F ' ' '{print $1}'`
        mv $logFile $oldLogFile

        echo "VERSION: $1" > $logFile
        echo "MD5:$md5" >> $logFile
}

create_log filename
```

# if-else

```shell script
#判断2个变量是否相等
if [ "$OLD_BUILD_VER" != "$NEW_BUILD_VER" ]

# 判断一个变量是否等于某一个常量
if [ "$LIBRARY" == "true" ]
```

```shell script
# [ -f FILE ]	True if FILE exists and is a regular file.
if [ ! -f "build.xml" ]
then
    log_msg "创建build.xml失败"
    return 0
fi
```

```shell script
# [ -z STRING ]	True of the length if "STRING" is zero.
if [ -z $BUILD_PATH ]
then
	export BUILD_PATH=$(cd "$(dirname "$0")"; pwd) 
	# 获取$0的文件夹名字，之后切换到那个目录，然后那个路径的全名
	# 将这个路径的全名赋值给BUILD_PATH
fi
# "$(dirname "$0")"  -》 $0文件夹名字，例如./shell.sh为点， /aaa/bbb/ccc 为/aaa/bbb, /aaa/bbb/ccc/ 为/aaa/bbb
```

# while 

```shell script
while true; 
  do echo "hello world"
  sleep 1
done
```

# sed

sed 的 -i 选项可以直接修改文件内容.

```shell
sed 's/要被取代的字串/新的字串/g'

# 将 regular_express.txt 内每一行结尾若为 . 则换成 !
sed -i 's/\.$/\!/g' regular_express.txt

# 直接在 regular_express.txt 最后一行加入 # This is a test:
# 由於 $ 代表的是最后一行，而 a 的动作是新增，
# 因此该文件最后新增 # This is a test！
sed -i '$a # This is a test' regular_express.txt
```

# awk

```shell
cd ~/ && vim score.txt
```

>score.txt

```shell
name yw sx
lisi 3  60
ww   4  70
lucy 7 80
```

```shell
# 打印所有行
awk '{printf("%s\n",$0)}' score.txt

# 打印第一列
awk '{printf("%s\n",$1)}' score.txt

# 打印第2列
awk '{printf("%s\n",$2)}' score.txt
awk '{printf("%s\n",$3)}' score.txt

# 把l开头的行过滤了出来
awk '/l/{printf("%s\n",$0)}' score.txt
awk '/l/{printf("%s\n",$3)}' score.txt
```

# 文档

- [https://www.runoob.com/linux/linux-comm-sed.html](https://www.runoob.com/linux/linux-comm-sed.html)
[shell script guide](http://tldp.org/LDP/Bash-Beginners-Guide/html/sect_07_01.html)