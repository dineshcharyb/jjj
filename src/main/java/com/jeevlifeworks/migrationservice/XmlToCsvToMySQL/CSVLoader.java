package com.jeevlifeworks.migrationservice.XmlToCsvToMySQL;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import au.com.bytecode.opencsv.CSVReader;

public class CSVLoader {
	
	private static final 
	String SQL_CREATE="CREATE TABLE ${table}(${columns})";
	
	private static final String COLUMNS_REGEX="\\$\\{columns\\}";
	
	private static final 
	String SQL_INSERT = "INSERT INTO ${table}(${keys}) VALUES(${values})";
	
    private static final String TABLE_REGEX = "\\$\\{table\\}";
    private static final String KEYS_REGEX = "\\$\\{keys\\}";
    private static final String VALUES_REGEX = "\\$\\{values\\}";


    private Connection connection;
    private char seprator;
    
    /**
	 * Public constructor to build CSVLoader object with
	 * Connection details. The connection is closed on success
	 * or failure.
	 * @param connection
	 */

public CSVLoader(Connection connection) {
	this.connection = connection;
	//Set default separator
	this.seprator = ',';
}

/**
 * Parse CSV file using OpenCSV library, create database table and load 
 * data into it 
 * @param csvFile Input CSV file
 * @param tableName, Database table name to create and import data
 * @param truncateBeforeLoad Truncate the table before inserting 
 * 			new records.
 * @throws Exception
 */

public void loadCSV(String csvFile, String tableName, boolean truncateBeforeLoad) throws Exception {

	CSVReader csvReader = null;
	if(null == this.connection) {
		throw new Exception("Not a valid connection.");
	}
	try {
		csvReader = new CSVReader(new FileReader(csvFile), this.seprator);

	} catch (Exception e) {
		e.printStackTrace();
		throw new Exception("Error occured while executing file. "
				+ e.getMessage());
	}
    //headerRow contains all columns of csv file
	String[] headerRow = csvReader.readNext();
    
	if (null == headerRow) {
		throw new FileNotFoundException(
				"No columns defined in given CSV file." +
				"Please check the CSV file format.");
	}
	
	//prepare a insert statement of SQL
	String questionmarks = StringUtils.repeat("?,", headerRow.length);
	questionmarks = (String) questionmarks.subSequence(0, questionmarks
			.length() - 1);

	String query = SQL_INSERT.replaceFirst(TABLE_REGEX, tableName);
	query = query
			.replaceFirst(KEYS_REGEX, StringUtils.join(headerRow, ","));
	query = query.replaceFirst(VALUES_REGEX, questionmarks);

	
	//modifiedHeaderRow also contains all columns of csv file
    String[] modifiedHeaderRow = headerRow;
	
	for(int column=0;column<headerRow.length;column++) {
		//modify headerRow to add datatype as longtext to each header to form a CREATE table statement
	    modifiedHeaderRow[column]=headerRow[column]+" "+"longtext";
	    
	}
	//complete query of Create table SQL statement
	String createquery1=SQL_CREATE.replaceFirst(TABLE_REGEX,tableName);
	createquery1=createquery1.replaceFirst(COLUMNS_REGEX,StringUtils.join(modifiedHeaderRow,","));
	
	System.out.println("CreateQuery: " + createquery1);
	
	System.out.println("InsertQuery: " + query);
	
	String[] nextLine;
	Connection con = null;
	PreparedStatement ps = null;
	PreparedStatement ps1=null;
	
	try {
		con = this.connection;
		con.setAutoCommit(false);
		ps1 = con.prepareStatement(createquery1);
		ps = con.prepareStatement(query);
        
		if(truncateBeforeLoad) {
			
			//delete data from table before loading csv
			//con.createStatement().execute("DELETE FROM " + tableName);
			
			//drop table if table already exists
		    con.createStatement().execute("DROP TABLE IF EXISTS "+ tableName);
		}
		System.out.println("create table");
		ps1.execute(createquery1);
		System.out.println("completed");
		
		final int batchSize = 1000;
		int count = 0;
		
		while ((nextLine = csvReader.readNext()) != null) {

			if (null != nextLine) {
				int index = 1;
				for (String string : nextLine) {
					ps.setString(index++, string);
					
				}
			}
			ps.addBatch();
			//System.out.println("adding to batch");
			if (++count % batchSize == 0) {
				System.out.println("executing batch");
				ps.executeBatch();
				ps.clearBatch();
			}

		}
		System.out.println("batch execution started");
		ps.executeBatch();     // insert remaining records
		ps.clearBatch(); 
		con.commit(); //commit statements to apply changes
		
		} catch (Exception e) {
		con.rollback();
		e.printStackTrace();
		throw new Exception(
				"Error occured while loading data from file to database."
						+ e.getMessage());
	} finally {
		if (null != ps)
			ps.close();
		if (null != con)
			con.close();

		csvReader.close();
	}
}

public char getSeprator() {
	return seprator;
}

public void setSeprator(char seprator) {
	this.seprator = seprator;
}

}

