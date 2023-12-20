package com.jeevlifeworks.migrationservice.XmlToCsvToMySQL;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//class to define request body parameters
public class XmlToCsvDTO {
    private String sourceFolder;
    private String destFolder;
    private int toolCatalogueId;

    public String getSourceFolder() {
        return null;
    }
}
