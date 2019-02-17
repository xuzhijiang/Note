package com.journaldev.hibernate.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

	private static SessionFactory sessionFactory;
	
	private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration config = new Configuration();
            config.configure("hibernate.cfg.xml");
        	System.out.println("Hibernate Configuration loaded");

        	ServiceRegistry serviceRegistry1 = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        	System.out.println("Hibernate serviceRegistry created");

        	SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry1);

            return sessionFactory;
        }
        catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }
	
	public static SessionFactory getSessionFactory() {
		if(sessionFactory == null) sessionFactory = buildSessionFactory();
        return sessionFactory;
    }
}
