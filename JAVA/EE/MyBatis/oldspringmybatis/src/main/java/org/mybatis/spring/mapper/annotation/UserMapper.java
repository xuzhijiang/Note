package org.mybatis.spring.mapper.annotation;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.domain.User;

/**
 * 可以通过注解的方式直接将sql写到UserMapper接口的方法上，此时你就不再需要mybatis/mapper/UserMapper.xml
 *
 * 此时我们可以修改mybatis-config.xml中<mapper>元素的resource属性为class属性，
 * 指定UserMapper接口的全路径，如下
 *
 * <mappers>
 *    <!--<mapper resource="mappers/UserMapper.xml"/>—>
 *    <!--使用class属性指定UserMapper接口全路径-->
 *    <mapper class="org.mybatis.spring.mapper.annotation.UserMapper"/>
 * </mappers>
 *
 * 之后，我们可以通过以下方式来使用UserMapper接口，更加直观，不容易出错：
 * sqlSession session = sqlSessionFactory.openSession();
 * UserMapper mapper = session.getMapper(UserMapper.class);
 * User user = mapper.selectById(1);
 * session.close();
 *
 * 但是在与spring进行整合时，是否有更加简单的使用方法呢？能够在一个业务Bean中注入UserMapper接口，
 * 不需要通过SqlSession的getMapper来创建。我们期望的使用方式如下：
 * public class UserService {
 *     @Autowired
 *     private UserMapper userMapper;
 *     public void insert(User user){
 *         System.out.println("create a new user!"+user);
 *         userMapper.insert(user);
 *     }
 * }
 *
 *  直接这样操作显然是会报错的，因为UserMapper是一个接口，且不是spring管理的bean，因此无法直接注入。
 *
 *  这个时候，本节的主角MapperFactoryBean登场了，通过如下配置，
 *  MapperFactoryBean会针对UserMapper接口创建一个代理，并将其变成spring的一个bean。
 *  MapperFactoryBean继承了SqlSessionDaoSupport类，这也是为什么我们先介绍SqlSessionDaoSupport，再介绍MapperFactoryBean的原因。
 *
 *  显然的，你可以想到，我们可以为MapperFactoryBean指定一个SqlSessionTemplate或者SqlSessionFactory，
 *  如果两个属性都设置了,那么 SqlSessionFactory 就会被忽略。
 *
 *  通过以下配置，我们就可以在一个业务bean中直接使用@Autowired注入UserMapper接口了(注入的就是下面的userMapper)：
 *  <bean id="userMapper" class="org.mybatis.spring.mapper.MapperFactoryBean">
 *       <property name="mapperInterface" value="org.mybatis.spring.mapper.UserMapper" />
 *       <property name="sqlSessionFactory" ref="sqlSessionFactory" />
 *  </bean>
 *
 * 你可能想知道MapperFactoryBean为什么具有这样的魔力，但是这不是本文的重点，
 * 本文讲解的是mybatis是如何spring进行整合的。笔者将其会在其他章节分析MapperFactoryBean的源码。
 *
 * 上述方式，已经是mybatis与spring进行时理想的方式了。但是如果你的业务很复杂，
 * 有许多的XxxMapper接口要配置，针对每个接口都配置一个MapperFactoryBean，
 * 会使得我们的配置文件很臃肿。关于这一点，mybatis团队提供了MapperScannerConfigurer来帮助你解决这个问题。
 *
 * 整合六：MapperScannerConfigurer
 * 通常我们在开发时，会将DAO类放于同一个包下，例如现在我们@Autowired包下，有两个接口：UserMapper、UserAccountMapper。
 *
 * MapperScannerConfigurer可以指定扫描某个包，将这个包下的所有接口，自动的为每个映射器接口都注册一个MapperFactoryBean。配置如下：
 *
 * <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
 *   <property name="basePackage" value="org.mybatis.spring.user.mappers" />
 * </bean>
 *
 * 其中：basePackage 属性是让你为映射器接口的包路径。如果的映射器接口位于不同的包下，
 * 可以使用分号”;”或者逗号”,”进行分割。如：
 *
 * <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
 *   <property name="basePackage" value="org.mybatis.spring.user.mappers;org.mybatis.spring.product.mappers" />
 * </bean>
 *
 * 如果你指定的包还包含子包，子包中的映射器接口递归地被搜索到。因此对于上述配置，
 * 我们可以通过公共的包名"org.mybatis.spring”进行简化。如：
 * <property name="basePackage" value="org.mybatis.spring" />
 *
 * 你可能想到了，如果指定的公共的包名下面还包含了一些其他的接口，这些接口是你作为其他用途使用到的，
 * 并不能作为mybatis的映射器接口来使用。此时，你可以通过markerInterface属性或者annotationClass属性来进行过滤。
 *
 * 对于markerInterface，首先，你需要定义一个标记接口，接口名随意，这个接口中不需要定义任何方法，如：
 * public interface MybatisMapperInterface{}
 *
 * 接着，你需要将你的映射器接口继承MybatisMapperInterface，如：
 * public interface UserMapper implements MybatisMapperInterface{
 *    //...映射器方法
 * }
 *
 * 此时你可以指定MapperScannerConfigurer中指定，只有继承了MybatisMapperInterface接口的子接口，
 * 才为其自动注册MapperFactoryBean，如：
 *
 * <property name="markerInterface" value=“org.mybatis.spring.MybatisMapperInterface"/>
 *
 * 对于annotationClass属性，作用是类似的，根据注解进行过滤。
 * 一般我们是映射器接口上添加mybatis提供的@Mapper注解进行过滤，你也可以自定义一个注解。配置如下：
 * <property name="annotationClass" value="org.apache.ibatis.annotations.Mapper"/>
 *
 * 如果同时指定了markerInterface和annotationClass属性，那么只有同时满足这两个条件的接口才会被注册为MapperFactoryBean。
 *
 *     细心的读者可能意识到了，到目前，我们还没有为MapperScannerConfigurer指定一个SqlSessionFactory，
 * 或者SqlSessionTemplate。前面配置MapperFactoryBean的时候，我们已经看到，我们至少需要为其提供一项。
 * 之所以不指定，是因为MapperScannerConfigurer将会spring上下文中自动进行寻找类型为SqlSessionFactory，
 * 或者SqlSessionTemplate的bean，然后利用其来创建MapperFactoryBean实例。
 *
 * 当然你也可以手工的进行指定任意一个，如：
 *
 * <property name="sqlSessionFactory" ref="sqlSessionFactory"/>
 * <!--<property name="sqlSessionTemplate" ref="sqlSessionTemplate"/>-->
 *
 *  然而，sqlSessionFactory和sqlSessionTemplate属性已经不建议使用了。原因在于，
 *  这两个属性不支持你使用spring提供的PropertyPlaceholderConfigurer的属性替换。
 *  例如你配置了SqlSessionFactoryBean来创建SqlSessionFactory实例，前面已经看到必须为其指定一个dataSource属性。
 *  很多用户习惯将数据源的配置放到一个独立的配置文件，如jdbc.properties文件中，之后在spring配置中，通过占位符来替换，如：
 *
 * <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
 *         <property name="username" value="${jdbc.username}"/>
 *         <property name="password" value="${jdbc.password}"/>
 *         <property name="url" value="${jdbc.url}"/>
 *         <property name="driverClassName" value="${jdbc.driver}"/>
 *         <!--其他配置-->
 * </bean>
 *
 *     对于这样的配置，spring在初始化时会报错，因为MapperScannerConfigurer会在PropertyPlaceholderConfigurer初始化之前，
 *     就加载dataSource的配置，而此时PropertyPlaceholderConfigurer还未准备好替换的占位符的内容，所以抛出异常。
 *
 *     当然，这个问题并不是无解，我们可以使用sqlSessionFactoryBeanName、sqlSessionTemplateBeanName属性来替代sqlSessionFactory和sqlSessionTemplate属性。如下：
 *
 * <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
 * <!--<property name="sqlSessionTemplateBeanName" value="sqlSessionTemplate"/>-->
 *
 * 此时，你依然可以放心大胆的在你的数据源配置中，使用占位符了。
 *
 *     事实上，笔者总是建议你，在MapperScannerConfigurer的配置中，显示的指定sqlSessionFactoryBeanName或sqlSessionTemplateBeanName。
 *     如果你不指定，MapperScannerConfigurer就会在spring上下文中自动的寻找类型为SqlSessionFactory或者SqlSessionTemplate的bean。
 *     如果你的数据源配置中使用了占位符，还是会报错。
 *
 * 最后，你可能想知道，为什么MapperScannerConfigurer指定一个basePackage属性，就可以为包下的每个接口都注册一个MapperFactoryBean？
 * 其内部是如何自动在spring上下文中寻找类型为SqlSessionFactory或者SqlSessionTemplate的bean实例的？
 * 以及为什么又做了如此多限制，只有指定sqlSessionFactoryBeanName或sqlSessionTemplateBeanName才能在数据源的配置中使用占位符？
 *
 *     关于这些问题，笔者将在MapperScannerConfigurer的源码分析中进行解答。
 */
public interface UserMapper {

    @Insert("insert into user(name, age) values(#{id},#{name},#{age})")
    public int insert(User user);

    @Select("select * from user where id = #{id}")
    public User selectById(int id);
}
