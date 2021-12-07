package com.example.seed.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.seed.entity.ApplicationPhase;
import com.example.seed.repository.ApplicationPhaseRepo;

@Service
public class ApplicationPhaseImpl implements ApplicationPhaseService{
	@Autowired
	ApplicationPhaseRepo phaseRepo;
	@Override
	public List<ApplicationPhase> getAllPhases() {
		
		return phaseRepo.findAll();
	}
	@Override
	public ApplicationPhase getByName(String name) {
		// TODO Auto-generated method stub
		return phaseRepo.findByName(name);
	}
	@Override
	public ApplicationPhase getById(int id) {
		// TODO Auto-generated method stub
		Optional<ApplicationPhase> optional = phaseRepo.findById(id);
		ApplicationPhase phase=null;
		if(optional.isPresent()) {
			phase=optional.get();
		}else {
			throw new RuntimeException("Application phase not found");
		}
		return phase;
	}

}
