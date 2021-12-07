package com.example.seed.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.seed.entity.Investment;
import com.example.seed.entity.User;
import com.example.seed.repository.InvestmentRepo;
import com.example.seed.web.dto.InvestmentsByUser;
@Service
public class InvestmentServiceImpl implements InvestmentService{
@Autowired
InvestmentRepo investmentRepo;
@Autowired
JavaMailSender javaMailSender;
	@Override
	public Investment save(Investment investment) {
		// TODO Auto-generated method stub
		return investmentRepo.save(investment);
	}
	@Override
	public List<InvestmentsByUser> getInvestmentByUser(User user) {
		// TODO Auto-generated method stub
		return investmentRepo.findInvestmentByUser(user);
	}
	@Override
	public void sendNotificationMail(User user,User investor) throws UnsupportedEncodingException, MessagingException {
		String toAddress =user.getEmail();
	    String fromAddress = "b77kibet@gmail.com";
	    String senderName = "E-Capital";
	    String subject = "Your Startup has been matched to an investor";
	    String content = "Dear [[name]],<br>"
	            + "Congratulations,your startup project has been chosen as a viable option by an investor,"
	            + "kindly contact [[firstname]] [[lastname]] on [[phone]] as soon as possible for further engagements.<br>"     
	            + "Thank you,<br>"
	            + "E-Capital Sourcing";
	    
	   
	    MimeMessage message = javaMailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	     
	    helper.setFrom(fromAddress, senderName);
	    helper.setTo(toAddress);
	    helper.setSubject(subject);
	    
	    String phoneString=String.valueOf(investor.getPhoneNumber());
	    
	    content = content.replace("[[name]]", user.getFirstName());
	    content = content.replace("[[firstname]]", investor.getFirstName());
	    content = content.replace("[[lastname]]", investor.getLastName());
	    content = content.replace("[[phone]]",phoneString);
	    	    
	    helper.setText(content, true);
	     
	    javaMailSender.send(message);
		
	}
	@Override
	public long getNumberOfInvestments() {
		// TODO Auto-generated method stub
		return investmentRepo.count();
	}
	@Override
	public Page<Investment> getAllInvestments(int pageNum,String sortField,String sortDir) {
		int pageSize=10;
		Pageable pageable=PageRequest.of(pageNum - 1, pageSize,sortDir.equals("asc") ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending());
		return investmentRepo.findAll(pageable);
	}

}
