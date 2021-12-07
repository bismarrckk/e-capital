package com.example.seed.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.seed.entity.Investment;
import com.example.seed.entity.User;
import com.example.seed.web.dto.InvestmentsByUser;

@Repository
public interface InvestmentRepo extends JpaRepository<Investment,Long>{
	@Query("SELECT new com.example.seed.web.dto.InvestmentsByUser(a.id,a.targetAmount,a.applicationDate)"
			+ "FROM Investment i join i.application a WHERE  i.user = :user")
	List<InvestmentsByUser> findInvestmentByUser(User user); 
}
