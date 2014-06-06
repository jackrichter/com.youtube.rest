package com.youtube.item;

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
public class ItemEntry {
	public String PC_PARTS_TITLE;
	public String PC_PARTS_CODE;
	public String PC_PARTS_MAKER;
	public String PC_PARTS_AVAIL;
	public String PC_PARTS_DESC;
}
