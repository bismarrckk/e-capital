package com.example.seed.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.data.domain.Page;

import com.example.seed.entity.Investment;
import com.example.seed.entity.User;
import com.example.seed.web.dto.InvestmentsByUser;

public interface InvestmentService {
	Investment save(Investment investment);
	List<InvestmentsByUser> getInvestmentByUser(User user);
	void sendNotificationMail(User user, User investor) throws UnsupportedEncodingException, MessagingException;
	long getNumberOfInvestments();
	Page<Investment> getAllInvestments(int pageNum,String sortField,String sortDir);
}
