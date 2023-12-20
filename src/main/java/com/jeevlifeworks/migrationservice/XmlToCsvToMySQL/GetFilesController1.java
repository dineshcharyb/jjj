package com.jeevlifeworks.migrationservice.XmlToCsvToMySQL;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jeevlifeworks.migrationservice.ApplicationLevel;


/**
 * 
 * @author Abhishek Shetkar
 * Date 18-10-2020
 *
 */

@RestController
@RequestMapping("${baseurl}")
public class GetFilesController1 {
	
	@Autowired
	GetFilesService1 getFilesService;
	
	@Autowired
	XmlToCsvService xmlToCsvService;
	
	@Autowired
	ApplicationLevel applicationLevel;
	
	@Autowired
	LogService logservice;
	
	/**
	    * request is of type post which consumes and produces JSON value
	    * method is to convert XMLToCSVToSQL
	    * @param xmlToCsvDTO
	    * @return response string in JSON format
	    * @throws JsonMappingException
	    * @throws JsonProcessingException
	    * @throws JSONException
	    * @throws InterruptedException
	    */
//	@PostMapping(value="${xmltocsvtosqlurl}",
//			  consumes={MediaType.APPLICATION_JSON_VALUE},
//			  produces = {MediaType.APPLICATION_JSON_VALUE})
//
//	public ResponseData getFiles(@RequestBody XmlToCsvDTO xmlToCsvDTO ) throws JsonMappingException, JsonProcessingException, JSONException, InterruptedException {
//	  boolean sourceExists=FolderCheck.FolderExistsCheck(xmlToCsvDTO.getSourceFolder());
//	  boolean destExists=FolderCheck.FolderExistsCheck(xmlToCsvDTO.getDestFolder());
//
//	  int toolId=xmlToCsvDTO.getToolCatalogueId();
//
//	  //check for proper tool selection
//	  if(toolId!=1) {
//		  return new ResponseData(ConstantString.ToolIdError[0]);
//	  }
//	  //check for file existence
//	  if(sourceExists==false) {
//		  return new ResponseData(ConstantString.sourceError);
//	  }
//	  else if(destExists==false) {
//		  return new ResponseData(ConstantString.destinationError);
//	  }
//	  else {
//
//		 //thread to run the service in background without interrupting the main
//		 Thread thread1=new Thread() {
//			 public void run() {
//			 try{
//				 SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//
//				 /**
//				  * insert the data about the service call into the database
//				  */
////				      logservice.insertServiceLogInformation(toolId);
//
//				  Date date = new Date();
//				  String startdate=format.format(date);
//
//				 /**
//				  * call a service to update the start time of processing,status of service called for a particular toolId and activityName
//				  */
//
//				      logservice.updateServiceLogInformation(startdate,ConstantString.activityName[0],ConstantString.status[1],ConstantString.beforeOrAfter[0],toolId);
//
//				 /**
//				  * service is called to convert XML To CSV files
//				  */
//				       getFilesService.listFiles(xmlToCsvDTO.getSourceFolder());
//				       xmlToCsvService.writeCsv1(applicationLevel.jsonArrayData,xmlToCsvDTO.getDestFolder());
//
//				  Date date1 = new Date();
//				  String enddate=format.format(date1);
//
//				 /**
//				  * call a service to update the end time of processing,status of service called for a particular toolId and activityName
//				  */
//				      logservice.updateServiceLogInformation(enddate,ConstantString.activityName[0],ConstantString.status[2],ConstantString.beforeOrAfter[1],toolId);
//
//
//                  Date date2=new Date();
//                  String startdate1=format.format(date2);
//
//                  /**
// 				  * call a service to update the start time of processing,status of service called for a particular toolId and activityName
// 				  */
//                  logservice.updateServiceLogInformation(startdate1,ConstantString.activityName[1],ConstantString.status[1],ConstantString.beforeOrAfter[0],toolId);
//
//                 /**
//                  * service is called to load CSV files into SQL
//                  */
//				  getFilesService.csvListFiles(xmlToCsvDTO.getDestFolder());
//
//
//				  Date date3=new Date();
//                  String enddate1=format.format(date3);
//
//                  /**
// 				  * call a service to update the end time of processing,status of service called for a particular toolId and activityName
// 				  */
//                  logservice.updateServiceLogInformation(enddate1,ConstantString.activityName[1],ConstantString.status[2],ConstantString.beforeOrAfter[1],toolId);
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
//	  }
//    }
	/**
	 * 
	 * @param id
	 * @return last record of loggingInformation for particular tool id 
	 */
	@GetMapping("${toolserviceloginformationurl}")
	public List<LogInfo> getServiceLogInfoByToolId(@PathVariable("id")int id) {
		return logservice.logInformationByToolId(id);
	}
}
