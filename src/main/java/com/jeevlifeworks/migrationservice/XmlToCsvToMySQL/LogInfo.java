package com.jeevlifeworks.migrationservice.XmlToCsvToMySQL;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="toolserviceloginformation")
public class LogInfo {
 
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int ServiceLogId;
	
	private int ToolId;
	private String ActivityName;
	private String ServiceStartDateTime;
	private String ServiceEndDateTime;
	private String Status;
	private String ExecutedBy;
}
