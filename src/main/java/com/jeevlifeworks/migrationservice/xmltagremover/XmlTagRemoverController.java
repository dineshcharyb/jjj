package com.jeevlifeworks.migrationservice.xmltagremover;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jeevlifeworks.migrationservice.controller.GetFilesController;

@RestController
public class XmlTagRemoverController {
	
	@Autowired
	GetFilesController getFilesController;
	
	
	public void xmlTagRemover() throws Exception {
		getFilesController.getFiles("XmlTagRemover");
	}

}
