package com.youtube.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.codehaus.jettison.json.JSONArray;

import com.youtube.util.ToJSON;

/**
 * This java class will hold all the SQL queries.
 * 
 * Having all SQL/database code in one package makes it easier to maintain and audit
 * but increase complexity.
 * 
 * Note: we also used the extends Oracle308tube on this java class to inherit all
 * the methods in Oracle308tube.java
 * 
 * Note: The tutorial uses the name 308tube, but my own schema in my Oracle db XE 11.2 is called ORAXEDEV.
 * 
 * @author Jack
 *
 */
public class Schema308tube extends Oracle308tube {
	
	/**
	 * This method will search for a specific brand from the PC_PARTS table.
	 * By using prepareStatement and the ?, we are protecting against SQL injection
	 * 
	 * Never add parameter straight into the prepareStatement
	 * 
	 * @param brand - product brand
	 * @return - JSON array of the results from the database
	 * @throws Exception
	 */
	public JSONArray queryReturnBrandParts (String brand) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		
		ToJSON converter = new ToJSON();
		JSONArray jsonArr = new JSONArray();
		
		try {
			
			// Create a connection to db
			conn = OraclePcPartsConnection();
			
			// Create the pre compiled SQL query.Obs. never use select * in real code
			query = conn.prepareStatement("select PC_PARTS_PK, PC_PARTS_TITLE, PC_PARTS_CODE, PC_PARTS_MAKER, PC_PARTS_AVAIL,PC_PARTS_DESC " +
					"from PC_PARTS " +
					"where UPPER(PC_PARTS_MAKER) = ? ");
			
			// Insert the sought parameter into the query in a secure way, protecting against SQL Injection
			query.setString(1, brand.toUpperCase());
			
			// Execute the SQL query and return a cursor
			ResultSet rs = query.executeQuery();
			
			// Convert the data into a JSONArray of JSON objects
			jsonArr = converter.toJSONArray(rs);
			
			// Very Important!
			conn.close();
		}
		catch (SQLException sqlErr) {
			sqlErr.printStackTrace();
			return jsonArr;
		}
		catch (Exception e) {
			e.printStackTrace();
			return jsonArr;
		}
		finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		return jsonArr;
	}
}
