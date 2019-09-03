package com.nobbysoft.first.server.utils;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.common.constants.Constants;

public class ConnectionManager {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private String databaseName;
	private Connection con;
	private Object lock = new Object();

	
	public void shutdown() {
		try {
			DriverManager.getConnection(protocol + databaseName + ";shutdown=true");
		} catch (Exception e) {
			// we expect
			// Caused by: ERROR 08006: Database  'C:\Users\nobby\Documents\nobbysoft\derby\PCDatabase'  shutdown.
			if (!e.getCause().toString().contains("ERROR 08006")) {
				LOGGER.error("Error happened", e);
			} else {
				LOGGER.info("Database successfully shut down");
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public ConnectionManager()  {
		
		this.databaseName = System.getProperty(Constants.PARMNAME_ONEEDDDB);

		try {
			for(String driver:drivers) {
			Class.forName(driver).newInstance();
			}
		} catch (Exception e) {
			throw new IllegalStateException("Unable to load driver "+drivers,e);
		}
	}
// String nsURL="jdbc:derby://localhost:1527/sample";  
	private final String[] drivers = new String[] {
			//"org.apache.derby.jdbc.EmbeddedDriver",
	 "org.apache.derby.jdbc.ClientDriver"};
	private final String protocol = "jdbc:derby://localhost:1527/";
	public Connection getConnection() throws SQLException{ 

		synchronized (lock){
		if(con!=null){
			if(!con.isClosed()){
				return con;
			}
		}
		
		con= DriverManager.getConnection(protocol + databaseName + ";create=true", new Properties());
		}
		return con;
	}

}
