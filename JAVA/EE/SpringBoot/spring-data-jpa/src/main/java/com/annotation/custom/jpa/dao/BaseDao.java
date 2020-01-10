package com.annotation.custom.jpa.dao;

import com.annotation.custom.jpa.annotation.Table;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class BaseDao<T> {

    //private static BasicDataSource datasource = new BasicDataSource();

    //静态代码块,设置连接数据库的参数
    /*
    static{
        datasource.setDriverClassName("com.mysql.jdbc.Driver");
        datasource.setUrl("jdbc:mysql://localhost:3306/test");
        datasource.setUsername("root");
        datasource.setPassword("123456");
    }
    */

    //得到jdbcTemplate
    //private JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);

    // 泛型参数的Class对象
    private Class<T> beanClass;

    /**
     * 在这里获得子类传递的实际类型参数T的Class对象
     */
    public BaseDao() {
        // 获取子类的Class对象，假设是User.class
        Class<? extends BaseDao> clazz = this.getClass();
        // 通过子类的Class对象,得到泛型化的父类的Class对象,相当于拿到了BaseDao<T>
        Type genericSuperclass = clazz.getGenericSuperclass();
        // 打印发现: 上面的BaseDao<T>的Class对象是ParameterizedType的类型
        System.out.println("genericSuperclass: " + genericSuperclass);
        // cast
        ParameterizedType parameteriedSuperClass = (ParameterizedType) genericSuperclass;
        // 获取泛型父类 的 实际类型参数 数组
        Type[] arguments = parameteriedSuperClass.getActualTypeArguments();
        beanClass = (Class<T>) arguments[0];
    }

    public void add(T bean) {
        // 得到bean对象的所有字段
        Field[] declaredFields = beanClass.getDeclaredFields();

        // 拼接SQL
        Table annotation = beanClass.getAnnotation(Table.class);
        String tableName = annotation.value();

        // String sql = "INSERT INTO " + beanClass.getSimpleName() + " (";
         String sql = "INSERT INTO " + tableName + " (";

        for (int i=0;i<declaredFields.length;i++) {
            sql += declaredFields[i].getName();
            if (i < declaredFields.length - 1) {
                sql += ", ";
            }
        }

        sql += ") VALUES (";
        for (int i=0;i<declaredFields.length;i++) {
            sql += "?";
            if (i < declaredFields.length - 1) {
                sql += ",";
            }
        }
        sql += ")";

        // 获取bean中字段要插入的值
        ArrayList<Object> paramList = new ArrayList<>();
        for (int i=0;i<declaredFields.length;i++) {
            try {
                declaredFields[i].setAccessible(true);
                Object param = declaredFields[i].get(bean);
                paramList.add(param);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        int size = paramList.size();
        Object[] params = paramList.toArray(new Object[size]);

        System.out.println(sql);
        System.out.println(Arrays.toString(params));

        // int num = jdbcTemplate.update(sql, params);
    }

}
