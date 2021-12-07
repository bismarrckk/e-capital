package com.example.seed.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.seed.entity.Application;
import com.example.seed.entity.ApplicationPhase;
import com.example.seed.entity.BusinessLine;
import com.example.seed.entity.User;
import com.example.seed.web.dto.ApplicationsByUser;
import com.example.seed.web.dto.FloatingApplication;
@Repository
public interface ApplicationRepo extends JpaRepository<Application,Long> {
	Application findByBusinessLine(BusinessLine line);
	List<Application> findByApplicationPhase(ApplicationPhase phase);
	@Query("SELECT new com.example.seed.web.dto.FloatingApplication(a.id,ph.name,bl.sector,bl.commodity)"
			+ " FROM Application a join a.applicationPhase ph join a.businessLine bl WHERE ph.name like 'FLOATING' ")
	Page<FloatingApplication> findNewFloatingApplications(Pageable pageable);
	@Query("SELECT new com.example.seed.web.dto.ApplicationsByUser(a.id,a.targetAmount,a.applicationDate,bl.commodity,bl.sector,ph.name,ty.name)"
			+ "FROM Application a join a.applicationPhase ph join a.type ty join a.businessLine bl where bl.businessEntity.contactPerson=:user")
	List<ApplicationsByUser> findApplicationsByUser(User user);
}
