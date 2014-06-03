package com.youtube.dao;

import java.sql.Connection;

import javax.naming.*;
import javax.sql.*;

/**
 * The class demonstrates an example of a Data Access Object class, that supplies
 * a DataSource to the external Weblogic database resource registered in JNDI as "308tubeOracle".
 * 
 * It is a static class (and method), as there should always be only one connection open.
 * Being static can save resources.
 * You only need one instance of this class running.
 * 
 * @author Jack
 *
 */
public class Oracle308tube {
	
	private static DataSource Oracle308tube = null;
	private static Context context = null;
	
	/**
	 * This is a method that will return a connection to the ORAXEDEV database (called 308TubeOracle).
	 * 
	 * This method's sole job is to lookup the connection within Weblogic.
	 * 
	 * @return - Database object
	 * @throws Exception
	 */
	public static DataSource Oracle308tubeConn () throws Exception {
		
		// Check to see if the database object is already defined. If it is, no need to look it up again.
		if (Oracle308tube != null) {
			return Oracle308tube;
		}
		
		try {
			
			// This only needs to run once to get the database object.
			// context is used to lookup the database object in Weblogic.
			// Oracle308tube will hold the database object.
			if (context == null) {
				context = new InitialContext();
			}
			
			Oracle308tube = (DataSource) context.lookup("308tubeOracle");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return Oracle308tube;
	}

	/**
	 * This methods purpose is to create the actual connection to the database (Oracle ORAXEDEV schema).
	 * 
	 * @return - Connection to the Oracle ORAXEDEV database.
	 */
	protected static Connection OraclePcPartsConnection () {
		Connection conn = null;
		
		try{
			conn = Oracle308tubeConn().getConnection();
			return conn;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
}
