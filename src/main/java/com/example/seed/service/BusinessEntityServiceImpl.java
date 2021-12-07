package com.example.seed.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.seed.entity.BusinessEntity;
import com.example.seed.entity.User;
import com.example.seed.repository.BusinessEntityRepo;

@Service
public class BusinessEntityServiceImpl implements BusinessEntityService{
	@Autowired
	BusinessEntityRepo entityRepo;
	@Override
	public void save(BusinessEntity entity) {
		entityRepo.save(entity);
		
	}
	@Override
	public Page<BusinessEntity> getEntitiesByUser(User user,int pageNum, String sortField, String sortDir) {
		int pageSize=10;
		Pageable pageable=PageRequest.of(pageNum - 1, pageSize,sortDir.equals("asc") ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending());
		return entityRepo.findByContactPerson(user, pageable);
	}
	@Override
	public BusinessEntity getEntityById(long id) {
		Optional<BusinessEntity> optional=entityRepo.findById(id);
		BusinessEntity entity=null;
		if(optional.isPresent()) {
			entity=optional.get();
		}
		else {
			throw new RuntimeException("Business Entity not found!");
		}
		return entity;
	}
	
	@Override
	public void deleteEntity(long id) {
		BusinessEntity entity=getEntityById(id);
		entityRepo.delete(entity);
		
	}
	
	
}
