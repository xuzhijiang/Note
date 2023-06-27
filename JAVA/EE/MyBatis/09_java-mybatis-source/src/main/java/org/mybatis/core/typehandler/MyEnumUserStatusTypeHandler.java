package org.mybatis.core.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * 1、实现TypeHandler接口。或者继承BaseTypeHandler
 *
 * MyEnumUserStatusTypeHandler 用来处理 数据库字段user_status 和 User中的属性userStatus之间的映射.
 */
public class MyEnumUserStatusTypeHandler implements TypeHandler<UserStatus> {

    /**
     * 定义 当前数据 如何 保存到 数据库中
     */
    @Override
    public void setParameter(PreparedStatement ps, int i, UserStatus parameter,
                             JdbcType jdbcType) throws SQLException {
        System.out.println("要保存的状态码："+parameter.getCode());
        ps.setString(i, parameter.getCode().toString());
    }

    /**
     * 定义 从 数据库中 取出的数据 如何 封装
     */
    @Override
    public UserStatus getResult(ResultSet rs, String columnName)
            throws SQLException {
        //需要根据从数据库中拿到的枚举的状态码返回一个枚举对象
        int code = rs.getInt(columnName);
        System.out.println("从数据库中获取的状态码："+code);
        UserStatus status = UserStatus.getEmpStatusByCode(code);
        return status;
    }

    @Override
    public UserStatus getResult(ResultSet rs, int columnIndex)
            throws SQLException {
        int code = rs.getInt(columnIndex);
        System.out.println("从数据库中获取的状态码："+code);
        UserStatus status = UserStatus.getEmpStatusByCode(code);
        return status;
    }

    @Override
    public UserStatus getResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        int code = cs.getInt(columnIndex);
        System.out.println("从数据库中获取的状态码："+code);
        UserStatus status = UserStatus.getEmpStatusByCode(code);
        return status;
    }

}
