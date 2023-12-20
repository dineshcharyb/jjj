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
@Table(name="toolscatalogue")
public class ToolsCatalogue {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int ToolCatalogueId;
	
	private String ToolName;
	private String ToolDescription;
	private String Input;
	private String Output;
	private String Endpoints;
	private String InstructionsToUse;
	private String Usage;
	
}
