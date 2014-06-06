package com.youtube.rest.inventory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.QueryParam;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;

import com.youtube.dao.Schema308tube;

/**
 * Version 2 of inventory.
 * 
 * We are version controlling via the URL path.
 * This method of version controlling makes the changes more
 * transparent to the users and developers.
 * It allows the uses to have more time for transition.
 * 
 * @author Jack
 *
 */
@Path("/v2/inventory/")
public class V2_inventory {
	
	/**
	 * This method will return the specific brand of PC parts the user is looking for.
	 * It uses QueryParam to bring in the data to the method.
	 * 
	 * Using QueryParam the value is provided directly in the URL path, after a ?, as a key/value pair. E.g. ?brand=ASUS
	 * 
	 * Example:
	 * http://localhost:7001/com.youtube.rest/api/v2/inventory?brand=ASUS
	 * 
	 * @param brand - product brand name
	 * @return - JSON array result list from the database 
	 * @throws Exception
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrandParts (@QueryParam("brand") String brand) throws Exception {
		
		String returnString = null;
		JSONArray jsonArray = new JSONArray();
		
		try {
			
			// Inform client if brand parameter is missing in URL
			if (brand == null) {
				return Response.status(400).entity("Error: please specify brand for this search").build();
			}
			
			// Instance of the class that performs the SQL work
			Schema308tube dao = new Schema308tube();
			
			// Retrieve the sought data from db returned in a JSONArray
			jsonArray = dao.queryReturnBrandParts(brand);
			
			// Extract the string representation that will be placed in the body of the HTTP Response
			returnString = jsonArray.toString();
			
		}
		catch (Exception e) {
			e.printStackTrace();
			Response.status(500).entity("Server was not able to process your request.").build();
		}
		
		return Response.ok(returnString).build();
	}	

	/**
	 * Method to allow for an error message if on the URL path from the root is missing a brand and the
	 * above method is missing.
	 * 
	 * Note: Two methods with annotation @GET only are not allowed. 
	 * See class V1_status.
	 * 
	 * @return - Response embodied with an error message 
	 * @throws Exception
	 */
/*	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnErrorOnBrand () throws Exception {
		return Response.status(400).entity("Error: please specify brand for this search").build();
	}*/
	
	/**
	 * This method will return the specific brand of PC parts the user is looking for.
	 * It uses PathParam to bring in the data to the method.
	 * 
	 * Using PathParam, the value is brought in as part of the path, e.g. /ASUS.
	 * By naming the Path value as the variable (or entity) we are expecting, this is made possible.
	 * 
	 * Note that we combine the use of two annotations: @Path (with curly brackets) and @PathParam in the method's signature.
	 * 
	 * Example:
	 * http://localhost:7001/com.youtube.rest/api/v2/inventory/ASUS
	 * 
	 * @param brand - product brand name
	 * @return - JSON array result list from the database 
	 * @throws Exception
	 */
	@Path("/{brand}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrand (@PathParam("brand") String brand) throws Exception {
		
		String returnString = null;
		JSONArray jsonArray = new JSONArray();
		
		try {
			
			/**
			 * This check is not needed as we can't get to this method without using the brand as part of the URL path.
			 */
/*			// Inform client if brand parameter is missing in URL
			if (brand == null) {
				return Response.status(400).entity("Error: please specify brand for this search").build();
			}*/
			
			// Instance of the class that performs the SQL work
			Schema308tube dao = new Schema308tube();
			
			// Retrieve the sought data from db returned in a JSONArray
			jsonArray = dao.queryReturnBrandParts(brand);
			
			// Handle the event that a certain brand doe's not exist in db
			if (jsonArray.length() < 1) {
				return Response.status(404).entity("Error: No matches for specified brand.").build();
			}
			
			// Extract the string representation that will be placed in the body of the HTTP Response
			returnString = jsonArray.toString();
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request.").build();
		}
		
		return Response.ok(returnString).build();
	}
	
	/**
	 * This method does a search on both product and the product item number (= PC_PARTS_CODE).
	 * It uses PathParam to bring in both parameters.
	 * 
	 * Example:
	 * http://localhost:7001/com.youtube.rest/api/v2/inventory/ASUS/168131318
	 * 
	 * @param brand - the product brand
	 * @param itemNumber - the particular code number for an item
	 * @return - JSON array result list from the database
	 * @throws Exception
	 */
	@Path("/{brand}/{itemNumber}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnSpecificBrandItem (@PathParam("brand") String brand, @PathParam("itemNumber") int itemNumber) throws Exception {
		
		String returnString = null;
		JSONArray jsonArray = new JSONArray();
		
		try {
			
			// Instance of the class that performs the SQL work
			Schema308tube dao = new Schema308tube();
			
			// Retrieve the sought data from db returned in a JSONArray
			jsonArray = dao.queryReturnBrandItemNumber(brand, itemNumber);
			
			// Extract the string representation that will be placed in the body of the HTTP Response
			returnString = jsonArray.toString();
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request.").build();
		}
		
		return Response.ok(returnString).build();
	}
	
	/**
	 * This method will allow you to insert data the PC_PARTS table.  
	 * This is a example of using the Jackson Processor
	 * 
	 * Note: If you look, this method addPcParts using the same URL as a GET method returnBrandParts.
	 * 		 We can do this because we are using different HTTP methods for the same URL string.
	 * 
	 * @param incomingData - must be in JSON format
	 * @return String
	 * @throws Exception
	 */
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPcParts (String incomingData) throws Exception {
		
		String returnString = null;
		
		Schema308tube dao = new Schema308tube();
		
		try {
			System.out.println("IncomingData: " + incomingData);
			
			/**
			 * ObjectMapper is from Jackson Processor framework
			 * http://jackson.codehaus.org/
			 * 
			 * Using the readValue method, you can parse the JSON from the HTTP request
			 * and data bind it to a Java Class.
			 */
			ObjectMapper mapper = new ObjectMapper();
			ItemEntry itemEntry = mapper.readValue(incomingData, ItemEntry.class);
			
			int httpCode = dao.insertIntoPc_Parts(itemEntry.PC_PARTS_TITLE, itemEntry.PC_PARTS_CODE, itemEntry.PC_PARTS_MAKER,
					itemEntry.PC_PARTS_AVAIL, itemEntry.PC_PARTS_DESC);
			
			if (httpCode == 200) {
				//returnString = jsonArray.toString()
				returnString = "Item inserted";
			}
			else {
				return Response.status(500).entity("Unable to process Item").build();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}
}

/**
 *
 * This is a class used by the addPcParts method (POST). We use it to bind incoming data to it.
 * Used by the Jackson Processor
 * 
 * Note: for re-usability you should place this in its own package. In that case these classes are called: Domain Objects.
 * 
 * @author Jack
 *
 */
class ItemEntry {
	public String PC_PARTS_TITLE;
	public String PC_PARTS_CODE;
	public String PC_PARTS_MAKER;
	public String PC_PARTS_AVAIL;
	public String PC_PARTS_DESC;
}
