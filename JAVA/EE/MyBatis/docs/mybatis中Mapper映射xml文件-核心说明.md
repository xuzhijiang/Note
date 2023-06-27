# namespace

    mybatis使用namespace.id的方式来唯一定位一个sql.
    
    namespace属性：必须是接口的全类名
    <mapper namespace="org.mybatis.core.mapper.UserMapper">
    
    id属性：必须是接口中方法的方法名, resultType属性：必须是方法的返回值的全类名.(如果配置了别名也可以写别名.)

# parameterType,resultType和resultMap

    parameterType指定参数类型,值可以为基本数据类型,比如int等,也可以为对象类型,比如User,java.util.List,hashmap.
    mybatis会自动帮我们将User对象的值取出来，来替换sql中的变量,
    即mybatis会调用user.getName()来替换#{name}，调用user.getAge()来替换#{age}
    
    <insert id="insert" parameterType="org.mybatis.core.model.User">
          INSERT INTO user(id,name,age) VALUES (#{id},#{name},#{age})
    </insert>

---
    resultType: 表示将数据库结果封装到这个User类中.是基于表字与User类属性是完全对应的
    例如对于数据库id、name、age字段，会自动调用User类的、setId、setName、setAge方法进行设置.
    如果表字段与实体属性不能一一对应的话，如数据库表字段为username，但是java类中定义的属性为name，则无法进行自动映射,
    需要我们配置
    
    <select id="selectAll" resultType="org.mybatis.core.model.User">
        SELECT id,name,age FROM user;
    </select>        

# 类型别名

    可以将parameterType、resultType属性值直接指定为User，前提是要在mybatis-config.xml全局配置
    文件中配置typeAlias别名.如下:
    
    <typeAlias  alias="User" type="org.mybatis.core.model.User"/>