package org.mybatis.core.SourceAnalyze;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.io.Closeable;
import java.util.List;
import java.util.Map;

import java.sql.Connection;

// SqlSession中，为我们在xml映射文件中配置的<insert>、<update>、<delete>、<select>提供了相应的操作方法

// 针对<insert>、<update>、<delete>、<select>元素，SqlSession中都提供了对应方法的多种重载形式
// 对应的方法，无一例外的都要接受一个String类型的statement参数
public interface SqlSession extends Closeable {

    //<select>元素对应的相关方法

    // 前面提到过，mybatis是通过namespace.id的方式来唯一定位要执行哪个sql，
    // 这里的statement就是namespace.id的值。例如要执行UserMapper.xml文件中以下<select>元素中的sql:

    //<select id="selectById" parameterType="int"
    //     resultType="org.mybatis.core.model.User">
    //     SELECT id,name,age FROM user where id= #{id}
    //</select>

    // statement的值为"org.mybatis.core.model.User.selectById"
    <T> T selectOne(String statement);



    // 3. 有些方法需要接受一个Object类型的parameter参数，而另一些不需要。
    //
    //回顾在xml映射文件中，我们配置的<insert>、<update>、<delete>、<select>元素，
    // 每个都可以配置一个parameterType属性，就是与此处传入的parameter参数相呼应。
    //
    //> 例如对于我们正在UserMapper.xml中配置的<insert>元素
    //
    //<insert id="insert" parameterType="org.mybatis.core.model.User">
    //      INSERT INTO user(id,name,age) VALUES (#{id},#{name},#{age})
    //</insert>
    //
    //> 我们配置的parameterType属性值为"org.mybatis.core.model.User"，表示执行这个元素内部的sql时，
    // 我们需要传递一个User对象。
    // 而以下<delete>元素中，我们配置的parameterType属性值为"int"，
    // 表示执行这个元素内部的sql时，我们需要传递一个Integer值。
    //
    //<delete id="deleteById" parameterType="int">
    //       DELETE FROM user WHERE id=#{id}
    //</delete>
    //
    //> 因为传递的参数类型是各种各样的，因此parameter的类型是Object。
    //
    //对于另外一些sql执行时不需要参数的，此时我们可以调用不要传递parameter参数的方法重载形式。如：
    //
    //<delete id="deleteAll">
    //   DELETE  FROM user
    //</delete>

    <T> T selectOne(String statement, Object parameter);

    // 对于select相关方法，比insert、update、delete提供的方法都要多。
    //
    //> <select>元素对应的相关方法大致可以归为这几类：selectOne、selectList、selectMap、selectCursor、没有返回值的select方法。
    //
    //> 首先声明，对于映射文件中的一个<select>元素，从API层面，上述方法我们都可以调用，但是在实际使用中，却要注意。
    //例如，对于以下<select>元素：
    //
    //<select id="selectAll" resultType="org.mybatis.core.model.User">
    //        SELECT id,name,age FROM user;
    //</select>
    //
    //这个<select>元素中执行的sql可能会查询到多条记录，每条记录都会被封装成一个User对象。
    //
    //> 当我们调用selectList方法执行这条sql时,表示将User对象放到一个List返回。
    //
    //> 当我们调用selectMap方法执行这条sql时，表示将User对象放到一个Map返回。
    // 特别的，selectMap方法需要额外指定一个mapKey参数，表示用哪一个字段作为Map的key，
    // 一般我们会把主键字段当做Map的key，而Map的value显然就是对应的User对象。
    //
    //> 而当我们调用selectOne方法来执行SQL时，如果数据库中没有记录，或者只有1条记录，那么没问题。
    // 但是如果数据库记录数>1，那么mybatis就会抛出一个TooManyResultsException异常，表示返回的结果记录数与预期不符。
    // 从selectOne方法的名字就可以看出来，我们希望的是执行了这个sql最终返回一条记录。
    //
    //> 所以，当我们调用selectOne方法执行一条sql时，一定要保证这个sql最多执行返回一条记录。
    // 例如调用selectOne执行以下<select>元素中的sql，就是没问题的：
    //
    //<select id="selectById" parameterType="int"
    //             resultType="org.mybatis.core.model.User">
    //             SELECT id,name,age FROM user where id= #{id}
    //</select>
    //
    //> selectCursor方法主要是为了处理超大结果集，这是mybatis 3.4.0版本中的新功能。
    // 例如数据库中有1000W条记录，selectMap和selectList方法会直接将所有的记录查询出来，封装到List或Map中，
    // 结果很显然是内存被撑爆掉。selectCursor是通过游标的方式来解决这个问题。
    //
    //* 对于没有返回值的select方法，相比其他方法多接受一个ResultHandler参数，其作用很明显，对数据库返回的结果进行自定义的处理。
    //* 最后，除了selectOne方法，其他形式select方法中都可以接受一个RowBounds参数，这是mybatis对分页功能的支持。
    // 不过这里用的是逻辑分页，而不是物理分页，所以一般我们不会使用这种类型的重载形式。
    // 之后的章节中，我们会介绍物理分页和逻辑分页的区别。
    <E> List<E> selectList(String statement);
    <E> List<E> selectList(String statement, Object parameter);
    <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds);

    <K, V> Map<K, V> selectMap(String statement, String mapKey);
    <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey);
    <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds);

    <T> Cursor<T> selectCursor(String statement);
    <T> Cursor<T> selectCursor(String statement, Object parameter);
    <T> Cursor<T> selectCursor(String statement, Object parameter, RowBounds rowBounds);

    void select(String statement, Object parameter, ResultHandler handler);
    void select(String statement, ResultHandler handler);
    void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler);

    // 4. insert、update、delete相关方法，调用后，返回值类型都是int
    //,这些操作属于更新数据库的操作，int表示受影响的记录行数.

    //<insert>元素对应的相关方法
    int insert(String statement);
    int insert(String statement, Object parameter);

    //<update>元素对应的相关方法
    int update(String statement);
    int update(String statement, Object parameter);

    //<delete>元素对应的相关方法
    int delete(String statement);
    int delete(String statement, Object parameter);

    //事务操作相关方法
    void commit();
    void commit(boolean force);

    void rollback();
    void rollback(boolean force);

    //其他方法
    List<BatchResult> flushStatements();

    @Override
    void close();

    void clearCache();

    Configuration getConfiguration();

    <T> T getMapper(Class<T> type);

    Connection getConnection();

}
