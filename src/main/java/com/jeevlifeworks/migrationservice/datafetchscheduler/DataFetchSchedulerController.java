package com.jeevlifeworks.migrationservice.datafetchscheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.jeevlifeworks.migrationservice.entity.AuthModel;

@RestController
public class DataFetchSchedulerController {
	
	
	@Autowired
	DataFetchSchedularService dataFetchSchedularService;
	
	
	public AuthModel getLoginSessionId() {
		return dataFetchSchedularService.getLoginSessionId();
	}
	

}
