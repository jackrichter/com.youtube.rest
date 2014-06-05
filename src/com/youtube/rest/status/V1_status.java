package com.youtube.rest.status;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;

import com.youtube.dao.*;

/**
 * This is the root for our restful api service.
 * In the web.xml file, we specify that /api/* need to be in the URL to
 * get to this class.
 * 
 * We are versioning the class in the URL path. This is the first version v1.
 * Example how to get to this api resource:
 * http://localhost:7001/com.youtube.rest/api/v1/status
 * 
 * Adding v1 to the ath is a good way to keep versioning our releases without depricating. This is best practice.
 * 
 * @author Jack (by 308tube9
 *
 */
//A root path in which this java class will be called
@Path("/v1/status/")	// Removed * (../*) wildcard to make it more compatible with tomcat			
public class V1_status {
	
	private static final String API_VERSION = "00.02.00";	// With the database status method we improved the api, and we can now say it is version 2 
	
	/**
	 * This method sits at the root of the api. It will return the name
	 * of this api.
	 * 
	 * @return String - Title of the api
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnTitle() {
		return "<p>Java Web Service</p>";
	}
	
	/**
	 * By adding version to the root path, another GET call is enabled.
	 * Otherwise the two GET calls would conflict.to the path URL.
	 * 
	 * Note: this nested one down from the root. You will need to add /version in
	 * 
	 * Ex.: http://localhost:7001/com.youtube.rest/api/v1/status/version, returns 00.1.00
	 * 
	 * @return String - Version of the api
	 */
	@Path("/version")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnVersion() {
		return "<p>Version:</p>" + API_VERSION;
	}
	
	/**
	 * A generic method to demonstrate retreiving info from a database (in this case time)
	 * and make available as a web service.
	 * 
	 * @return Response - An HTTP Response back to the client containing the Date and time of the connected Database via JNDI "308tubeOracle"
	 * @throws Exception
	 */
	@Path("/database")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response returnDatabaseStatus () throws Exception {
		
		/**
		 * Class refactored to fit the dao package
		 */
		
		String returnString = null;
		
		JSONArray jsonArr = new JSONArray();
		
		try {
			
			// Instance of the class that performs the SQL work and create the DB connection
			Schema308tube dao = new Schema308tube();
			
			// Query for DB Status (Date/Time)
			jsonArr = dao.queryCheckDbConnection();
			
			returnString = "<p>Database Status</p>" + 
					"<p> Database Date/Time returned: " + jsonArr.toString() + "</p>";
			
		}
		catch (Exception e) {
			e.printStackTrace();
			Response.status(500).entity("Server was not able to process your request.").build();
		}
		
		return Response.ok().entity(returnString).build();
	}

}
