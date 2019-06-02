package org.springboot.core.dao.db1;

import org.mybatis.spring.annotation.MapperScan;

@MapperScan(basePackages = "org.springboot.core.dao.db1", sqlSessionTemplateRef = "first_sql_session_template")
public interface StudentDao {

}
