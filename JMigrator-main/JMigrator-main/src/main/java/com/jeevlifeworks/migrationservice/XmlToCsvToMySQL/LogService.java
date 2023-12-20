package com.jeevlifeworks.migrationservice.XmlToCsvToMySQL;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LogService {

	@Autowired
	LogRepository logrepo;
	
	public List<LogInfo> logInformationByToolId(int id) {
		List<LogInfo> logObj=logrepo.serviceLogInformationByToolId(id);
		return logObj;
	}
	/**
	 * method to insert tool service logging information for particular ToolId 
	 * @param toolId
	 */
//	public void insertServiceLogInformation(int toolId) {
//		List<LogInfo> serviceLogList = new ArrayList<LogInfo>();
//		if(toolId==1) {
//		for(int i=0;i<=1;i++) {
//			LogInfo serviceLogInfo=new LogInfo();
//			serviceLogInfo.setToolId(toolId);
//			serviceLogInfo.setStatus(ConstantString.status[0]);
//			if(i==0) {
//				serviceLogInfo.setActivityName(ConstantString.activityName[0]);
//			}else if(i==1){
//				serviceLogInfo.setActivityName(ConstantString.activityName[1]);
//			}
//			serviceLogList.add(serviceLogInfo);
//		 }
//		}else if(toolId==2){
//			LogInfo serviceLogInfo=new LogInfo();
//			serviceLogInfo.setToolId(toolId);
//			serviceLogInfo.setStatus(ConstantString.status[0]);
//			serviceLogInfo.setActivityName(ConstantString.activityName[2]);
//			serviceLogList.add(serviceLogInfo);
//		}else {
//			LogInfo serviceLogInfo=new LogInfo();
//			serviceLogInfo.setToolId(toolId);
//			serviceLogInfo.setStatus(ConstantString.status[0]);
//			serviceLogInfo.setActivityName(ConstantString.activityName[3]);
//			serviceLogList.add(serviceLogInfo);
//		}
//		logrepo.saveAll(serviceLogList);
//
//	}

	/**
	 * method to update the tool service logging information about initiation and termination of services
	 * @param datetime
	 * @param activityName
	 * @param status
	 * @param beforeOrafer
	 * @param toolId
	 */
	public void updateServiceLogInformation(String datetime, String activityName, String status, String beforeOrafer,int toolId) {
		
		if(beforeOrafer.equals(ConstantString.beforeOrAfter[0])) {
			logrepo.updateServiceLogInformationBefore(datetime,activityName,status,toolId);
		}else {
			logrepo.updateServiceLogInformationAfter(datetime,activityName,status,toolId);
		}
	}
			

	

}
