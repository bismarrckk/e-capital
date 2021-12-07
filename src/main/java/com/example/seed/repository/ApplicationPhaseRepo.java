package com.example.seed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.seed.entity.ApplicationPhase;
@Repository
public interface ApplicationPhaseRepo extends JpaRepository<ApplicationPhase,Integer> {
	ApplicationPhase findByName(String name);
}
