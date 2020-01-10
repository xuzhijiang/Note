package com.springdata.jdbc.core.controller;

import com.springdata.jdbc.core.domain.User;
import com.springdata.jdbc.core.request.UserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// update(): 执行DML语句。增、删、改语句
// queryXXX(): 查询(DQL语句)
@RestController
public class JDBCController {

    //自动注入Spring所提供的JdbcTemplate对象
    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        String sqlDropTable = "DROP TABLE IF EXISTS user";
        jdbcTemplate.execute(sqlDropTable);
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
        List<User> allUsers = getAllUsers();
        for (User u : allUsers) {
            System.out.println(u);
        }
    }

    // INSERT
    //@Transactional可以应用于方法和类.
    // 如果你希望所有方法都具有事务管理功能，则应该应用于类.
    @Transactional
    @PostMapping("/api/users")
    public User createUser(@RequestBody UserReq req) {
        String id = randomString();
        Long now = System.currentTimeMillis();
        // 保存一个用户对象
        String sql = "INSERT INTO user (id, email, name, gender, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, id, req.getEmail(), req.getName(), req.isGender(), now, now);
        return getUserById(id);
    }

    // QUERY ALL
    // 查询所有记录，将其封装为List集合
    @GetMapping("/api/users")
    public List<User> getUsers() {
        String sql = "SELECT * FROM user ORDER BY createdAt";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    // QUERY ALL
    // 查询所有记录，将其封装为List集合
    public List<User> getUsersByMyself() {
        String sql = "SELECT * FROM user ORDER BY createdAt";
        List<User> list = jdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int i) throws SQLException {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
                user.setGender(rs.getBoolean("gender"));
                user.setCreatedAt(rs.getLong("createdAt"));
                user.setUpdatedAt(rs.getLong("updatedAt"));
                return null;
            }
        });
        return list;
    }

    // QUERY ONE
    @GetMapping("/api/users/{id}")
    public User getUserById(@PathVariable("id") String id) {
        String sql = "SELECT * FROM user WHERE id=?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
        // 下面这种方式也可以.
        //return jdbcTemplate.queryForObject("SELECT * FROM user WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(User.class));
    }

    // UPDATE
    @PutMapping("/api/users/{id}")
    public User updateUser(@PathVariable("id") String id, @RequestBody UserReq req) {
        String sql = "UPDATE user SET email=?, name=?, gender=?, updatedAt=? WHERE id=?";
        // 返回受影响的行
        int line = jdbcTemplate.update(sql, // SQL
                req.getEmail(), req.getName(), req.isGender(), System.currentTimeMillis(), id);
        return getUserById(id);
    }

    // DELETE
    @DeleteMapping("/api/users/{id}")
    public boolean deleteUser(@PathVariable("id") String id) {
        String sql = "DELETE FROM user WHERE id=?";
        int n = jdbcTemplate.update(sql, id);
        return n == 1;// 或者判断n>0也可以,也就是有多少行受影响.
    }

    private String randomString() {
        return UUID.randomUUID().toString();
    }

    @Bean
    public Docket userApi() {
        return new Docket(DocumentationType.SWAGGER_2).select().paths(PathSelectors.regex("^/api/.*$")).build();
    }

    //提取所有的用户数据
    private List<User> getAllUsers(){
        String sql="select * from user";
        final List<User> users=new ArrayList<>();
        // 从数据库中提取记录集，针对每条记录，调用RowCallbackHandler回调函数
        jdbcTemplate.query(sql, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                //调用辅助函数创建User对象，并将其加入到对象集合中
                System.out.println("processRow called!");
                users.add(fillUser(resultSet));
            }
        });
        return users;
    }

    // 从ResultSet中提取数据，创建一个User对象
    private static User fillUser(ResultSet rs) throws SQLException {
        if(rs==null){
            return null;
        }
        User user=new User();
        user.setId(rs.getString("id"));
        user.setEmail(rs.getString("email"));
        user.setName(rs.getString("name"));
        user.setGender(rs.getBoolean("gender"));
        user.setCreatedAt(rs.getLong("createdAt"));
        user.setUpdatedAt(rs.getLong("updatedAt"));
        return user;
    }

    // 查询总记录数
    public void testQueryForObject() {
        String sql="select * from user";
        Long total = jdbcTemplate.queryForObject(sql, Long.class);
        System.out.println(total);
    }
}
