package com.example.seed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.seed.entity.BusinessLine;
import com.example.seed.entity.Patent;

@Repository
public interface PatentRepo extends JpaRepository<Patent,Long>{
	Patent findByBusinessLine(BusinessLine line);
}
