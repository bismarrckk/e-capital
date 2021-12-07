package com.example.seed.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.seed.entity.BusinessEntity;
import com.example.seed.entity.BusinessLine;
import com.example.seed.entity.User;
import com.example.seed.web.dto.BusinessLinesByUser;

@Repository
public interface BusinessLineRepo extends JpaRepository<BusinessLine,Long> {
	Page<BusinessLine> findByBusinessEntity(BusinessEntity entity,Pageable pageable);
	@Query("SELECT new com.example.seed.web.dto.BusinessLinesByUser(bl.id,bl.sector,bl.commodity,bl.startDate,bl.employeeCount,be.legalName)"
			+ "FROM BusinessLine bl join bl.businessEntity be where be.contactPerson=:user")
	List<BusinessLinesByUser> findBusinessLineByUser(User user);
}
