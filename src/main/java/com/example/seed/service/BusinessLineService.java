package com.example.seed.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.seed.entity.BusinessEntity;
import com.example.seed.entity.BusinessLine;
import com.example.seed.entity.User;
import com.example.seed.web.dto.BusinessLinesByUser;

public interface BusinessLineService {
	void save(BusinessLine line);
	Page<BusinessLine> getAllLines(BusinessEntity entity,int pageNum,String sortField,String sortDir);
	BusinessLine getLineById(long id);
	void deleteLine(long id);
	List<BusinessLinesByUser> getLinesByUser(User user);
	
}
