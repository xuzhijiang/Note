package com.qianfeng.session;

import lombok.Setter;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName MySessionDAO
 * @Description: Session对象的 CURD到redis
 * @Author 臧红久
 * @Date 2019/10/16
 * @Version V1.0
 **/
@Setter
public class MySessionDAO  extends AbstractSessionDAO{

    private RedisTemplate<String,Object> template;

    /**
     * todo: 增加
     *       当发来首次请求，会创建新的Session，此方法中的参数：session即是。
     *       注意：此时的session有SimpleSessionFactory 创建，且还没有sessionID。
     * @param session
     * @return session的id ，会随着响应回到浏览器，存在cookie中
     */
    @Override
    protected Serializable doCreate(Session session) {
        System.out.println("doCreate session~~~");
        // 为session指定sessionID,默认策略是：UUID
        Serializable JSESSIONID04 = this.generateSessionId(session);//生成id
        System.out.println("JSESSIONID04: " + JSESSIONID04);
        this.assignSessionId(session,JSESSIONID04);//指定id
        // 将此session对象 存入redis
        //template.opsForValue().set("session04:"+JSESSIONID04,session);
        template.opsForValue().set("session04:"+JSESSIONID04,session,300, TimeUnit.SECONDS);
        return JSESSIONID04;
    }

    /**
     * todo: 查询
     *       通过SessionId，查询对应的session对象
     * @param sessionId 从客户端的cookie中传来
     * @return
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        System.out.println("doRead session:"+sessionId);
        // 设置session的key的有效时间
        template.expire("session04:"+sessionId,300,TimeUnit.SECONDS);
        return (SimpleSession)template.opsForValue().get("session04:"+sessionId);
    }

    /**
     * todo: 更新
     *       在用户更改了session中的作用域，需要将用户修改后的session，覆盖存储
     * @param session 用户修改作用域后的session对象(sessionID是不会改变的)
     * @throws UnknownSessionException
     */
    @Override
    public void update(Session session) throws UnknownSessionException {
        System.out.println("update session:"+session.getId());
        // 设置session的key的有效时间
        template.expire("session04:"+session.getId(),300,TimeUnit.SECONDS);
        template.opsForValue().set("session04:"+session.getId(),session);
    }

    /**
     * todo: 在session过期被发现后，需要删除session
     * @param session  需要删除的session对象
     */
    @Override
    public void delete(Session session) {
        System.out.println("session过期，"+session.getId());
        template.delete("session04:"+session.getId());
    }

    /**
     * todo: 查询出所有session，供检测使用
     * @return
     */
    @Override
    public Collection<Session> getActiveSessions() {
        Set keys = template.keys("session04:*");//通过前缀获取Session
        List<Session> sessions = template.opsForValue().multiGet(keys);
        return sessions;
    }
}