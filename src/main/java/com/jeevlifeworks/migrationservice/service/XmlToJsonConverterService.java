package com.jeevlifeworks.migrationservice.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.netty.handler.codec.json.JsonObjectDecoder;

@Service
public class XmlToJsonConverterService {
	
	
	@Value("${jsonOutputFolderDir}")
	String jsonOutputFolderDir;
	
	
	/**
	 * @author Abhishek Shetkar
	 * @Date 24-01-2021
	 * 
	 *  
	 * This function will convert Xml file into Json object using predefined libraries
	 * 
	 * 
	 * @param file
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public String xmlToJsonConverter(File file, String fileName) throws IOException {
		
		
		String xml=FileUtils.readFileToString(file);
		JSONObject json = XML.toJSONObject(xml); // converts xml to json
		String jsonPrettyPrintString = json.toString(4); // json pretty print
		createJsonFile(json,fileName);
		
		return "Success";
	}

	
	/**
	 * @author Abhishek Shetkar
	 * @Date 24-01-2021
	 * 
	 * This function will store the each json object into a file with the extension of .Json
	 * 
	 * @param jsonObject
	 * @param fileName
	 */
	public void createJsonFile(JSONObject jsonObject,String fileName) {
		 try {
	         FileWriter file = new FileWriter(jsonOutputFolderDir+fileName+".json");
	         file.write(jsonObject.toString());
	         file.close();
	         System.out.println("file created");
	      } catch (IOException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	}
	
	
}
