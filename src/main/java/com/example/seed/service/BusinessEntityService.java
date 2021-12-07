package com.example.seed.service;

import org.springframework.data.domain.Page;

import com.example.seed.entity.BusinessEntity;
import com.example.seed.entity.User;

public interface BusinessEntityService {
	void save(BusinessEntity entity);
	Page<BusinessEntity> getEntitiesByUser(User user,int pageNum,String sortField,String sortDir);
	BusinessEntity getEntityById(long id);
	void deleteEntity(long id);
	
}
