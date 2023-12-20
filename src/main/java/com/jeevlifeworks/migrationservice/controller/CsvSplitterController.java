package com.jeevlifeworks.migrationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeevlifeworks.migrationservice.service.CsvSplitterService;

@RestController
@RequestMapping("/api")
public class CsvSplitterController {
	
	@Autowired
	CsvSplitterService csvSplitterService;
	
	@RequestMapping("/csvSplitter/{fileName}/{fileSize}")
	public String csvSplitter(@PathVariable String fileName, @PathVariable int fileSize) {
		System.out.println(fileName+"   "+fileSize);
		return csvSplitterService.csvSplitter(fileName,fileSize);
		
	}

}
