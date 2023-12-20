package com.jeevlifeworks.migrationservice.service;

import java.io.File;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class CreateSingleDocumentAndInitiateUserActionService {
	
	private static final Logger log = LoggerFactory.getLogger(CreateSingleDocumentAndInitiateUserActionService.class);

	@Value("${safety.filepattern}")
	private String filePattern;
	
	@Value("${createSingleDocument.endPoint}")
	private String createSingleDocumentUrl;
	
	@Value("${filepath1}")
	private String filePath;
	
	
	@Async
	public CompletableFuture<String> createSingleDocumentAndInitiateUserAction(File file,String sessionId,String caseNumber) {
	
		 long start = System.currentTimeMillis();
		log.info("Inside createSingleDocumentAndInitiateUserAction");
		HttpResponse<String> response_DocumentId = null;
		HttpResponse<String> responseOfInitiateUserAction=null;
		try {
			log.info("Inside try block");
			response_DocumentId=Unirest.post(createSingleDocumentUrl)
					.header("Authorization", sessionId)
					  .header("Content-Type", "application/x-www-form-urlencoded")
					  .header("Accept", "application/json")
					  .field("file", new File(file.getPath()))
					  .field("type__v","Case")
					  .field("subtype__v","Source")
					  .field("classification__v","Adverse Event Report")
					  .field("lifecycle__v","AER Lifecycle")
					  .field("major_version_number__v","0")
					  .field("minor_version_number__v","1")
					  .field("name__v",caseNumber)
					  .asString();
			log.info("Thread Name      "+Thread.currentThread().getName());
log.info("response id        "+response_DocumentId.getBody());

				
responseOfInitiateUserAction= Unirest.put("https:// partnersi-jeevlifeworks-safety.veevavault.com/api/v20.2/objects/documents/132/versions/0/1/lifecycle_actions/sdkDocLifecycleUA11kxbtfnjgoc0__c")
						  .header("Authorization", "F77AFAB2A1ABE1CB0DA95A079D24878E0AA12CE6E576A25060DB31BBDAD0A21978A927C496DF920ECBB6624CF750581F9168193C7F72C920F9E54118197E88BF")
						  .header("Content-Type", "application/x-www-form-urlencoded")
						  .header("Accept", "application/json")
						  .asString();





			log.info("response of initiate user action        "+responseOfInitiateUserAction.getBody());
					
			
			
		}catch(UnirestException e) {
			e.printStackTrace();		
			}
		
		return CompletableFuture.completedFuture("Success");
		
	}

}
