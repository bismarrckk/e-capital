package com.example.seed.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.seed.entity.BusinessEntity;
import com.example.seed.entity.User;
@Repository
public interface BusinessEntityRepo extends JpaRepository<BusinessEntity,Long>{
	
	Page<BusinessEntity> findByContactPerson(User user,Pageable pageable);
	
}
