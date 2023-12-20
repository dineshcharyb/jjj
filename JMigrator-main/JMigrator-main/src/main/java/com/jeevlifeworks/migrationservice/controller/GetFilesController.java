package com.jeevlifeworks.migrationservice.controller;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jeevlifeworks.migrationservice.service.GetFilesService;

@RestController
public class GetFilesController {
	
	@Autowired
	GetFilesService getFilesService;
	

	@Value("${folderPathName}")
	String folderDir;
	
	@GetMapping("/getFiles")
	public String getFiles(String action) throws Exception {
		
	getFilesService.listFiles(folderDir,action);
		return "Success";
	}


}
