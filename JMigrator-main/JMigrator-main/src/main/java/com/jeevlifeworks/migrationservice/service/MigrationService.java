package com.jeevlifeworks.migrationservice.service;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.jeevlifeworks.migrationservice.entity.AuthModel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import au.com.bytecode.opencsv.CSVWriter;

@Service
public class MigrationService {
	
	private static final Logger log = LoggerFactory.getLogger(MigrationService.class);
	
	@Value("${auth.endpoint}")
	private String authUrl;
	
	@Value("${e2b.endpoint}")
	private String e2bUrl;

	@Value("${useraction.endpoint}")
	private String useractionUrl;

	@Value("${safety.directroy}")
	private String e2bPath;
	
	@Value("${safety.filepattern}")
	private String filePattern;
	
	@Value("${createSingleDocument.endPoint}")
	private String createSingleDocumentUrl;
	
	@Value("${filepath1}")
	private String filePath;
	
	@Autowired 
	CreateSingleDocumentAndInitiateUserActionService createSingleDocumentAndInitiateUserActionService;
	
	@Cacheable(value = "AuthKey")
	public String getAuthToken() {
		log.info("Starting authToken call");
		Unirest.setTimeouts(0, 0);
		HttpResponse<String> response = null;
		try {
			response = Unirest.post(authUrl)
			  .header("Content-Type", "application/x-www-form-urlencoded")
			  .header("Accept", "application/json")
			  .asString();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		response = response != null?response: new HttpResponse<String>(null, null);
		log.info("Completed authToken call : "+response.getBody().toString());
		return response.getBody().toString();
	}
	
	public String uploadE2B(String sessionId) {
		log.info("Starting E2B upload");
		File dir = new File(e2bPath);
		FileFilter fileFilter = new WildcardFileFilter(filePattern);
		File[] files = dir.listFiles(fileFilter);
		Set<String> responseSet = new HashSet<String>();
		Unirest.setTimeouts(0, 0);
		HttpResponse<String> response = null;
		for (int i = 0; i < files.length; i++) {
			try {
				response = Unirest.post(e2bUrl)
				  .header("Authorization", sessionId)
				  .header("Content-Type", "multipart/form-data; boundary=calculatedwhenrequestissent")
				  .field("file", new File(files[i].getPath()))
				  .field("originId", "SND")
				  .field("destinationId", "RCV")
				  .asString();
			} catch (UnirestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			responseSet.add(response.getBody().toString());
		}
		log.info("Completed E2B upload");
		return responseSet.toString();
	}

	public String createCaseFromDocument(String sessionId, List<String[]> allRows, String csvFilePath) {
		log.info("Starting Case Creation");
		Set<String> responseSet = new HashSet<String>();
		Unirest.setTimeouts(0, 0);
		HttpResponse<String> response = null;
		
		//fetch starting time
		long startTime = System.currentTimeMillis(); 
		
		// get first Record
		String[] firstRecord=null;
		// get last Record
		String[] lastRecord=null;
		
		
		int number=1;
		
		//using this count checking how many status are already updated in csv file
		int number1=0;
		  for(int i=1;i<allRows.size();i++){
			  number=i;
			  number1++;
			if(  Arrays.toString(allRows.get(i)).split(",")[16].equals(" ]")){
				firstRecord=Arrays.toString(allRows.get(i)).split(",");
				
				break;
			  }
			
		  }
		  
		  // these 2 variables are using for FOR LOOP
		  int first=number1;
		  int last=allRows.size()-1;

		// get last Record
		lastRecord=Arrays.toString(allRows.get(allRows.size()-1)).split(","); 
		 
		String firstId=firstRecord[1].toString();
		String lastId=lastRecord[1].toString();
		int fId= Integer.parseInt(firstId.trim());
		int lId= Integer.parseInt(lastId.trim());
		
		
		for (int i = first; i <=last; i++) {
			
			// Here Using the i value taking the DocumentId
			String  idRecord[]=Arrays.toString(allRows.get(i)).split(",");
			int id=Integer.parseInt(idRecord[1].toString().trim());
			
			try {

				response = Unirest.put("https://sb-arriello-safety.veevavault.com/api/v20.2/objects/documents/"+id+"/versions/0/1/lifecycle_actions/sdkDocLifecycleUA54cmhq95w96r__c")
						.header("Authorization", sessionId)
						.header("Content-Type", "application/x-www-form-urlencoded")
						.header("Accept", "application/json")
						.body("")
						.asString();
//		jeevsb		sdkDocLifecycleUA11kxbtfnjgoc0__c
//	arriellosb			sdkDocLifecycleUA54cmhq95w96r__c

				String[] record=Arrays.toString(allRows.get(number)).split(",");
				
				System.out.println(response.getBody().toString());
				if(response.getBody().toString().contains("responseStatus:SUCCESS")) {
					log.info("Response Success");
					record[16]="Completed";
					allRows.remove(number);
					allRows.add(number, record);
				}else {
					log.info("Response Failed");
					record[16]="Failed";
					allRows.remove(number);
					allRows.add(number, record);
					
					
					
				}
			} catch (UnirestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			responseSet.add(response.getBody().toString());
			
			// variable declaration for Terminating the loop
			boolean flag=false;
			String executionStatus=null;
			if(i==last) {
				flag=true;
				executionStatus="Success";
			}else{
				executionStatus="Incomplete";
			}
			
			/**
			 * Here after sometime terminating the loop
			 */
			
			
			if(System.currentTimeMillis()-startTime>100000||flag) {
				
				System.out.println("Writing CSV");
				 CSVWriter writer = null;
				try {
					writer = new CSVWriter(new FileWriter(csvFilePath));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			        
			    
			      //Write the record to file
			      writer.writeAll(allRows);
			        
			      //close the writer
			      try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			      return executionStatus;
				//System.exit(0);
			}
			number++;
		}
		log.info("Completed USer Action");
		return responseSet.toString();
	}
	
	
	public String getFiles(String sessionId) {
		log.info("Inside get Files Function");
		File dir = new File(filePath);
		FileFilter fileFilter = new WildcardFileFilter(filePattern);
		File[] files = dir.listFiles(fileFilter);
	
		Unirest.setTimeouts(0, 0);
		
		for (int i = 0; i < files.length; i++) {
			log.info("Inside For loop");
			
			String fileName=files[i].getName();
			String caseNumber=fileName.split("-")[1].replace(".xml","");
			
			
			createSingleDocumentAndInitiateUserActionService.createSingleDocumentAndInitiateUserAction(files[i],sessionId,caseNumber);
			
			
		}
		log.info("Completed  upload");
		return "success";

}
}