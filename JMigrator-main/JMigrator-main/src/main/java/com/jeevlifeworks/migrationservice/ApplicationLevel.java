package com.jeevlifeworks.migrationservice;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.springframework.stereotype.Component;

@Component
public class ApplicationLevel {
	
	public Map<String, String> applicationLevelMap = new HashMap<String, String>();
	
	public  Map<String, Object[]> elementsData = new LinkedHashMap<String, Object[]>(); 
   
	/**
	 * every time when converting xml file to csv , open and closing of csv files leads to lower the performance
	 * so data of each xml file is stored in JSONArray ,so that at once we can put xml data in jsonarray into csv files 
	 * to increase the performance
	 */
	public Map<String, JSONArray> jsonArrayData = new HashMap<String, JSONArray>();

	public JSONArray fileNames;
}
