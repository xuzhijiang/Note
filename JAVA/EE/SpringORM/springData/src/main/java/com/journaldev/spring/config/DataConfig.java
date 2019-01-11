package com.journaldev.spring.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.ejb.HibernatePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// @Configuration：这个spring注释说它是配置类。

// @EnableTransactionManagement：此注释允许用户在应用程序中使用事务管理。

// @EnableJpaRepositories（“com.journaldev.spring.repository”）：
// 表示repositories classes(存储库类)的存在位置。

// @PropertySource（“classpath：database.properties”）：
// 说我们的类路径中有属性文件。 此文件中的值将注入到环境变量中。 database.properties文件的内容如下所示:

//driver=org.postgresql.Driver
//url=jdbc:postgresql://127.0.0.1:5432/postgres
//user=postgres
//password=postgres
//
//hibernate.dialect=org.hibernate.dialect.PostgreSQL82Dialect
//hibernate.show_sql=true

// 对于使用Spring Data，首先我们必须配置DataSource bean。 
// 然后我们需要配置LocalContainerEntityManagerFactoryBean bean。 
// 我们需要这个bean来控制实体。 在这个bean中，您必须在我们的例子中指定持久性提供程序，即HibernatePersistence。

// 下一步是配置bean以进行事务管理。 在我们的例子中，它是JpaTransactionManager。 
// 请注意，如果不配置事务管理器，我们就无法使用@Transactional注释。

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.journaldev.spring.repository")
@PropertySource("classpath:database.properties")
public class DataConfig {

	private final String PROPERTY_DRIVER = "driver";
	private final String PROPERTY_URL = "url";
	private final String PROPERTY_USERNAME = "user";
	private final String PROPERTY_PASSWORD = "password";
	private final String PROPERTY_SHOW_SQL = "hibernate.show_sql";
	private final String PROPERTY_DIALECT = "hibernate.dialect";

	@Autowired
	Environment environment;

	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean lfb = new LocalContainerEntityManagerFactoryBean();
		lfb.setDataSource(dataSource());
		lfb.setPersistenceProviderClass(HibernatePersistence.class);
		lfb.setPackagesToScan("com.journaldev.spring.model");
		lfb.setJpaProperties(hibernateProps());
		return lfb;
	}

	@Bean
	DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setUrl(environment.getProperty(PROPERTY_URL));
		ds.setUsername(environment.getProperty(PROPERTY_USERNAME));
		ds.setPassword(environment.getProperty(PROPERTY_PASSWORD));
		ds.setDriverClassName(environment.getProperty(PROPERTY_DRIVER));
		return ds;
	}

	Properties hibernateProps() {
		Properties properties = new Properties();
		properties.setProperty(PROPERTY_DIALECT, environment.getProperty(PROPERTY_DIALECT));
		properties.setProperty(PROPERTY_SHOW_SQL, environment.getProperty(PROPERTY_SHOW_SQL));
		return properties;
	}

	@Bean
	JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}
	
}
