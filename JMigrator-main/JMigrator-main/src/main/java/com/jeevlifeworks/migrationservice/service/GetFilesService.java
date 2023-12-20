package com.jeevlifeworks.migrationservice.service;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeevlifeworks.migrationservice.xmltagremover.XmlTagRemoverService;

@Service
public class GetFilesService {
	
	@Autowired
	XmlToJsonConverterService xmlToJsonConverterService;
	
	@Autowired
	XmlTagRemoverService xmlTagRemoverService;

	
	
	/**
	 * 
	 * @param folderDir
	 * @param action
	 * 
	 * Description :- here we are using this service as a common service 
	 * 				  we are taking action as parameter and according to that we are calling specific methods	
	 * @throws Exception 
	 */
	 public  void listFiles(String folderDir,String action) throws Exception
	    {
		 System.out.println(folderDir+"    inside service       "+action       );
		 
	        File folder = new File(folderDir);
	 System.out.println("222222");
	        //Creating files array
	        File[] files = folder.listFiles();
	        System.out.println("333333");       
	        System.out.println("File Size    "+files. length);
	 System.out.println("44444");
	        //Checking files 
	        for (File file : files)
	        {
	        	
	        	System.out.println("FileName      "+file.getName());
	        	// If the file  is xml file then  calling XmlConverter Service
	            if (file.isFile() && file.getName().endsWith(".xml"))
	            {
	            	try {
	            		
	            		System.out.println("Inside if try");
	            		
	            		
	            		/**
	            		 * using this if condition we are calling specific methods
	            		 */
	            		
	            		if(action.equals("XmlToJson")) {
	            			
	            		xmlToJsonConverterService.xmlToJsonConverter(file, file.getName());
	            		
	            		}else if(action.equals("XmlTagRemover")) {
	            		System.out.println("inside if");
	            		
	            			xmlTagRemoverService.xmlTagRemover(file);
	            	}
	            		
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	               
	            }
	            else if (file.isDirectory())
	            {System.out.println(file.getAbsolutePath());
	                listFiles(file.getAbsolutePath(),action);
	            }
	        }
	        /**
	         * Here if this service is calling XmlTagRemover Function then we need to write the Excel file 
	         * So here by using if condition and checking and calling the Write Excel Function
	         */
	        if(action.equals("XmlTagRemover")) {
    			
	        	xmlTagRemoverService.writeExcel();
        		
        		}
	        
	    }
	      
	    }



