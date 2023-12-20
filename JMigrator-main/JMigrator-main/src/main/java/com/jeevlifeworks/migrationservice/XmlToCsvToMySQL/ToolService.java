package com.jeevlifeworks.migrationservice.XmlToCsvToMySQL;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.jeevlifeworks.migrationservice.XmlToCsvToMySQL.ToolsCatalogue;


@Service
public class ToolService {
	@Autowired
	ToolRepository toolrepo;
	
	public List<ToolsCatalogue> getCatalogueinfo() {
		List<ToolsCatalogue> obj=toolrepo.findAll();
		return obj;
	}
}
