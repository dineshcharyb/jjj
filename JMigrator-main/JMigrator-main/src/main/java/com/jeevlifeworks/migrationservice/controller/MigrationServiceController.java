package com.jeevlifeworks.migrationservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeevlifeworks.migrationservice.entity.AuthModel;
import com.jeevlifeworks.migrationservice.service.MigrationService;

@RestController
@RequestMapping("/casemigration")
public class MigrationServiceController {
	
	@Autowired
	MigrationService migrationService;
	
	HttpHeaders headers = new HttpHeaders();
	
	@GetMapping("/execute")
	public String executeCaseMigration(List<String[]> allRows, String csvFilePath) {
		String authResponse = migrationService.getAuthToken();
		ObjectMapper mapper = new ObjectMapper();
		AuthModel authModel = new AuthModel();
		String caseResponse = "";
		try {
			authModel = mapper.readValue(authResponse, AuthModel.class);
//			migrationService.uploadE2B(authModel.getSessionId());
			caseResponse = migrationService.createCaseFromDocument(authModel.getSessionId(),allRows,csvFilePath);

		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		return new ResponseEntity<AuthModel>(authModel, headers, HttpStatus.OK);
		return caseResponse;
	}
	
	
	//ResponseEntity<AuthModel>
	@GetMapping("/createAndInitiate")
	public String createSingleDocumentAndInitiateUserAction() {
		System.out.println("Inside Controller");
		String authResponse = migrationService.getAuthToken();
		System.out.println("Response    "+authResponse);
		ObjectMapper mapper = new ObjectMapper();
		AuthModel authModel = new AuthModel();
		System.out.println("Before try");
		try {
			authModel = mapper.readValue(authResponse, AuthModel.class);
			System.out.println("before callin service");
			migrationService.getFiles(authModel.getSessionId());
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
		//return new ResponseEntity<AuthModel>(authModel, headers, HttpStatus.OK);
	}
}
