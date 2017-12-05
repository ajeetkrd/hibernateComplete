/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.complete.HibernateApp.config;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import com.complete.HibernateApp.pojo.*;

public class HibernateUtility {

	private static HibernateUtility hc;
	private Configuration cfg;
	private SessionFactory factory;

	private HibernateUtility() throws HibernateException {

		// build the config
		cfg = new Configuration();

		/**
		 * Connection Information..
		 */

		cfg.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
		cfg.setProperty("hibernate.connection.url", "jdbc:mysql://localhost/onlinetutorialspoint");
		cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		cfg.setProperty("cache.provider_class", "org.hibernate.cache.NoCacheProvider");
		cfg.setProperty("hibernate.connection.username", "root");
		cfg.setProperty("hibernate.connection.password", "root");
		cfg.setProperty("hibernate.show_sql", "true");
		cfg.setProperty("hibernate.hbm2ddl.auto", "create");

		/**
		 * Mapping Resources..
		 */

		cfg.addAnnotatedClass(Student.class);
		
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
		factory = cfg.buildSessionFactory(builder.build());
	}

	public static synchronized HibernateUtility getInstance() throws HibernateException {
		if (hc == null) {
			hc = new HibernateUtility();
		}

		return hc;
	}

	public Session getSession() throws HibernateException {
		Session session = factory.openSession();
	/*	if (!session.isConnected()) {
			this.reconnect();
		}
	*/	return session;
	}
	
	public void closeFactory() throws HibernateException {
		factory.close();
	}

	private void reconnect() throws HibernateException {
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
		this.factory = cfg.buildSessionFactory(builder.build());
	}
}
