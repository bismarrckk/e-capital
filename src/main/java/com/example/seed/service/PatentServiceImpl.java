package com.example.seed.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.seed.entity.BusinessLine;
import com.example.seed.entity.Patent;
import com.example.seed.repository.PatentRepo;
@Service
public class PatentServiceImpl implements PatentService{
	@Autowired
	PatentRepo patentRepo;
	private static final List<String> imageContentTypes = Arrays.asList("application/PDF", "application/pdf");
	@Override
	public void save(Patent patent) {
		patentRepo.save(patent);
		
	}
	@Override
	public Patent getPatentByLine(BusinessLine line) {
		// TODO Auto-generated method stub
		return patentRepo.findByBusinessLine(line);
	}
	@Override
	public Boolean validateImageFile(MultipartFile file) {
		String fileContentType = file.getContentType();
		if(imageContentTypes.contains(fileContentType)) {
	        return true;
	    }
		else {
			return false;
		}
	}
}
