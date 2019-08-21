# shell

[shell script guide](http://tldp.org/LDP/Bash-Beginners-Guide/html/sect_07_01.html)

shell中针对一个script.sh脚本，使用`. script.sh`, `bash script.sh`, `source script.sh`效果都是一致的

`while true; do echo hello world; sleep 1; done`

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

# 文档

- [https://www.runoob.com/linux/linux-comm-sed.html](https://www.runoob.com/linux/linux-comm-sed.html)