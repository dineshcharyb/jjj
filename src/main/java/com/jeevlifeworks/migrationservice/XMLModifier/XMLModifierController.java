package com.jeevlifeworks.migrationservice.XMLModifier;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jeevlifeworks.migrationservice.XmlToCsvToMySQL.ConstantString;
import com.jeevlifeworks.migrationservice.XmlToCsvToMySQL.FolderCheck;
import com.jeevlifeworks.migrationservice.XmlToCsvToMySQL.LogService;
import com.jeevlifeworks.migrationservice.XmlToCsvToMySQL.ResponseData;
import com.jeevlifeworks.migrationservice.XmlToCsvToMySQL.XmlToCsvDTO;

@RestController
@RequestMapping("${baseurl}")
public class XMLModifierController {

	@Autowired
	GetFilesForXMLModifierService GetFilesForXMLModifierService;

	@Autowired
	LogService logservice;

//	@PostMapping(value = "${xmlmodifierurl}",
//			consumes = {MediaType.APPLICATION_JSON_VALUE},
//			produces = {MediaType.APPLICATION_JSON_VALUE})

//	public ResponseData getFiles(@RequestBody XmlToCsvDTO xmlToCsvDTO ) throws JsonMappingException, JsonProcessingException, JSONException, InterruptedException {
//	  boolean sourceExists=FolderCheck.FolderExistsCheck(xmlToCsvDTO.getSourceFolder());
//	  boolean destExists=FolderCheck.FolderExistsCheck(xmlToCsvDTO.getDestFolder());
//	  int toolId=xmlToCsvDTO.getToolCatalogueId();
//
//	  //check for proper tool selection
//	  if(toolId!=2) {
//		  return new ResponseData(ConstantString.ToolIdError[1]);
//	  }
//	  //check for file existence
//	  else if(sourceExists==false) {
//		  return new ResponseData(ConstantString.sourceError);
//	  }
//	  else if(destExists==false) {
//		  return new ResponseData(ConstantString.destinationError);
//
//	  }
//	  else {
//		  /**
//		   * service is called to check whether configuration file is present or not
//		   */
//		  boolean configurationfileExists=GetFilesForXMLModifierService.ConfigurationFileCheck(xmlToCsvDTO.getSourceFolder());
//		  System.out.println("config file"+configurationfileExists);
//
//		  if(configurationfileExists==false) {
//
//			 return new ResponseData(ConstantString.configurationFileError);
//
//		  }else {
//
//		       //thread to run the service in background without interrupting the main
//		       Thread thread1=new Thread() {
//			   public void run() {
//			   try{
//
//				 SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//
//				 /**
//				  * insert the data about the service call into the database
//				  */
//
//				 logservice.insertServiceLogInformation(toolId);
//
//				  Date date = new Date();
//				  String startdate=format.format(date);
//
//				 /**
//				  * call a service to update the start time of processing,status of service called for a particular toolId and activityName
//				  */
//
//				  logservice.updateServiceLogInformation(startdate,ConstantString.activityName[2],ConstantString.status[1],ConstantString.beforeOrAfter[0],toolId);
//
//				 /**
//				  * service is called to modify XML files
//				  */
//
//				  GetFilesForXMLModifierService.ListFilesForXMLModifier(xmlToCsvDTO.getSourceFolder(),xmlToCsvDTO.getDestFolder());
//
//				  Date date1 = new Date();
//				  String enddate=format.format(date1);
//
//				 /**
//				  * call a service to update the end time of processing,status of service called for a particular toolId and activityName
//				  */
//				  logservice.updateServiceLogInformation(enddate,ConstantString.activityName[2],ConstantString.status[2],ConstantString.beforeOrAfter[1],toolId);
//
//
//
//
//			 }catch(Exception e){
//				 e.printStackTrace();
//			 }
//			 }
//
//		 };
//		 thread1.start();
//
//		//main thread will give immediate response to client
//	    return new ResponseData(ConstantString.initiatedServiceMsg);
//		  }
//	  }
//  }
}
