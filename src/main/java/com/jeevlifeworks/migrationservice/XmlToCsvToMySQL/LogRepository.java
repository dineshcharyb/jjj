package com.jeevlifeworks.migrationservice.XmlToCsvToMySQL;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<LogInfo, Integer>{

	@Query(value="SELECT * FROM toolserviceloginformation WHERE ToolId=?1 ORDER BY ServiceStartDateTime DESC LIMIT 1",nativeQuery=true)
	List<LogInfo> serviceLogInformationByToolId(int id);

	@Transactional
	@Modifying
	@Query(value="update  toolserviceloginformation set ServiceStartDateTime=?1,Status=?3  where\r\n" + 
			"        ToolId=?4 and ActivityName=?2 order by ServiceLogId desc limit 1",nativeQuery=true)
	void updateServiceLogInformationBefore(String datetime, String activityName, String status, int toolId);

	@Transactional
	@Modifying
	@Query(value="update  toolserviceloginformation set ServiceEndDateTime=?1,Status=?3  where\r\n" + 
			"        ToolId=?4 and ActivityName=?2 order by ServiceLogId desc limit 1",nativeQuery=true)
	void updateServiceLogInformationAfter(String datetime, String activityName, String status, int toolId);



}
