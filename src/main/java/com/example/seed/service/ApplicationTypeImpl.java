package com.example.seed.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.seed.entity.ApplicationType;
import com.example.seed.repository.ApplicationTypeRepo;
@Service
public class ApplicationTypeImpl implements ApplicationTypeService {
	@Autowired
	ApplicationTypeRepo typeRepo;
	@Override
	public List<ApplicationType> getAllTypes() {
	
		return typeRepo.findAll();
	}

}
