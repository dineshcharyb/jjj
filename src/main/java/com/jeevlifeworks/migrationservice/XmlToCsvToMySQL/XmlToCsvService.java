package com.jeevlifeworks.migrationservice.XmlToCsvToMySQL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jeevlifeworks.migrationservice.ApplicationLevel;

import au.com.bytecode.opencsv.CSVWriter;

@Service
public class XmlToCsvService {
	
	@Autowired
	ApplicationLevel applicationLevel;
	
	
	DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

	// Creating Map to send jsondata to writeExcelFunction
	Map<String, Integer> oldWorkbookDetails = null;
	Map<String, JSONArray> mapOfjsonDataForWriteExcel = null;

	int classLevelCount = 0;
	UUID uuid = null;
	
	String safetyReportId=null;

	
	
	public void xmltoCsv(File file, String fileName) throws IOException {

		try {

			/**
			 * generate UUID for each file getting form client and attach with file Name
			 * and with auto ID
			 */

			uuid = UUID.randomUUID();
			fileName = fileName + "_" + uuid.toString();

			oldWorkbookDetails = new HashMap<String, Integer>();
			mapOfjsonDataForWriteExcel = new HashMap<String, JSONArray>();
			classLevelCount = 0;
			//byte[] b = file.getBytes();
		//String xml = new String(b);
			String xml=FileUtils.readFileToString(file);
			Date dateobjj = new Date();
			System.out.println("Before calling convert to json " + df.format(dateobjj));
			/**
			 * here converting xml to json
			 */
			
			JSONObject json = XML.toJSONObject(xml); // converts xml to json
			String jsonPrettyPrintString = json.toString(4); // json pretty print

			// By calling this function checking either we have existing sheet or not
			// if we have then taking the number of rows from that sheet
			// Date dateobjje = new Date();
			// System.out.println("after calling convert to json and before opening excel
			// file "+df.format(dateobjje));
			// getExistingWorkBookDetails();

			
			Date dateobj1 = new Date();
			System.out.println("before calling recursino function after opening excel file " + df.format(dateobj1));

			/**
			 * here we are calling recursion function by passing generated json.
			 */
			xmlToExcelRecursion(json, "Sheet1");
			Date dateobj2 = new Date();
			System.out.println("before calling write excel  " + df.format(dateobj2));

			/**
			 * after calling recursion function we are generating map (sheetname,JSONArray)
			 * here we are calling writeExcel function by passing this map
			 */
			if (mapOfjsonDataForWriteExcel.size() > 0) {
				
				globalObject(mapOfjsonDataForWriteExcel);
				
			}

			Date dateobj3 = new Date();
			System.out.println("after calling write excel  " + df.format(dateobj3));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Here it is the recursive function
	void xmlToExcelRecursion(JSONObject obj, String sheetName) throws IOException, JSONException {
		// System.out.println(sheetName);
		// This map is used to store parentKey and there values
		Map<String, String> parentkeyValue = new HashMap<String, String>();

		// Creating a list to add all the keys
		List<String> allKeys = new ArrayList<String>();
		
		

		int count1 = 0;

		// Here adding Auto generatedNumber to each sheet
		if (classLevelCount != 0) {
			if (oldWorkbookDetails.containsKey(sheetName)) {

				// here we will take the size of jsonarray inside map whhich we are going to
				// send to writeExcel function

				obj.put(sheetName + "_Auto_Id", oldWorkbookDetails.get(sheetName) + 1 + "_" + uuid);
			} else {

				// here we will take the size of jsonarray inside map whhich we are going to
				// send to writeExcel function

				obj.put(sheetName + "_Auto_Id", 1 + "_" + uuid);
			}
		}
		classLevelCount++;
		// getting all the keys from json
		Iterator<String> keysFromObj = obj.keys();
		// checking and adding all the keys to a list

		JSONObject jsonObjectForWriteExcel = new JSONObject();

		/**
		 * in this below while loop we are creating json object with only leaf nodes
		 * 
		 */
		while (keysFromObj.hasNext()) {
			allKeys.add((String) keysFromObj.next());
			String key = allKeys.get(count1);
			
			if(key.equals("safetyreportid") && sheetName.equals("safetyreport")) {
				System.out.println("got safetyreportid");
				safetyReportId=(String) obj.get(key);
			}

			if (key.endsWith("_Auto_Id")) {
				parentkeyValue.put(key, obj.get(key).toString());
				jsonObjectForWriteExcel.put(key, obj.get(key));
			}

			if (!(obj.get(key) instanceof JSONObject || obj.get(key) instanceof JSONArray)) {

				jsonObjectForWriteExcel.put(key, obj.get(key));
				if(!sheetName.equals("ichicsrmessageheader") && !sheetName.equals("ichicsr") && !sheetName.equals("safetyreport")) {
				//	System.out.println("safeturreportid stored    "+safetyReportId+"        "+sheetName);
					jsonObjectForWriteExcel.put("safetyreportid", safetyReportId);
					
				}
			}
			

			count1++;
		}

		if (jsonObjectForWriteExcel.length() > 0) {

			JSONArray jsonArray2 = mapOfjsonDataForWriteExcel.get(sheetName);
			JSONArray jsonArray = new JSONArray();

			if (jsonArray2 != null) {
				jsonArray2.put(jsonObjectForWriteExcel);
				mapOfjsonDataForWriteExcel.put(sheetName, jsonArray2);
			} else {
				jsonArray.put(jsonObjectForWriteExcel);
				mapOfjsonDataForWriteExcel.put(sheetName, jsonArray);
			}

			Date dateobj2 = new Date();

			/**
			 * increment count in map
			 */

			if (oldWorkbookDetails.containsKey(sheetName)) {

				// here we will take the size of jsonarray inside map whhich we are going to

				oldWorkbookDetails.put(sheetName, oldWorkbookDetails.get(sheetName) + 1);

			} else {

				oldWorkbookDetails.put(sheetName, 1);
			}
		}
		for (String key : allKeys) {

			if (obj.get(key) instanceof JSONArray) {

				JSONArray jsonArray = (JSONArray) obj.get(key);
				for (int k = 0; k < jsonArray.length(); k++) {
					for (String parentKey : parentkeyValue.keySet()) {
						
						if(!sheetName.equals("ichicsrmessageheader") && !sheetName.equals("ichicsr") && !sheetName.equals("safetyreport")) {
							//System.out.println("safeturreportid stored    "+safetyReportId+"        "+sheetName);
							((JSONObject) jsonArray.get(k)).put("safetyreportid", safetyReportId);
							
						}
						
						((JSONObject) jsonArray.get(k)).put(parentKey, parentkeyValue.get(parentKey));
					}
					this.xmlToExcelRecursion((JSONObject) jsonArray.get(k), key.toString());
				}

			} else if (obj.get(key) instanceof JSONObject) {

				for (String parentKey : parentkeyValue.keySet()) {
					if(!sheetName.equals("ichicsrmessageheader") && !sheetName.equals("ichicsr") && !sheetName.equals("safetyreport")) {
					//	System.out.println("safeturreportid stored    "+safetyReportId+"        "+sheetName);
						((JSONObject) obj.get(key)).put("safetyreportid", safetyReportId);
						//System.out.println("safeturreportid stored ");
					}
					((JSONObject) obj.get(key)).put(parentKey, parentkeyValue.get(parentKey));
				}
				this.xmlToExcelRecursion((JSONObject) obj.get(key), key.toString());

			}

		}

	}

	/**
	 * function to write data into csv file
	 * @param jsonData
	 * @param destFolder
	 */
	public void writeCsv1(Map<String, JSONArray> jsonData, String destFolder) {
		System.out.println("InsideWriteCSV");
		Iterator<Map.Entry<String, JSONArray>> itr = jsonData.entrySet().iterator();
		while(itr.hasNext()) {
			Map.Entry<String, JSONArray> entry = itr.next();
			String sheetName = entry.getKey();
			System.out.println(sheetName);
			JSONArray jsonArray = entry.getValue();
			Set<String> columns= new LinkedHashSet<>();
			Set<String> oldColumns=null;
			 List<String> record=null;
			String[] arr = new String[500];
			String[] recordForInsertion =  new String[500];
			
			 List<String[]> allRows=new ArrayList<>();
		
		 try {
//			 try {
//			 CSVReader reader = new CSVReader(new FileReader(sheetName+".csv"));
//			 //Read all rows at once
//		     allRows = reader.readAll();
//		      reader.close();
//			 }catch(Exception e) {
//				 e.printStackTrace();
//			 }
			 
			 FileOutputStream fileStream = new FileOutputStream(new File(destFolder+"/"+sheetName+".csv"));
			 OutputStreamWriter writer1 = new OutputStreamWriter(fileStream, "UTF-8");

			//  FileWriter file1=new FileWriter(sheetName+".csv",true); 
			CSVWriter writer = new CSVWriter(writer1);
	

		      if(allRows.size()>0) {
		      oldColumns=new LinkedHashSet<>(Arrays.asList( allRows.get(0)));
		      //System.out.println(Arrays.asList( allRows.get(0)));
		      }else {
		    	  oldColumns=new LinkedHashSet<>();
		      }
			 
			
			for(int i=0;i<jsonArray.length();i++) {
				columns.addAll(jsonArray.getJSONObject(i).keySet());
			
			}
			
			
			oldColumns.addAll(columns);
			arr = Arrays.copyOf(oldColumns.toArray(), columns.size(), String[].class);
			if(allRows.size()>0) {
			allRows.remove(0);	
			}
			allRows.add(0, arr);	
			 record=new ArrayList<>();
			for(int i=0;i<jsonArray.length();i++) {
				JSONObject onj=jsonArray.getJSONObject(i);
				 Iterator<String> itr1 = oldColumns.iterator(); 
				   while (itr1.hasNext()) {
			        String key=  itr1.next();
			        //System.out.println(key);
			       
			        if(onj.has(key)) {
			        	
			      
			        	record.add((String) onj.get(key).toString());
			        
			        }else{
			        	record.add("");
			        }
			        
			        
				   }
				  
				   recordForInsertion=Arrays.copyOf(record.toArray(), record.size(), String[].class);
				  // System.out.println(recordForInsertion);
				   allRows.add(recordForInsertion);
				   record.clear();
				   recordForInsertion=null;
				 //  System.out.println(allRows);
			} 
			
			
			
			writer.writeAll(allRows);
			 writer.flush();
			 writer.close();
			System.out.println("Fiile Closed");
			allRows.clear();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
		/**
		 * clear the JSONArray after inserting data into csv file to avoid data rewriting
		 */
		applicationLevel.jsonArrayData.clear();
	}
	
	public void globalObject(Map<String, JSONArray> jsonArrayMap) {
		System.out.println("inside global object");
		Iterator<Map.Entry<String, JSONArray>> itr = jsonArrayMap.entrySet().iterator();
		while(itr.hasNext()) {
		Map.Entry<String, JSONArray> entry = itr.next();
		String sheetName = entry.getKey();
	//	System.out.println(sheetName);
		JSONArray jsonArray = entry.getValue();
		
			if(applicationLevel.jsonArrayData.containsKey(sheetName)) {
				
				JSONArray jsonArr=applicationLevel.jsonArrayData.get(sheetName);
				for(int i=0;i<jsonArray.length();i++) {
					jsonArr.put(jsonArray.get(i));
				}
				applicationLevel.jsonArrayData.put(sheetName, jsonArr);
				
				//applicationLevel.jsonArrayData.get(sheetName).put(jsonArray);
			}else {
				applicationLevel.jsonArrayData.put(sheetName, jsonArray);
			}
		}
		
		
		
		
	}
		
	}
	
	


