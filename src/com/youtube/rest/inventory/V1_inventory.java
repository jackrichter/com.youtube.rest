package com.youtube.rest.inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.QueryParam;

import org.codehaus.jettison.json.JSONArray;

import com.youtube.dao.Oracle308tube;
import com.youtube.util.ToJSON;

/**
 * This class is used to manage inventory for computer parts as per the PC_Parts table in Oracle XE DB (sqldeveloper)
 * 
 * @author Jack
 *
 */
@Path("/v1/inventory/*")
public class V1_inventory {
	
	/**
	 * This method will return all computer parts that are listed
	 * in PC_PARTS table.
	 * 
	 * Note: In real situations is never good to return everything from a database.
	 * There should be built in limits.
	 * 
	 * @return - JSON array string
	 * 
	 * @throws Exception
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String returnAllPcParts () throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		
		try {
			
			// Make a connection to our Oracle db
			conn = Oracle308tube.Oracle308tubeConn().getConnection();
			
			// Create the java equivalent "precompiled stored procedure"
			query = conn.prepareStatement("select * from PC_PARTS");
			
			// Execute the SQL query and return a cursor
			ResultSet rs = query.executeQuery();
			
			// Convert the contents of the returned data set into a JSONArray, using the utility package
			ToJSON converter = new ToJSON();
			JSONArray jsonArray = new JSONArray();
			jsonArray = converter.toJSONArray(rs);
			
			// Close db connection
			conn.close();
			
			// Make the returning string
			returnString = jsonArray.toString();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (conn != null) conn.close();
		}
		
		return returnString;
	}

}
