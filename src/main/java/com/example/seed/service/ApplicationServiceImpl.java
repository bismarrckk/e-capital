package com.example.seed.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.seed.entity.Application;
import com.example.seed.entity.ApplicationPhase;
import com.example.seed.entity.BusinessLine;
import com.example.seed.entity.User;
import com.example.seed.repository.ApplicationRepo;
import com.example.seed.web.dto.ApplicationsByUser;
import com.example.seed.web.dto.FloatingApplication;
@Service
public class ApplicationServiceImpl implements ApplicationService{
	@Autowired
	ApplicationRepo applicationRepo;
	@Override
	public void save(Application application) {
	  applicationRepo.save(application);
		
	}
	@Override
	public Application getApplicationById(long id) {
		Optional<Application> optional=applicationRepo.findById(id);
		Application application=null;
		if(optional.isPresent()) {
			application=optional.get();
		}else {
			throw new RuntimeException("Fund Application not found!!");
		}
		return application;
	}
	@Override
	public Page<Application> getAllApplications(int pageNum, String sortField, String sortDir) {
		int pageSize=10;
		Pageable pageable=PageRequest.of(pageNum - 1, pageSize,sortDir.equals("asc") ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending());
		return applicationRepo.findAll(pageable);
	}
	@Override
	public Application getBusinessLine(BusinessLine line) {
		// TODO Auto-generated method stub
		return applicationRepo.findByBusinessLine(line);
	}
	@Override
	public List<Application> getApplicationByPhase(ApplicationPhase phase) {
		// TODO Auto-generated method stub
		return applicationRepo.findByApplicationPhase(phase);
	}
	@Override
	public Page<FloatingApplication> getNewFloatingApplications() {
		// TODO Auto-generated method stub
		Page<FloatingApplication> applications= applicationRepo.findNewFloatingApplications(PageRequest.of(0, 6, Sort.by(Sort.Direction.DESC, "id")));
		return applications;
	}
	@Override
	public List<ApplicationsByUser> getApplicationsByUser(User user) {
	
		return applicationRepo.findApplicationsByUser(user);
	}
	@Override
	public long getNumberOfApplications() {
		// TODO Auto-generated method stub
		return applicationRepo.count();
	}
	
	

}
