package com.revature.ers.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import oracle.jdbc.driver.OracleDriver;

public class ConnectionFactory {

	private static Logger log = Logger.getLogger(ConnectionFactory.class);
	private static ConnectionFactory cf = new ConnectionFactory();
	
	private ConnectionFactory() {
		super();
	}
	
	public static ConnectionFactory getInstance() {
		return cf;
	}
	
	public Connection getConnection() {
		
		Connection conn = null;
		
		// We use a .properties file so we do not hard-code our DB credentials into the source code
		Properties prop = new Properties();
		
		try {
			
			// Load the properties file (application.properties) keys/values into the Properties object

			// ERROR ConnectionFactory:47 - src/main/resources/application.properties (No such file or directory)
			//////////////////////////////////////////////////////////////////////////////////////////////////////
			DriverManager.registerDriver(new OracleDriver());

//			prop.load(new FileReader("src/main/resources/application.properties")); // doesn't work
//			prop.load(new FileReader("C:\\Users\\kamar\\Desktop\\Revature\\KamariaThanh\\ExpenseReimbursementSystem\\ExpenseReimbursementSystem\\src\\main\\resources\\application.properties"));
			prop.load(new FileReader("/home/thn05/Dropbox/_RevatureTraining/KamariaThanh/ExpenseReimbursementSystem/ExpenseReimbursementSystem/src/main/resources/application.properties"));
			
			// Get a connection from the DriverManager class
			conn = DriverManager.getConnection(
					prop.getProperty("url"),
					prop.getProperty("usr"),
					prop.getProperty("pw"));
			
		} catch (SQLException sqle) {
			log.error(sqle.getMessage());
		} catch (FileNotFoundException fnfe) {
			log.error(fnfe.getMessage());
		} catch (IOException ioe) {
			log.error(ioe.getMessage());
		}
		return conn;
	}
}
