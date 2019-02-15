package com.journaldev.hibernate.util;

import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import com.journaldev.hibernate.model.Employee1;
// 我们可以使用基于XML或基于属性的配置来进行数据库连接相关配置。

// 我们可以使用基于XML或基于注释的配置来提供模型类和数据库表映射。
// 我们将使用来自javax.persistence的JPA注释来进行基于注释的映射。

// 我创建了一个实用程序类，我从基于XML的配置和基于属性的配置创建SessionFactory。
// 对于基于属性的配置，我们可以有一个属性文件并在类中读取它，
// 但为了简单起见，我在类本身中创建了Properties实例。

// 无论映射是基于XML还是基于注释，为基于XML的配置创建SessionFactory都是相同的。
// 对于基于属性，我们需要在Configuration对象中设置属性并在
// 创建SessionFactory之前添加注释类。
public class HibernateUtil {

	//XML based configuration
	private static SessionFactory sessionFactory;
	
	//Annotation based configuration
	private static SessionFactory sessionAnnotationFactory;
	
	//Property based configuration
	private static SessionFactory sessionJavaConfigFactory;

	/**
	 * 从基于xml的Hibernate Configuration Files构建SessionFactory
	 * @return
	 */
    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
        	Configuration configuration = new Configuration();
        	configuration.configure("hibernate.cfg.xml");
        	System.out.println("Hibernate Configuration loaded");
        	
        	ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        	System.out.println("Hibernate serviceRegistry created");
        	
        	SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        	
            return sessionFactory;
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

	/**
	 * 从基于注解的Hibernate Configuration Files构建SessionFactory
	 * @return
	 */
	private static SessionFactory buildSessionAnnotationFactory() {
    	try {
            // Create the SessionFactory from hibernate-annotation.cfg.xml
        	Configuration configuration = new Configuration();
        	configuration.configure("hibernate-annotation.cfg.xml");
        	System.out.println("Hibernate Annotation Configuration loaded");
        	
        	ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        	System.out.println("Hibernate Annotation serviceRegistry created");
        	
        	SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        	
            return sessionFactory;
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
	}

	/**
	 * 从基于属性的配置构建SessionFactory
	 * @return
	 */
    private static SessionFactory buildSessionJavaConfigFactory() {
    	try {
    	Configuration configuration = new Configuration();
		
		//Create Properties, can be read from property files too
		Properties props = new Properties();
		props.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
		props.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/test");
		props.put("hibernate.connection.username", "root");
		props.put("hibernate.connection.password", "password");
		props.put("hibernate.current_session_context_class", "thread");
		
		configuration.setProperties(props);
		
		//we can set mapping file or class with annotation
		//addClass(Employee1.class) will look for resource
		// com/journaldev/hibernate/model/Employee1.hbm.xml (not good)
		configuration.addAnnotatedClass(Employee1.class);
		
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
    	System.out.println("Hibernate Java Config serviceRegistry created");
    	
    	SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    	
        return sessionFactory;
    	}
        catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
	}
    
	public static SessionFactory getSessionFactory() {
		if(sessionFactory == null) sessionFactory = buildSessionFactory();
        return sessionFactory;
    }
	
	public static SessionFactory getSessionAnnotationFactory() {
		if(sessionAnnotationFactory == null) sessionAnnotationFactory = buildSessionAnnotationFactory();
        return sessionAnnotationFactory;
    }
	
	public static SessionFactory getSessionJavaConfigFactory() {
		if(sessionJavaConfigFactory == null) sessionJavaConfigFactory = buildSessionJavaConfigFactory();
        return sessionJavaConfigFactory;
    }
	
}
