package com.jeevlifeworks.migrationservice.service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

@Service
public class CsvSplitterService {
	
	@Value("${FilePathForCsvSplitter}")
	private String csvFilePath;

	public String csvSplitter(String fileName, int fileSize) {
	
		
		try {
			
			 //Build reader instance
			CSVReader reader = new CSVReader(new FileReader(csvFilePath+fileName), ',', '"', 0);

			// Read all rows at once
			List<String[]> allRows = reader.readAll();
			System.out.println(allRows.size());
			
			// creating list to add in each csv file
			List<String[]> rows=new ArrayList<String[]>();
			
			// this variable is used to check recordsize and make files
			int count=1;
			
			//this variable is used to add concat a number with filename 
			int fileCount=1;
			
			// here the loop started from 1 to allRows.size()-1 because in thal allRows the first row is Header 
			for(int i=1;i<=allRows.size()-1;i++) {
				
				//here getting each row and adding it to the list
				String[] record=allRows.get(i);
				
				rows.add(record);
				
				//here checking if the count =+fileSize then creating file
				if(count==fileSize || i==allRows.size()-1) {
					//adding header to each file
					rows.add(0,allRows.get(0));
					
					CSVWriter writer = null;
					 
					writer = new CSVWriter(new FileWriter(csvFilePath+"abc_"+fileCount+".csv"));
					writer.writeAll(rows);
					writer.close();
					count=0;
					fileCount++;
					rows.clear();

				}
				count++;
				
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	return "Success";	
	}

}
