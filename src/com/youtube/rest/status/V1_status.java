package com.youtube.rest.status;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * This is the root for our restful api service.
 * In the web.xml file, we specify that /api/* need to be in the URL to
 * get to this class.
 * 
 * We are versioning the class in the URL path. This is the first version v1.
 * Example how to get to this api resource:
 * http://localhost:7001/com.youtube.rest/api/v1/status
 * 
 * @author Jack (by 308tube9
 *
 */
@Path("/v1/status/*")				// A root path in which this java class will be called
public class V1_status {
	
	private static final String API_VERSION = "00.01.00";
	
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
	
	@Path("/version")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnVersion() {
		return "<p>Version:</p>" + API_VERSION;
	}

}
