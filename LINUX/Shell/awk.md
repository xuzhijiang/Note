# awk

```shell
cd ~/ && vim score.txt
# 输入
name yw sx
lisi 3  60
ww   4  70
lucy 7 80
```

```shell
# 打印所有列
awk '{printf("%s\n",$0)}' score.txt

# 打印第一列
awk '{printf("%s\n",$1)}' score.txt
# 打印第2列
awk '{printf("%s\n",$2)}' score.txt
# 打印第3列
awk '{printf("%s\n",$3)}' score.txt

# 把l开头的行过滤了出来(把所有的列都过滤出来)
awk '/l/{printf("%s\n",$0)}' score.txt

# 把l开头的行过滤了出来(只把第3列过滤出来)
awk '/l/{printf("%s\n",$3)}' score.txt
```

```shell
# 打印包含Queries的行对应的第4列的值
awk '/Queries/{printf("%d ", $4)}'

# 打印包含Queries，Threads_connected，Threads_running的行的value
awk '/Queries/{printf("%d ", $4)}/Threads_connected/{printf("%d ", $4)}/Threads_running/{printf("%d\n", $4)}'

# 简化上一步脚本
awk '/Queries/{q=$4}/Threads_connected/{c=$4}/Threads_running/{r=$4}END{printf("%d %d %d\n",q,c,r)}'
```