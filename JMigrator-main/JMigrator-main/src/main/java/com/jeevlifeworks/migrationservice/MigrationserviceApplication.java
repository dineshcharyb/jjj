package com.jeevlifeworks.migrationservice;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jeevlifeworks.migrationservice.controller.GetFilesController;
import com.jeevlifeworks.migrationservice.controller.MigrationServiceController;

import au.com.bytecode.opencsv.CSVReader;

@SpringBootApplication
@EnableScheduling
public class MigrationserviceApplication implements CommandLineRunner {

	@Value("${FilePathForCSV}")
	private String csvFilePath;

	@Value("${action}")
	private String action;
	
	@Autowired
	MigrationServiceController migrationServiceController;
	
	@Autowired
	GetFilesController getFilesController;

	public static void main(String[] args) {
		SpringApplication.run(MigrationserviceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		/**
		 * here we are taking action value from Application.properties
		 * 
		 * if action value is XmlToJson then we are calling get Files function from GetFilesController
		 * Which will take the Folder Directory and filter all xml files from that folder and its subfolder
		 * then it will convert the xml files to json files
		 * 
		 * if action value is CreateCaseFromDocument
		 * then we will parse the CSV file then storing asll the records into list
		 * and calling executeCaseMigration of from MigrationServiceController
		 * 
		 */
		
		if(action.equals("XmlToJson")) {
			System.out.println("Inside if");
			System.out.println();
			getFilesController.getFiles(action);
			
		}else if(action.equals("CreateCaseFromDocument")) {
			
			System.out.println("Inside else if");
			File file = new File(csvFilePath);
			System.out.println("File name   " + file.getName());

			boolean flag = true;
			while (flag) {
				
				// Build reader instance
				CSVReader reader = new CSVReader(new FileReader(csvFilePath), ',', '"', 0);

				// Read all rows at once
				List<String[]> allRows = reader.readAll();

				String output = migrationServiceController.executeCaseMigration(allRows, csvFilePath);
				if (output.equals("Success")) {
					System.out.println("ouytput    " + output);
					flag = false;
				}
				{
					System.out.println("ouytput    " + output);
					flag = true;
				}
			}
		}
	}
		@Bean
		public WebMvcConfigurer corsConfigurer(){
		return new WebMvcConfigurer(){
		@Override
		public void addCorsMappings(CorsRegistry registry){
		registry.addMapping("/**").allowedOrigins("*"). allowedMethods("*").allowedHeaders("*");
		}
		};
		


	}

}
