package com.example.seed.service;

import java.util.List;

import com.example.seed.entity.ApplicationPhase;

public interface ApplicationPhaseService {
	List<ApplicationPhase> getAllPhases();
	ApplicationPhase getByName(String name);
	ApplicationPhase getById(int id);
}
