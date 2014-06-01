package com.youtube.dao;

import javax.naming.*;
import javax.sql.*;

/**
 * The class demonstrates an example of a Data Access Object class, that supplies
 * a DataSource to the external Weblogic database resource registered in JNDI as "308tubeOracle".
 * 
 * It is a static class (and method), as there should always be only one connection open.
 * 
 * @author Jack
 *
 */
public class Oracle308tube {
	
	private static DataSource Oracle308tube = null;
	private static Context context = null;
	
	public static DataSource Oracle308tubeConn () throws Exception {
		
		if (Oracle308tube != null) {
			return Oracle308tube;
		}
		
		try {
			
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

}
