package com.jeevlifeworks.migrationservice.XmlToCsvToMySQL;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.jeevlifeworks.migrationservice.XmlToCsvToMySQL.ToolsCatalogue;

@RestController

@RequestMapping("${baseurl}")
public class ToolAccessController {

	@Autowired
	ToolService toolservice;
	
	//get request to get information about migration tool
	@GetMapping("${toolinformationurl}")
	   public List<ToolsCatalogue> gettoolinfo() {
		   return toolservice.getCatalogueinfo();
	   }
	
}
