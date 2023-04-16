package com.sk.contactManager.contactManager.security;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

public class DataBaseConnectionUtility {


	private static HikariDataSource datasource=null;
	
	static {
		datasource=new HikariDataSource();
		datasource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		datasource.setJdbcUrl("jdbc:mysql://localhost:3306/contactmanager");
		datasource.setUsername("root");
		datasource.setPassword("root");
		datasource.setMaximumPoolSize(100);
		datasource.setMinimumIdle(10);	
	}
	
	public static DataSource getDataSource()
	{
		return datasource;
	}
	
}
