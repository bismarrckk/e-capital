package com.example.seed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.seed.entity.ApplicationType;
@Repository
public interface ApplicationTypeRepo extends JpaRepository<ApplicationType,Integer> {

}
