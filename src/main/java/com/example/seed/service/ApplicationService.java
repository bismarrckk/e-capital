package com.example.seed.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.seed.entity.Application;
import com.example.seed.entity.ApplicationPhase;
import com.example.seed.entity.BusinessLine;
import com.example.seed.entity.User;
import com.example.seed.web.dto.ApplicationsByUser;
import com.example.seed.web.dto.FloatingApplication;

public interface ApplicationService {
	void save(Application application);
	Application getApplicationById(long id);
	Page<Application> getAllApplications(int pageNum,String sortField,String sortDir);
	Application getBusinessLine(BusinessLine line);
	List<Application> getApplicationByPhase(ApplicationPhase phase);
	Page<FloatingApplication> getNewFloatingApplications();
	List<ApplicationsByUser> getApplicationsByUser(User user);
	long getNumberOfApplications();
}
