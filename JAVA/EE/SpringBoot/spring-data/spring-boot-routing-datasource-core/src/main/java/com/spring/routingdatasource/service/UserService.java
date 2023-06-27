package com.spring.routingdatasource.service;

import com.spring.routingdatasource.domain.User;
import com.spring.routingdatasource.request.UserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;

@RestController
public class UserService {

	/**
	 * 会使用我们自己配置的DataSource
	 */
	@Autowired
	JdbcTemplate jdbcTemplate;

	@PostConstruct
	public void init() {
		jdbcTemplate.execute("DROP TABLE IF EXISTS user");
		jdbcTemplate.execute("CREATE TABLE user (" //
				+ "id VARCHAR(50) NOT NULL," //
				+ "email VARCHAR(50) NOT NULL," //
				+ "name VARCHAR(50) NOT NULL," //
				+ "gender BOOLEAN NOT NULL," //
				+ "createdAt BIGINT NOT NULL," //
				+ "updatedAt BIGINT NOT NULL," //
				+ "PRIMARY KEY (id))");
		for (int i = 0; i < 25; i++) {
			UserReq req = new UserReq();
			req.setEmail(randomString() + "@test.io");
			req.setName("Mr " + randomString());
			req.setGender(randomString().hashCode() % 2 == 0);
			createUser(req);
		}
	}

	public User createUser(UserReq req) {
		String id = randomString();
		Long now = System.currentTimeMillis();
		jdbcTemplate.update(
				"INSERT INTO user (id, email, name, gender, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?)", // SQL
				id, req.getEmail(), req.getName(), req.isGender(), now, now);
		return getUser(id);
	}

	public List<User> getUsers() {
		return jdbcTemplate.query("SELECT * FROM user ORDER BY createdAt", new BeanPropertyRowMapper<>(User.class));
	}

	public User getUser(String id) {
		return jdbcTemplate.queryForObject("SELECT * FROM user WHERE id=?", new BeanPropertyRowMapper<>(User.class),
				id);
	}

	private String randomString() {
		return UUID.randomUUID().toString();
	}

}
