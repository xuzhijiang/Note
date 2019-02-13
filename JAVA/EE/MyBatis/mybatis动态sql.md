MyBatis 的强大特性之一便是它的动态SQL，其主要作用是进行SQL拼接。mybatis提供的动态sql元素包括：if、choose (when, otherwise)、trim (where, set)、foreach、bind.

### if

用于条件判断。例如动态的判断要使用哪些查询条件:

```xml
<select id="select" parameterType="hashmap" resultType="User">
        SELECT id,name,age FROM user
        WHERE 1=1
        <if test="name != null">
            AND name like #{name}
        </if>
        <if test="age !=null and age >0">
            AND age>#{age}
        </if>
</select>
```

注意：这里使用的parameterType类型是hashmap，对应的java类就是java.util.HashMap。对于这种情况，mybatis会调用map.get(key)的方式来替换#{}中的变量，而不再是getter方法。属性值test中的name也会被替换为map.get(name).

上面的<select>元素中的sql主要作用是根据"用户名"和"age"作为查询条件，从数据库筛选用户。当用户名(name)不为空时，使用用户名查询条件，当age不为空且>0时，那么用户年龄需要大于这个条件。

if可以帮助我们动态的判断是否使用某一个查询条件，拼接成不同的sql。

有一点需要注意的是：为了使得我们的语法总是正确的，我们在sql的查询WHERE条件中首先设置了 1=1，此时如果hashmap中没有设置任何查询条件，生成的SQL如下所示：

>SELECT id,name,age FROM user WHERE 1=1

考虑一下，如果没有1=1，则sql变为

>SELECT id,name,age FROM user WHERE

这明显是一个错误的语法，后面我们将介绍如何解决这个问题。

### choose, when, otherwise

有些时候，我们不想用到所有的条件语句，而只想从中择其一二。针对这种情况，MyBatis 提供了 choose 元素，它有点像 Java 中的 switch 语句。

在使用choose when otherwise的情况下，只要第一个条件满足了，剩下的条件都不会走了。

### trim, where, set

