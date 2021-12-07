package com.example.seed.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.seed.entity.BusinessEntity;
import com.example.seed.entity.BusinessLine;
import com.example.seed.entity.User;
import com.example.seed.repository.BusinessLineRepo;
import com.example.seed.web.dto.BusinessLinesByUser;

@Service
public class BusinessLineImpl implements BusinessLineService {
	@Autowired
	BusinessLineRepo lineRepo;
	@Override
	public void save(BusinessLine line) {
		lineRepo.save(line);
		
	}
	@Override
	public Page<BusinessLine> getAllLines(BusinessEntity entity,int pageNum, String sortField, String sortDir) {
		int pageSize=10;
		Pageable pageable=PageRequest.of(pageNum - 1, pageSize,sortDir.equals("asc") ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending());
		return lineRepo.findByBusinessEntity(entity,pageable);
	}
	@Override
	public BusinessLine getLineById(long id) {
		Optional<BusinessLine> optional=lineRepo.findById(id);
		BusinessLine line=null;
		if(optional.isPresent()) {
			line=optional.get();
		}else {
			throw new RuntimeException("Business Line not found!!");
		}
		return line;
	}
	@Override
	public void deleteLine(long id) {
		BusinessLine line=getLineById(id);
		lineRepo.delete(line);
		
	}
	@Override
	public List<BusinessLinesByUser> getLinesByUser(User user) {
	
		return lineRepo.findBusinessLineByUser(user);
	}
	

}
