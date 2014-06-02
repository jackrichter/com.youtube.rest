package com.youtube.util;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;

import org.owasp.esapi.ESAPI;

/**
 * Utility class to take database date and convert it to JSON format.
 * 
 * Note:  this java class requires the ESAPI 1.4.4 jar file.
 * ESAPI is used to encode data
 * 
 * @author Jack
 *
 */
public class ToJSON {
	
	/**
	 * This method will read each row returned by the cursor, and then loop through each column, build a JSON object
	 * and put it in a JSON array.
	 * Simply pass in a ResultSet from a database connection and it will convert database records into a JSON object in a JSON Array.
	 * 
	 * It is important to check to make sure that all DataType that are
	 * being used is properly encoding.
	 * varchar is currently the only dataType that is being encode by ESAPI
	 * 
	 * @param rs - database ResultSet
	 * @return - JSON array
	 * @throws Exception
	 */
	public JSONArray toJSONArray (ResultSet rs) throws Exception {
		
		// The JSONArray to be returned
		JSONArray jsonArr = new JSONArray();
		String tmp = null;
		String colName = null;
		
		try {
			
			// We need the columns names and the number of columns in the table, and that can be found in the table meta-data
			ResultSetMetaData rsmd = rs.getMetaData();
			
			// Loop through the rows of the result set
			while (rs.next()) {
				
				// Get the number of columns that are n the table
				int numCol = rsmd.getColumnCount();
				
				// Create a JSON object for each row of data
				JSONObject obj = new JSONObject();
				
				// Loop through all columns in a row and place them into the JSON object. The object consists of a column name/data value pair
				for (int i = 1; i < numCol + 1; i++) {
					
					// Get the column name
					colName = rsmd.getColumnName(i);
					
					// Check each possible type that the data in a column may be of
					if (rsmd.getColumnType(i) == Types.ARRAY) {
						obj.put(colName, rs.getArray(colName));
					}
					else if (rsmd.getColumnType(i) == Types.BIGINT) {
						obj.put(colName, rs.getInt(colName));
					}
					else if (rsmd.getColumnType(i) == Types.BOOLEAN) {
						obj.put(colName, rs.getBoolean(colName));
					}
					else if (rsmd.getColumnType(i) == Types.BLOB) {
						obj.put(colName, rs.getBlob(colName));
					}
					else if (rsmd.getColumnType(i) == Types.DOUBLE) {
						obj.put(colName, rs.getDouble(colName));
					}
					else if (rsmd.getColumnType(i) == Types.FLOAT) {
						obj.put(colName, rs.getFloat(colName));
					}
					else if (rsmd.getColumnType(i) == Types.INTEGER) {
						obj.put(colName, rs.getInt(colName));
					}
					else if (rsmd.getColumnType(i) == Types.NVARCHAR) {
						obj.put(colName, rs.getNString(colName));
					}
					else if (rsmd.getColumnType(i) == Types.VARCHAR) {
						
						// Save column data in a temporary variable
						tmp = rs.getString(colName);
						
						// Reduce a possibly encoded string down to its simplest form (decode back the data to its base state)
						tmp = ESAPI.encoder().canonicalize(tmp);
						
						// Encode data for use in HTML to e browser safe
						tmp = ESAPI.encoder().encodeForHTML(tmp);
						
						// Put the data into the JSON object
						obj.put(colName, tmp);
						
					}
					else if (rsmd.getColumnType(i) == Types.TINYINT) {
						obj.putOpt(colName, rs.getInt(colName));
					}
					else if (rsmd.getColumnType(i) == Types.SMALLINT) {
						obj.putOpt(colName, rs.getInt(colName));
					}
					else if (rsmd.getColumnType(i) == Types.DATE) {
						obj.putOpt(colName, rs.getDate(colName));
					}
					else if (rsmd.getColumnType(i) == Types.TIMESTAMP) {
						obj.putOpt(colName, rs.getTimestamp(colName));
					}
					else if (rsmd.getColumnType(i) == Types.NUMERIC) {
						obj.putOpt(colName, rs.getBigDecimal(colName));
					}
					else {
						// We treat it as a Java Object
						obj.put(colName, rs.getObject(colName));
					}
					
				} // end for
				
				// Put the JSON object into the JSON array to be returned
				jsonArr.put(obj);
				
			} // end while
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonArr;
	}

}
