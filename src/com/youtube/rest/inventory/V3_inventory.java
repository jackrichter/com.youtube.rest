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

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.youtube.dao.Schema308tube;

/**
 * @author Jack
 *
 */
@Path("/v3/inventory/")
public class V3_inventory {

	/**
	This method will allow you to insert data the PC_PARTS table.
	 * This is a example of using JSONArray and JSONObject
	 * 
	 * Note: If you look, this method addPcParts using the same URL as a GET method returnBrandParts.
	 * 		 We can do this because we are using different HTTP methods for the same URL string.
	 * 
	 * @param incomingData - must be in JSON format. This type of param is known as the message body or the payload
	 * @return Response
	 * @throws Exception
	 */
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPcParts (String incomingData) throws Exception {
		
		String returnString = null;
		
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		
		Schema308tube dao = new Schema308tube();
		
		try {
			
			/**
			 * OIn this single line, by passing the message body as a constructor parameter,
			 * We consume the payload and the JSON object contains all the data that came
			 * in the message body
			 * 
			 * Note: We do not need to bind it into a Java Domain Object.
			 */
			JSONObject partsData = new JSONObject(incomingData);
			System.out.println("jsonData: " + partsData.toString());
			
			// Look for each value using it key value (In JSON everything is in Key/Value format)
			int httpCode = dao.insertIntoPc_Parts(partsData.optString("PC_PARTS_TITLE"),
					partsData.optString("PC_PARTS_CODE"), partsData.optString("PC_PARTS_MAKER"),
					partsData.optString("PC_PARTS_AVAIL"), partsData.optString("PC_PARTS_DESC"));
			
			if (httpCode == 200) {
				jsonObject.put("HTTP_CODE", "200");
				jsonObject.put("MSG", "Item has been entered successfully, Version 3");
				returnString = jsonArray.put(jsonObject).toString();
			}
			else {
				return Response.status(500).entity("Unable to process Item").build();
			}
			
			System.out.println("retunString: " + returnString);
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}
}
