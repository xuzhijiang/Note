package org.mybatis.core.plugins;

import java.util.Properties;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

/**
 * 插件签名：告诉MyBatis当前插件用来拦截哪个对象的哪个方法,
 *          本例中就是: 只会给StatementHandler创建代理对象.不会为其他的4大对象中的3大对象创建代理对象
 *
 *		type: 告诉拦截哪个类的对象,这里是拦截StatementHandler对象
 *		method: 告诉拦截这个对象的哪个方法
 *		args: 因为方法可能同名,但是有不同的参数个数,所以这里指定了method的参数.
 */
@Intercepts(
        {
                @Signature(type=StatementHandler.class,method="parameterize",args=java.sql.Statement.class)
        })
//  Interceptor的实现类就是我们所谓的插件
public class MyFirstPlugin implements Interceptor{

    /**
     * plugin：包装目标对象：为目标对象创建一个代理对象
     * 本例中就是: 只会给StatementHandler创建代理对象.不会为其他的4大对象中的3大对象创建代理对象
     * 其他的3大对象, 调用MyFirstPlugin中的plugin()方法的时候,再调用Plugin.wrap(target, this);
     * 会原样返回target,不会对target进行包装,
     * 因为这个MyFirstPlugin只拦截了StatementHandler的创建
     */
    @Override
    public Object plugin(Object target) {
        //我们可以借助Plugin的wrap方法来使用当前Interceptor包装我们目标对象
//        System.out.println("MyFirstPlugin...plugin:mybatis将要包装的对象"+target);
        // 第一个参数: 把要包装的目标对象target传进来
        // 第二个参数: 用哪个拦截器包装,就会返回包装后的对象.
        // 这个方法里面会判断,只会包装 StatementHandler.的拦截器,因为这个MyFirstPlugin只拦截StatementHandler
        Object wrap = Plugin.wrap(target, this);

        // 返回当前target对象的动态代理对象
        return wrap;
    }

    /**
     * intercept：拦截：
     * 		拦截目标对象的目标方法的"执行"
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        /**
         * 前置处理
         */
//        System.out.println("MyFirstPlugin...intercept:"+invocation.getMethod());

        /**
         * 动态的改变一下sql运行的参数：以前5号员工，实际从数据库查询6号员工
         */
        /*Object target = invocation.getTarget();
        System.out.println("当前拦截到的对象："+target);
        //拿到：StatementHandler==>ParameterHandler===>parameterObject
        MetaObject metaObject = SystemMetaObject.forObject(target); //拿到target的元数据
        // 拿到parameterHandler的parameterObject的值
        Object value = metaObject.getValue("parameterHandler.parameterObject");
        System.out.println("sql语句用的参数是："+value);
        //修改完sql语句要用的参数
        metaObject.setValue("parameterHandler.parameterObject", 6);*/

        /**
         * 目标方法执行 (只有执行了目标方法,4大对象真正方法才会被执行)
         * 我们可以在 目标方法 前后做一些额外的操作达到我们的目的.
         */
        Object proceed = invocation.proceed();

        //返回执行后的返回值
        return proceed;
    }

    /**
     * setProperties：
     * 		将插件注册时 的property属性设置进来
     */
    @Override
    public void setProperties(Properties properties) {
        // 可以拿到 mybatis-config.xml中 的注册这个插件的属性信息
        System.out.println("插件配置的信息："+properties);
    }

}
