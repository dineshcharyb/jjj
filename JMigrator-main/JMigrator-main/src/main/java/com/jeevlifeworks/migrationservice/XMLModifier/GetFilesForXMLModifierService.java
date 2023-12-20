package com.jeevlifeworks.migrationservice.XMLModifier;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetFilesForXMLModifierService {

	@Autowired
	XMLModifierService xmlmodifierservice;
	
	
	public File configFile;
	
	/**
	 * method to check whether configuration file exists in source folder or not 
	 * @param sourceDir
	 * @return boolean value
	 */
	public boolean ConfigurationFileCheck(String sourceDir)  {
		
		boolean configurationFileexists=false;
		
        File folder = new File(sourceDir);
 
        //Creating files array
        File[] files = folder.listFiles();
        
        System.out.println("File Size    "+files. length);
        
        for (File file : files)
          {
        	if(configurationFileexists==false){
        		
        	     System.out.println("FileName      "+file.getName());
        	     // If the file  is csv file then configurationFileexists=true
                 if (file.isFile() && file.getName().endsWith(".csv"))
                 {
            	      configurationFileexists = true;
            	      configFile = file.getAbsoluteFile();
            	
                 }
                 else if (file.isDirectory())
                 {
            	      System.out.println(file.getAbsolutePath());
            	
            	      configurationFileexists = ConfigurationFileCheck(file.getAbsolutePath());
                }
            
             }
          }
        return configurationFileexists;
		
	}
	
    //method to read all files in source folder and call xmlModifier service for each file
	public void ListFilesForXMLModifier(String sourceDir,String destinationDir) {
		
		System.out.println(sourceDir+"   inside service");
				
        File folder = new File(sourceDir);
 
        //Creating files array
        File[] files = folder.listFiles();
        
        System.out.println("File Size    "+files. length);	
        
        System.out.println("configuration file    "+configFile);
        
            for (File file : files)
            {
          	
          	    System.out.println("FileName      "+file.getName());
          	    
          	    // If the file is xml file then calling XmlModifier Service
                if (file.isFile() && file.getName().endsWith(".xml"))
                {
              		try {
              			System.out.println("Inside if try");
                  		xmlmodifierservice.xmlEditorWithMultiFunction(file,configFile,destinationDir);
              		}catch(Exception e) {
              			e.printStackTrace();
              		}
              	
              
              }
              else if (file.isDirectory())
              {
              	System.out.println(file.getAbsolutePath());
              
              	ListFilesForXMLModifier(file.getAbsolutePath(),destinationDir);
              }
              
            }
	
    }
}
