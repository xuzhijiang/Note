## #与$的区别

>在mybatis中，#与$的都可以起到变量替换的作用，但是二者的使用场景却是截然不同的。 

"#{}"的作用主要是替换预编译语句(PrepareStatement)中的占位符?，例如xml映射文件中，有以下insert语句

```xml
<insert id="insert" parameterType="User">
    INSERT INTO user (name) VALUES (#{name});
</insert>
```

```
16:00:00.691 [main] DEBUG com.tianshouzhi.dragon.common.mappers.UserMapper.insert - ==>  Preparing: INSERT INTO user (name) VALUES (?);
16:00:00.750 [main] DEBUG com.tianshouzhi.dragon.common.mappers.UserMapper.insert - ==> Parameters: xzj(String)
```

1. 在第1行日志中，可以看到#{name}被替换成了占位符”?”，熟悉jdbc编程的同学，这里使用的肯定是预编译的PrepareSatement。
2. 而第2行日志显示了最终要替换这个占位符的参数值是字符串类型的"xzj"。

#### $符号的作用是直接进行字符串替换

```xml
<insert id="insert" parameterType="User">
    INSERT INTO user (name) VALUES ('${name}');
</insert>
```

```
16:00:58.642 [main] DEBUG com.tianshouzhi.dragon.common.mappers.UserMapper.insert - ==>  Preparing: INSERT INTO user (name) VALUES ('xzj');
16:00:58.663 [main] DEBUG com.tianshouzhi.dragon.common.mappers.UserMapper.insert - ==> Parameters:
```

>可以看到在sql日志中，#{name}被直接替换成了"xzj"。值得我们注意的是：使用${}存在SQL注入的风险。

例如上面插入insert语句中的name变量，假设是用户注册时填写的，有一个恶意用户，故意将name填写为：xzj'),('zjx

>此时对于使用#{name}情况：

INSERT INTO user (name) VALUES (#{name});
mybatis首先将其#{name}替换成占位符"?"，然后用xzj'),('zjx  来替换"?"，因此用户名就是xzj'),('zjx。

>而对于使用${name}的情况：

INSERT INTO user (name) VALUES ('${name}');
由于是直接替换字符串，此时sql变成如下所示：

>INSERT INTO user (name) VALUES ('xzj'),('zjx');
这是mysql的批量插入语法，相当于在数据库中插入了2条记录。

>因此在实际开发中，对于用户填写的变量值，我们一定要尽量使用#{}，不要轻易使用${}。

#### 典型误解

部分人认为#{}可以防止Sql注入，因此其使用的是jdbc编程中的PrepareSatement。而${}不可以防止SQL注入，因此使用的Satement。

1. 事实上，默认情况下，在mybatis中,#{}、和${}，使用的都是PrepareSatement。请读者回看前面两种情况打印出sql时，前面都有一个Preparing，这就是明显的提示。
2. 事实上<insert>、<update>、<delete>、<select>都有一个statementType属性，取值范围为： STATEMENT，PREPARED 或 CALLABLE 的一个，这会让 MyBatis 分别使用 Statement，PreparedStatement 或 CallableStatement。
3. statementType属性的默认值为PREPARED，因此默认的SQL语句都是通过PreparedStatement来执行的。