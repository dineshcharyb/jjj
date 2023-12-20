package com.jeevlifeworks.migrationservice.XmlToCsvToMySQL;


public class ConstantString {
	
	public static final String sourceError="source directory does not exists,please check for the correct path";
	 public static final String destinationError="destination directory does not exists,please check for the correct path";
	 public static final String initiatedServiceMsg="your service got initiated ,please wait for some time";
	 public static final String configurationFileError="configuration file doesn't exists please check it";
	 public static final String[] ToolIdError={"Tool Id which is selected is not valid for XmltoCsvConversion","Tool Id which is selected is not valid for XML Modification","Tool Id which is selected is not valid for XML Splitter"};
	 
	 public static final String[] activityName={"XMLToCsvConversion","CSVToSQL","XML Modifier","XML Splitter"};
	 public static final String[] status= {"Yet To Start","In progress","completed"};
	 public static final String[] beforeOrAfter= {"before","after"};
}
