package com.jeevlifeworks.migrationservice.XmlToCsvToMySQL;

import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.jeevlifeworks.migrationservice.ApplicationLevel;


/**
 * 
 * @author Abhishek Shetkar
 *Date 18-10-2020
 */
@Service
public class GetFilesService1 {
	
	@Autowired
	XmlToCsvService xmlToCsvService;
	@Autowired
	ApplicationLevel applicationLevel;
	@Autowired
    CsvToSqlService csvtosqlservice;	

    
	 public  void listFiles(String folderDir)
	    {
		 System.out.println(folderDir+"    inside service");
		 
	        File folder = new File(folderDir);
	 
	        //Creating files array
	        File[] files = folder.listFiles();
	        
	        System.out.println("File Size    "+files. length);
	
	        //Checking files 
	        for (File file : files)
	          {
	        	
	        	System.out.println("FileName      "+file.getName());
	        	// If the file  is XML file then  calling XmlToCsv Service
	            if (file.isFile() && file.getName().endsWith(".xml"))
	            {
	            	try {
	            		
	            		System.out.println("Inside if try");
						xmlToCsvService.xmltoCsv(file, file.getName());
	          	
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	               
	            }
	            else if (file.isDirectory())
	            {
	            	System.out.println(file.getAbsolutePath());
	            
	                listFiles(file.getAbsolutePath());
	            }
	        }
	      
	        
	    }
   
	 public void csvListFiles(String destFolder) {
			
			System.out.println(destFolder+"    inside service");
			 
	        File folder = new File(destFolder);
	 
	        //Creating files array
	        File[] files = folder.listFiles();
	        
	        System.out.println("File Size    "+files. length);

	        //Checking files 
	        for (File file : files)
	          {
	        	
	        	System.out.println("FileName      "+file.getName());
	        	// If the file  is csv file then  calling csvtosql Service
	            if (file.isFile() && file.getName().endsWith(".csv"))
	            {
	            	try {
	            		
	            		System.out.println("Inside if try");
	            		String filename=file.getName();
	            		//to make table name as filename so remove .csv from filename
	            		String newFileName=filename.replaceFirst(".csv","" );
						csvtosqlservice.csvToSql(file,newFileName);
	          	
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	               
	            }
	            else if (file.isDirectory())
	            {
	            	System.out.println(file.getAbsolutePath());
	            
	                listFiles(file.getAbsolutePath());
	            }
	        }
	      
	        
	    }
 
        
	 }

	
	

