package com.jeevlifeworks.migrationservice.XmlToCsvToMySQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolRepository extends JpaRepository<ToolsCatalogue, Integer>{

}
