package com.example.seed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.seed.entity.ApplicationPhase;
import com.example.seed.entity.ApplicationType;
import com.example.seed.entity.Role;
import com.example.seed.repository.ApplicationPhaseRepo;
import com.example.seed.repository.ApplicationTypeRepo;
import com.example.seed.repository.RoleRepo;
import com.example.seed.repository.UserRepo;

@Component
public class ConstantValues implements CommandLineRunner{
	@Autowired
	ApplicationPhaseRepo phaseRepo;
	@Autowired 
	ApplicationTypeRepo typeRepo;
	@Autowired
	RoleRepo roleRepo;
	@Autowired 
	UserRepo userRepo;
	
	@Override
	public void run(String... args) throws Exception {
	  /*roleRepo.save(new Role("ADMIN"));
		roleRepo.save(new Role("INVESTOR"));
		roleRepo.save(new Role("STARTUP"));
		roleRepo.save(new Role("SUPER_USER"));
		
		phaseRepo.save(new ApplicationPhase("PENDING"));
		phaseRepo.save(new ApplicationPhase("LOGGING"));
		phaseRepo.save(new ApplicationPhase("FLOATING"));
		phaseRepo.save(new ApplicationPhase("ACCEPTED"));
		phaseRepo.save(new ApplicationPhase("REJECTED"));
		
		typeRepo.save(new ApplicationType("EQUITY"));
		typeRepo.save(new ApplicationType("GRANT"));
		typeRepo.save(new ApplicationType("LOAN"));
		*/
		
	}

}
