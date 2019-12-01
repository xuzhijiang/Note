package org.java.core.base.jdbc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.*;

public class PreparedStatementDemo {

    // 通用的针对于不同表的查询:返回一个对象
    public <T> T getInstance(Class<T> clazz, String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // 1.获取数据库连接
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            // 2. 填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1, args[i]);
            }
            // 3. 执行executeQuery(),得到结果集：ResultSet
            rs = ps.executeQuery();
            // 4. 得到结果集的元数据：ResultSetMetaData
            ResultSetMetaData md = rs.getMetaData();
            // 列的数量
            int columnCount = md.getColumnCount();
            // 因为是返回一个对象,所以这里不用while循环
            if (rs.next()) {
                T t = clazz.newInstance();
                // 遍历每一个列
                for (int i = 0; i < columnCount; i++) {
                    // 获取列的别名，使用类的属性名充当
                    String columnLabel = md.getColumnLabel(i + 1);
                    // 获取这个类自己申明的属性
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, rs.getObject(columnLabel));
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, ps, conn);
            System.out.println("release db resource");
        }
        return null;
    }

    public static void main(String[] args) {
        PreparedStatementDemo preparedStatementDemo = new PreparedStatementDemo();
        String sql = "select * from tb_user where email = ?";
        String email = "xxx@qq.com";
        User user = preparedStatementDemo.getInstance(User.class, sql, email);
        System.out.println(user);
    }

    private static class User {
        Long id;
        String username;
        String password;
        String phone;
        String email;
        Date created;
        Date updated;

        // 如果不加上这个public的无参构造器,这个private的static内部类的构造器就是private的,就会导致反射报错
        public User() {}

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Date getCreated() {
            return created;
        }

        public void setCreated(Date created) {
            this.created = created;
        }

        public Date getUpdated() {
            return updated;
        }

        public void setUpdated(Date updated) {
            this.updated = updated;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", phone='" + phone + '\'' +
                    ", email='" + email + '\'' +
                    ", created=" + created +
                    ", updated=" + updated +
                    '}';
        }
    }
}
