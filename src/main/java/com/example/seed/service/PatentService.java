package com.example.seed.service;

import org.springframework.web.multipart.MultipartFile;

import com.example.seed.entity.BusinessLine;
import com.example.seed.entity.Patent;

public interface PatentService {
	void save(Patent patent);
	Patent getPatentByLine(BusinessLine line);
	Boolean validateImageFile(MultipartFile file);
}
