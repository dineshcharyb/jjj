package com.jeevlifeworks.migrationservice.XMLSplitter;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetFilesForXMLSplitterService {
    
	@Autowired
	XMLSplitterService xmlSplitterService;
	
	//method which reads each file in sourceDirectory and call XMLSplitter service
	public void ListFilesForXMLSplitter(String sourceDir,String destinationDir) {
		System.out.println(sourceDir+"   inside service");
				
        File folder = new File(sourceDir);
 
        //Creating files array
        File[] files = folder.listFiles();
        
        System.out.println("File Size    "+files. length);	
                
            for (File file : files)
            {
          	    System.out.println("FileName      "+file.getName());
          	    String fileName=file.getName();
          	    // If the file is xml file then calling XmlSplitter Service
                if (file.isFile() && file.getName().endsWith(".xml"))
                {
              		try {
              			
              			System.out.println("Inside if try");
//              			xmlSplitterService.readXml1(file,fileName,destinationDir);
              			
              		}catch(Exception e) {
              			e.printStackTrace();
              		}
              	
              }
              else if (file.isDirectory())
              {
              	System.out.println(file.getAbsolutePath());
              
              	ListFilesForXMLSplitter(file.getAbsolutePath(),destinationDir);
              }
              
            }
	
    }
}
