package com.jeevlifeworks.migrationservice.datafetchscheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeevlifeworks.migrationservice.ApplicationLevel;
import com.jeevlifeworks.migrationservice.entity.AuthModel;
import com.jeevlifeworks.migrationservice.service.MigrationService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class DataFetchSchedularService {
	
	@Value("${queryForDataFetchSchedular}")
	private String query;
	
	@Autowired
	MigrationService migratService;
	
	@Autowired
	ApplicationLevel applicationLevel;

	/**
	 * Step 1 
	 * User will login and we will get sessionid 
	 * @return
	 */
	public AuthModel getLoginSessionId() {
		String authResponse=migratService.getAuthToken();
		
		// Here Mapping authResponse to AuthModel
		
		 ObjectMapper mapper = new ObjectMapper();
			AuthModel authModel = new AuthModel();
			try {
				authModel = mapper.readValue(authResponse, AuthModel.class);
				
				applicationLevel.applicationLevelMap.put("SessionId", authModel.getSessionId());
			} catch (JsonProcessingException e) {
				
				e.printStackTrace();
			}
		return authModel;
	}
	
	/**
	 * Step 2
	 * Schedular service to fetch data from VEEVA database
	 */
	
	@Scheduled(fixedDelay = 5000)
	public String fetchdata() {
		
		System.out.println("Function is running");
		
	
		// here we are calling getloginSessionId function to get the SessionId
	//	AuthModel authModel=this.getLoginSessionId();
		
		
		//String query="select id,name__v from documents";
		Unirest.setTimeouts(0, 0);
		HttpResponse<String> response=null;
		try {
			 response = Unirest.post("https://partnersi-jeevlifeworks-safety.veevavault.com/api/v20.2/query")
			  .header("Accept", "application/json")
			  .header("X-VaultAPI-DescribeQuery", "true")
			  .header("Authorization", applicationLevel.applicationLevelMap.get("SessionId"))
			  .header("Content-Type", "application/x-www-form-urlencoded")
			  .field("q", query).asString();
		} catch (UnirestException e) {
			System.out.println("Inside Catch block");
			e.printStackTrace();
		}
		
		if(response.getBody().toString().contains("Invalid or expired session ID")) {
			System.out.println("Inside If");
			this.getLoginSessionId();
		}
System.out.println("response     "+response.getBody().toString());
		
		return null;
	}
}
