package com.atguigu.shiro.realms;

import java.io.Serializable;
import java.util.List;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

// 会话dao:
// SessionDao: 可以把session写到数据库中,然后对session来做增删改查操作.
// 开发一般直接继承EnterpriseCacheSessionDAO
public class MySessionDao extends EnterpriseCacheSessionDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate = null;

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);
		String sql = "insert into sessions(id, session) values(?,?)";
		jdbcTemplate.update(sql, sessionId,
				SerializableUtils.serialize(session));
		return session.getId();
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		String sql = "select session from sessions where id=?";
		List<String> sessionStrList = jdbcTemplate.queryForList(sql,
				String.class, sessionId);
		if (sessionStrList.size() == 0)
			return null;
		return SerializableUtils.deserialize(sessionStrList.get(0));
	}
	
	@Override
	protected void doUpdate(Session session) {
		if (session instanceof ValidatingSession
				&& !((ValidatingSession) session).isValid()) {
			return; 
		}
		String sql = "update sessions set session=? where id=?";
		jdbcTemplate.update(sql, SerializableUtils.serialize(session),
				session.getId());
	}

	@Override
	protected void doDelete(Session session) {
		String sql = "delete from sessions where id=?";
		jdbcTemplate.update(sql, session.getId());
	}
}
