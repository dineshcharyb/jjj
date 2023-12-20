package com.jeevlifeworks.migrationservice.XMLSplitter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jeevlifeworks.migrationservice.XmlToCsvToMySQL.ConstantString;
import com.jeevlifeworks.migrationservice.XmlToCsvToMySQL.FolderCheck;
import com.jeevlifeworks.migrationservice.XmlToCsvToMySQL.LogService;
import com.jeevlifeworks.migrationservice.XmlToCsvToMySQL.ResponseData;
import com.jeevlifeworks.migrationservice.XmlToCsvToMySQL.XmlToCsvDTO;

@RestController
@RequestMapping("${baseurl}")
public class XMLSplitterController {

	@Autowired
	GetFilesForXMLSplitterService getFilesForXMLSplitterService;
	
	@Autowired
	LogService logservice;
	
//	@PostMapping(value="${xmlsplitterurl}",
//			  consumes={MediaType.APPLICATION_JSON_VALUE},
//			  produces = {MediaType.APPLICATION_JSON_VALUE})
//
//	public ResponseData getFiles(@RequestBody XmlToCsvDTO xmlToCsvDTO ) throws JsonMappingException, JsonProcessingException, JSONException, InterruptedException {
//	  boolean sourceExists=FolderCheck.FolderExistsCheck(xmlToCsvDTO.getSourceFolder());
//	  boolean destExists=FolderCheck.FolderExistsCheck(xmlToCsvDTO.getDestFolder());
//	  int toolId=xmlToCsvDTO.getToolCatalogueId();
//
//	  //check for proper tool selection
//	  if(toolId!=3) {
//		  return new ResponseData(ConstantString.ToolIdError[2]);
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
//
//		       //thread to run the service in background without interrupting the main
//		       Thread thread1=new Thread() {
//			   public void run() {
//			   try{
//
//				 SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//
//				 /**
//				  * insert the data about the service calling in database
//				  */
//
////				 logservice.insertServiceLogInformation(toolId);
//
//				  Date date = new Date();
//				  String startdate=format.format(date);
//
//				 /**
//				  * call a service to update the start time of processing,status of service called for a particular toolId and activityName
//				  */
//
//				  logservice.updateServiceLogInformation(startdate,ConstantString.activityName[3],ConstantString.status[1],ConstantString.beforeOrAfter[0],toolId);
//
//				 /**
//				  * service is called to Split XML files
//				  */
//
//				  getFilesForXMLSplitterService.ListFilesForXMLSplitter(xmlToCsvDTO.getSourceFolder(),xmlToCsvDTO.getDestFolder());
//
//				  Date date1 = new Date();
//				  String enddate=format.format(date1);
//
//				 /**
//				  * call a service to update the end time of processing,status of service called for a particular toolId and activityName
//				  */
//				  logservice.updateServiceLogInformation(enddate,ConstantString.activityName[3],ConstantString.status[2],ConstantString.beforeOrAfter[1],toolId);
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
}
	
	


