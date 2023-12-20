package com.jeevlifeworks.migrationservice.XmlToCsvToMySQL;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class CsvToSqlService {
	
	public void csvToSql(File file, String name) {
		/**
		 * get the connection, and pass the csvfile,table name and 
		 * set truncateBeforeLoad to true,
		 * to truncate the table before creating and inserting
		 */

		try {

			CSVLoader loader = new CSVLoader(getCon());
			
			loader.loadCSV(file.toString(), name, true);
			
		} catch (Exception e) {
			e.printStackTrace();
		
	 }
	}
	/**
	 * method to get connection to SQL server
	 * @return JDBC connection
	 */
		private static Connection getCon() {
			Connection connection = null;
			ResultSet rs=null;
			String driver = RDSConnection.driver;
	        String url = RDSConnection.url;
	        String user = RDSConnection.user;
	        String pass = RDSConnection.pass;
            String dbname=RDSConnection.dbname;
			try {
				Class.forName(driver);
				connection = DriverManager.getConnection(url,user,pass);
				rs=connection.getMetaData().getCatalogs();
				boolean databaseexists=false;
				while(rs.next()) {
					String catalogs=rs.getString(1);
					//check if database exists or not
				    if(dbname.equals(catalogs)) {
				    	databaseexists=true;
						System.out.println("database exists"+dbname);

						url=url+dbname+"?&rewriteBatchedStatements=true";
						connection=DriverManager.getConnection(url,user,pass);
					}
				}
				if(databaseexists==false){
				     //if database not exists then create a database 
					  final String SQL_DATABASE="CREATE DATABASE IF NOT EXISTS ${database}";
					  final String DATABASE_REGEX = "\\$\\{database\\}";
					  String query = SQL_DATABASE.replaceFirst(DATABASE_REGEX, dbname);
					  System.out.println("create database query"+query);
					  
					  PreparedStatement ps = null;
					  ps = connection.prepareStatement(query);
					  ps.execute(query);  
					  url=url+dbname+"?&rewriteBatchedStatements=true";
					  connection=DriverManager.getConnection(url,user,pass);
				    }
				    
				
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return connection;
		}
  
}
