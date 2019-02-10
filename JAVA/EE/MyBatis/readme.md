## 什么orm框架

所谓orm指的是对象关系映射(Object Relational Mapping)。其中Object指的就是java中的对象，Relational指的是关系型数据，而Mapping指的就是对象与关系型数据库的映射.

所谓映射,就是针对关系型数据库中的表,我们要创建对象与表的映射.

对象中的属性名与数据库表user的字段名是一一对应的。不过即使名字不一样也没有关系，因为ORM框架通常都会支持用户定义`映射文件(Mapping File)`来指定`对象中的属性`与`数据库表字段`的对应关系.

有了这个映射关系，ORM框架既可以发挥它的威力，例如：

1. 保存记录：直接保存java对象即可，ORM框架会自动将java对象每个属性的值保存到对应的数据库表对应的字段上。
2. 查询记录：ORM框架会将查询到的结果集(ResultSet)封装成对应的Java对象。

### MyBatis与其他orm框架的对比

ORM框架有很多，除了Mybatis，比较著名的还有Hibernate、Toplink等。通常我们把Mybatis称之为半自动的ORM框架，把Hibernate、TopLink等称之为全自动的ORM框架。

1. 所谓全自动：像Hibernate这种ORM框架，开发人员只需要定义好映射关系，`连sql都不用写了，ORM框架会动态的生成sql去操作数据库`。因此我们在程序中，对于数据库的CRUD，操作的都是Java对象。
2. 所谓半自动：指的是Mybatis中，虽然CRUD操作也是操作的Java对象，但是对应的sql还是需要开发人员自己来写，没有实现全自动的根据映射关系自动生成sql的功能。

> 从代码简洁性上来说，肯定是全自动的ORM框架更好。

> 但是从效率来说：半自动的ORM框架比全自动的ORM框架要更好。举个例子，Hibernate查询一条记录时，会将表中的所有字段查询出来，封装到Java对象中。这是因为hibernate中sql是动态生成的，不知道开发人员想要查询哪几个字段，因此只要查出所有。而Mybatis这种半自动ORM框架需要自己写SQL，因此我们可以指定只查询哪几个数据库字段。

> 从灵活性上来说：肯定是Mybatis更灵活，还是因为Sql由开发人员自己控制。Hibernate也可以自己写SQL来指定需要查询的字段，但这样就破坏了Hibernate开发的简洁性。

> 从上手难易程度来说：Hibernate比Mybatis更加复杂。因为全自动的orm框架比半自动的ORM框架抽象程度更高，理解起来也更为复杂。

目前，国内的大型互联网公司使用的基本上都是mybatis。从笔者的工作经历来说也是这样，经历的几家公司，所在的项目组，使用的都是mybatis。因此对于初学者来说，学习mybatis可能比学习其他的orm框架在国内更加实用。

### MyBatis

mybatis是一个开源的orm框架，在3.0版本之前叫做ibatis，3.0之后捐赠给了是Apache，改名为mybatis。

MyBatis 支持普通 SQL 查询,存储过程和高级映射的优秀持久层框架。MyBatis 消除 了几乎所有的 JDBC 代码和参数的手工设置以及结果集的检索。MyBatis 使用简单的 XML 或注解用于配置和原始映射,将接口和 Java 的 POJOs(Plain Old Java Objects,普通的 Java 对象)映射成数据库中的记录。

在使用mybatis时，我们需要先创建一个SqlSessionFactory对象(在ibatis为SqlMapClient)，然后通过SqlSessionFactory来创建SqlSession，通过SqlSession提供的api去执行sql。

####  SqlSessionFactory

是创建SqlSession实例的工厂类。SqlSessionFactory，某种程度上可以认为其是在数据源(Datasouce)的基础上做的一层封装，因此在整个程序中，最好只保存一个SqlSessionFactory实例。SqlSessionFactory与SqlSession之间的关系，就好像是Datasouce与Connection之间的关系一样。在使用一个数据源的时候，我们可以通过Datasouce的getConnection方法来获取Connection对象，而在mybatis中，我们通过SqlSessionFactory的openSession方法来获取SqlSession对象来操作数据库。

mybatis提供了一个SqlSessionFactoryBuilder对象，用于读取mybatis配置文件，创建SqlSessionFactory实例。以下代码演示了如何通过配置文件创建一个SqlSessionFactory实例.

```java
String resource = "mybatis-config.xml";
InputStream inputStream = Resources.getResourceAsStream(resource);
SqlSessionFactory sqlSessionFactory =new SqlSessionFactoryBuilder().build(inputStream);
```

> 其中Resources是mybatis提供的一个工具类，用于读取classpath下的资源配置文件。

#### SqlSession

SqlSession可以认为是在数据库连接(Connection)的基础上做的一层封装。其提供我们操作数据库相关的API，一个SqlSession实例从创建到销毁整个过程中执行的所有sql，都是通过同一个Connection对象进行。需要注意的是，SqlSession 的实例不是线程安全的，因此我们不应该使用多个线程来操作同一个Sqlsession对象。最佳实践是：每次需要操作数据库时，都创建一个新的SqlSession实例，使用完成之后，将其关闭。相关片段如下所示:

```
SqlSession session = sqlSessionFactory.openSession();
try {
  // do work
} finally {
  session.close();
}
```

SqlSession中，为我们在xml映射文件中配置的<insert>、<update>、<delete>、<select>提供了相应的操作方法,具体看源码。

1. 针对<insert>、<update>、<delete>、<select>元素，SqlSession中都提供了对应方法的多种重载形式
2. 对应的方法，无一例外的都要接受一个String类型的statement参数。

前面提到过，mybatis是通过namespace.id的方式来唯一定位要执行哪个sql，这里的statement就是namespace.id的值。例如要执行UserMapper.xml文件中以下<select>元素中的sql

```xml
<select id="selectById" parameterType="int"
     resultType="org.mybatis.core.model.User">
     SELECT id,name,age FROM user where id= #{id}
</select>
```

> 那么statement的值为"org.mybatis.core.model.User.selectById"

3. 有些方法需要接受一个Object类型的parameter参数，而另一些不需要。

回顾在xml映射文件中，我们配置的<insert>、<update>、<delete>、<select>元素，每个都可以配置一个parameterType属性，就是与此处传入的parameter参数相呼应。

> 例如对于我们正在UserMapper.xml中配置的<insert>元素

```xml
<insert id="insert" parameterType="org.mybatis.core.model.User">
      INSERT INTO user(id,name,age) VALUES (#{id},#{name},#{age})
</insert>
```

> 我们配置的parameterType属性值为"org.mybatis.core.model.User"，表示执行这个元素内部的sql时，我们需要传递一个User对象。而以下<delete>元素中，我们配置的parameterType属性值为"int"，表示执行这个元素内部的sql时，我们需要传递一个Integer值。

```xml
<delete id="deleteById" parameterType="int">
       DELETE FROM user WHERE id=#{id}
</delete>
```

> 因为传递的参数类型是各种各样的，因此parameter的类型是Object。

对于另外一些sql执行时不需要参数的，此时我们可以调用不要传递parameter参数的方法重载形式。如：

```xml
<delete id="deleteAll">
   DELETE  FROM user
</delete>
```

4. insert、update、delete相关方法，调用后，返回值类型都是int
,这些操作属于更新数据库的操作，int表示受影响的记录行数.

5. 对于select相关方法，比insert、update、delete提供的方法都要多。

> <select>元素对应的相关方法大致可以归为这几类：selectOne、selectList、selectMap、selectCursor、没有返回值的select方法。

> 首先声明，对于映射文件中的一个<select>元素，从API层面，上述方法我们都可以调用，但是在实际使用中，却要注意。

例如，对于以下<select>元素：

```xml
<select id="selectAll" resultType="com.tianshouzhi.mybatis.quickstart.domain.User">
        SELECT id,name,age FROM user;
</select>
```

这个<select>元素中执行的sql可能会查询到多条记录，每条记录都会被封装成一个User对象。

> 当我们调用selectList方法执行这条sql时,表示将User对象放到一个List返回。

> 当我们调用selectMap方法执行这条sql时，表示将User对象放到一个Map返回。特别的，selectMap方法需要额外指定一个mapKey参数，表示用哪一个字段作为Map的key，一般我们会把主键字段当做Map的key，而Map的value显然就是对应的User对象。

> 而当我们调用selectOne方法来执行SQL时，如果数据库中没有记录，或者只有1条记录，那么没问题。但是如果数据库记录数>1，那么mybatis就会抛出一个TooManyResultsException异常，表示返回的结果记录数与预期不符。从selectOne方法的名字就可以看出来，我们希望的是执行了这个sql最终返回一条记录。

> 所以，当我们调用selectOne方法执行一条sql时，一定要保证这个sql最多执行返回一条记录。例如调用selectOne执行以下<select>元素中的sql，就是没问题的：

```xml
<select id="selectById" parameterType="int"
             resultType="com.tianshouzhi.mybatis.quickstart.domain.User">
             SELECT id,name,age FROM user where id= #{id}
</select>
```

> 注意:testSelectList和testSelectMap调用的都是UserMapper.xml文件中，id值为"selectAll"这个<select>元素中的sql。

> selectCursor方法主要是为了处理超大结果集，这是mybatis 3.4.0版本中的新功能。例如数据库中有1000W条记录，selectMap和selectList方法会直接将所有的记录查询出来，封装到List或Map中，结果很显然是内存被撑爆掉。selectCursor是通过游标的方式来解决这个问题。

* 对于没有返回值的select方法，相比其他方法多接受一个ResultHandler参数，其作用很明显，对数据库返回的结果进行自定义的处理。
* 最后，除了selectOne方法，其他形式select方法中都可以接受一个RowBounds参数，这是mybatis对分页功能的支持。不过这里用的是逻辑分页，而不是物理分页，所以一般我们不会使用这种类型的重载形式。之后的章节中，我们会介绍物理分页和逻辑分页的区别。

#### 基于注解的映射配置

在前面的讨论中，mybatis的全局配置文件(mybatis-config.xml)和映射器配置(UserMapper.xml)都是基于xml文件格式配置的。事实上mybatis也支持将映射配置直接配置在Mapper接口中。此时修改：

1. 修改mybatis-config.xml的mappers元素配置，用注解配合代替xml映射配置

```xml
<mappers>
        <!-- <mapper resource="mappers/UserMapper.xml"/> -->
        <!--通过class属性指定UserMapper接口的全路径-->
        <mapper class="org.mybatis.core.mapper.UserMapper"/>
</mappers>
```

2. 在UserMapper接口的方法上，添加相关注解

```java
public interface UserMapper {
   @Insert("INSERT INTO user(id,name,age) VALUES (#{id},#{name},#{age})")
   public int insert(User user);
   @Select(" select id,name,age from user where id= #{id}")
   public User selectById(int id);
   @Select(" select id,name,age from user")
   public List<User> selectAll();
   @Update("UPDATE user SET name=#{name},age=#{age} WHERE id=#{id}")
   public int updateById(User user);
   @Delete("DELETE FROM user WHERE id=#{id}")
   public int deleteById(int id);
   @Delete("DELETE FROM user")
   public int deleteAll();
}
```

> 其中@Insert、@Select、@Update、@Delete注解，分别对应xml配置中的<insert>、<update>、<delete>、<select>元素。而注解里的sql直接就是把UserMapper.xml文件中对应的元素中的sql搬过来。

> 当然，mybatis中提供的注解远远不止这几个，我们接触的只是冰山一角。mybatis提供的注解org.apache.ibatis.annotations包下面，在后面的章节中，我会对这些注解逐一进行讲述。

#### 本节总共介绍了2种mybatis的使用方式

1. 通过xml映射文件，配置<insert>、<update>、<delete>、<select>元素

	* 又细分为直接操作SqlSession 

	* Mapper接口：通过SqlSession获得Mapper接口的动态代理，接口的全路径要与xml映射文件namespace属性值相匹配，接口中的方法名与映射文件中配置的<insert>、<update>、<delete>、<select>元素id属性值相匹配。

2. 通过注解映射，在Mapper接口的相应方法上添加@Insert、@Update、@Delete、@Select注解。

> 总的来说，直接操作SqlSession是最基础的使用方式，也最能帮助我们了解mybatis内部的核心工作流程，其他都是在这个基础上的封装。从笔者的工作经验来看，目前还是xml映射文件+Mapper接口的方式使用的最多。对于基于注解的方式，笔者并不建议，因为经常会出现在一些xml映射文件中支持的功能，使用注解的方式就不支持了。

### mybatis与mybatis-spring的关系

我们可以单独使用mybatis，只有在和spring整合的时候，才需要使用mybatis-spring.
mybatis在操作数据库时，需要依赖数据源(DataSource)，mybatis自带了几个数据源实现，我们也可以自己指定其他实现，如druid、c3p0，dbcp等。

Spring ----> mybatis-spring ----> mybatis ----> datasource ----> 数据库
			
mybatis是orm框架，避免直接使用JDBC API.
datasource是数据库连接池，用于获取数据库连接Connection.
