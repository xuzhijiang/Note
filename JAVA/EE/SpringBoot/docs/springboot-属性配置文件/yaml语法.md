# yaml语法

    基本语法： k:(空格)v ,表示一对键值对，注意的是空格不能省略，
    以缩进(空格)来控制层级关系。注意属性和值也是大小写敏感的

1. 值为普通的值（数字，字符串，布尔）：k: v ：就直接写值，注意，字符串默认不用添加单引号和双引号
2. "":双引号：会转义字符串里面的特殊字符；"zhangsan \n lisi"：输出；zhangsan 换行  lisi
3. '':单引号：不会转义字符串里面的特殊字符，‘zhangsan \n lisi’：输出；zhangsan \n  lisi
4. 值为对象，Map使用k: 下一行写对象的属性和值
5. 值为集合，使用k: 下一行使用-(空格)值来书写

~~~yaml
person:
  name: "张三\n李四"
  age: 14
  map1: {k1: V1,k2: V2}
  map2:
    k1: v1
    k2: v2
  list1:
    - cat
    - dog
    - pig
  list2: [cat,dog,pig]
  cat:
    name: '小猫\n小狗'
    age: 4
~~~