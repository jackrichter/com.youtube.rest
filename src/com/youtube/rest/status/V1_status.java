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
 * Adding v1 to the ath is a good way to keep versioning our releases without depricating. This is best practice.
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

}
