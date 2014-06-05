package com.youtube.rest.inventory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;

import com.youtube.dao.Schema308tube;

/**
 * This class is used to manage inventory for computer parts as per the PC_Parts table in Oracle XE DB (sqldeveloper)
 * 
 * @author Jack
 *
 */
@Path("/v1/inventory/")		// Removed * (../*) wildcard to make it more compatible with tomcat
public class V1_inventory {
	
	/**
	 * This method will return all computer parts that are listed
	 * in PC_PARTS table.
	 * 
	 * Note: In real situations is never good to return everything from a database.
	 * There should be built in limits.
	 * 
	 * @return - An HTTP Response back to the client
	 * @throws Exception
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnAllPcParts () throws Exception {
		
		/**
		 * Class refactored to fit the dao package
		 */
		
		String returnString = null;
		Response rb = null;
		
		JSONArray jsonArray = new JSONArray();
		
		try {
			
			// Instance of the class that performs the SQL work and create the DB connection
			Schema308tube dao = new Schema308tube();
			
			// Query for all parts
			jsonArray = dao.queryAllParts();
			
			// Make the string that will be attached to our HTTP Response
			returnString = jsonArray.toString();
			
			// Create the HTTP Response, add the string to the body of it, and compile it together
			rb = Response.ok(returnString).build();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return rb;
	}
}
