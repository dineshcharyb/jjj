package com.jeevlifeworks.migrationservice.XmlToCsvToMySQL;

import java.io.File;

public class FolderCheck {
	//here we check whether source folder/destination folder exists or not
		public static boolean FolderExistsCheck(String sourceFolder) {
			boolean folderExists=false;
			 File folder = new File(sourceFolder);
			
			 if(folder.exists()) {
				 folderExists=true;
			 }
			return folderExists;
			
		}
}
