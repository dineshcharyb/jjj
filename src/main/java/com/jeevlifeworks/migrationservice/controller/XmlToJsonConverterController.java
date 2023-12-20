package com.jeevlifeworks.migrationservice.controller;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jeevlifeworks.migrationservice.service.XmlToJsonConverterService;

@RestController
public class XmlToJsonConverterController {
	
	@Autowired
	XmlToJsonConverterService xmlToJsonConverterService;
	
	@Autowired
	GetFilesController getFilesController;

	/**
	 * 
	 * here calling getfiles controller because from get files controller it will goto
	 * getfilesService and in that it will check allx,ml files from that folder and subfolder
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String xmlToJsonConverter() throws Exception {
//		return xmlToJsonConverterService.xmlToJsonConverter();
		return getFilesController.getFiles("XmlToJson");
	}

}
