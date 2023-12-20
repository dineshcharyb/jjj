package com.jeevlifeworks.migrationservice.XmlToCsvToMySQL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class RDSConnection {
 
	public static String driver;
    public static String url;
    public static String user;
    public static String pass;
    public static String dbname;
    @Autowired
    public RDSConnection getRDSConnection(@Value("${jdbc_driver}") String driver,
    		                              @Value("${jdbc_connection_URL}") String url,
                                          @Value("${connection.username}") String user,
                                          @Value("${connection.password}") String pass,
                                          @Value("${databasename}") String dbname) {
        RDSConnection.driver = driver;
        RDSConnection.url = url;
        RDSConnection.user = user;
        RDSConnection.pass = pass;
        RDSConnection.dbname=dbname;
        return this;
    }
}
