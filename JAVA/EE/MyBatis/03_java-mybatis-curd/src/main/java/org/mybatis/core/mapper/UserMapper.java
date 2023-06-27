package org.mybatis.core.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.mybatis.core.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserMapper {
    public long addUser(User user);

    // mybatis允许增删改返回Integer,Long,Boolean,void 这些类型的包装类/基本类型都行,
    // 因为增删改返回的是影响多少行,如果你返回值写Boolean,那么只要影响的结果超过0行,就会返回true.
    // 0行就返回false.包括影响多少行也可以封装成Integer.我们只需要在返回方法上定义即可.而无需在XXXMapper.xml
    // 中写返回值类型.而且你在XXXMapper.xml中,insert/update/delete这三个增删改标签也不像select标签一样
    // 支持resultType这个属性(也就是增删改标签不支持写返回值类型).所以要想增删改有返回值,直接在方法上
    // 写对应的类型即可.
    // 结论: insert/delete/update不支持resultType这个属性,只有select标签支持这个属性
    public boolean updateUser(User user);
    public long deleteById(Integer id);

    public User getUserByIdAndLastName01(Integer id, String lastName);
    public User getUserByIdAndLastName02(Integer id, String lastName);
    public User getUserByIdAndLastName03(@Param("id")Integer id, @Param("lastName")String lastName);
    public User getUserByIdAndLastName04(User user); // 通过POJO的方式
    public User getUserByIdAndLastName05(Map<String, Object> map); // 通过POJO的方式

    public List<User> getUsersByLastNameLike(String lastName);

    //返回一条记录的map；key就是列名，值就是对应的值
    public Map<String, Object> getUserByIdReturnMap(Integer id);

    //多条记录封装一个map：Map<Integer,User>:键是这条记录的主键，值是记录封装后的javaBean
    //@MapKey:告诉mybatis封装这个map的时候使用哪个属性作为map的key
    @MapKey("lastName")
    public Map<String, User> getUserByLastNameLikeReturnMap(String lastName);

}