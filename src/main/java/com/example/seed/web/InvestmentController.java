package com.example.seed.web;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.seed.entity.Application;
import com.example.seed.entity.ApplicationPhase;
import com.example.seed.entity.Investment;
import com.example.seed.entity.User;
import com.example.seed.service.ApplicationPhaseService;
import com.example.seed.service.ApplicationService;
import com.example.seed.service.InvestmentService;
import com.example.seed.service.UserService;
import com.example.seed.web.dto.InvestmentsByUser;

@Controller
@RequestMapping("/investments")
public class InvestmentController {
	@Autowired
	ApplicationService appService;
	@Autowired
	InvestmentService investService;
	@Autowired
	UserService userService;
	@Autowired
	ApplicationPhaseService phaseService;
	
	
	@GetMapping
	public String viewPage(Model model,HttpServletRequest request) {
		Principal principal=request.getUserPrincipal();
		String email=principal.getName();
		User user=userService.getUserByEmail(email);
		
		List<InvestmentsByUser> listInvestments=investService.getInvestmentByUser(user);
		model.addAttribute("listInvestments", listInvestments);
	    return "frontend/investments";
	}
	
	@GetMapping("/details/{id}")
	public String getApplicationDetails(@PathVariable(name="id") long id,Model model,RedirectAttributes attributes) {
		try {
			Application application=appService.getApplicationById(id);
			model.addAttribute("applicationDetails", application);
			return"frontend/investment_details";
		}catch(RuntimeException ex) {
			attributes.addFlashAttribute("message", "Investment not found!!");
			return "redirect:/investments";
		}
		
		
	}
	@PostMapping("/save")
	public String saveInvestment(HttpServletRequest request,RedirectAttributes attributes) {
		long applicationId=Long.parseLong(request.getParameter("id"));
		Application application=appService.getApplicationById(applicationId);
		ApplicationPhase phase=phaseService.getByName("ACCEPTED");
		application.setApplicationPhase(phase);
		appService.save(application);
		Principal principal=request.getUserPrincipal();
		String email=principal.getName();
		User user=userService.getUserByEmail(email);
		Date date=new Date();
		Investment invest=new Investment();
		invest.setApplication(application);
		invest.setUser(user);
		invest.setDate(date);
		
		User contactPerson=application.getBusinessLine().getBusinessEntity().getContactPerson();
		try {
			Investment investment=investService.save(invest);
			User investor=investment.getUser();
			
			investService.sendNotificationMail(contactPerson,investor);
			
			attributes.addFlashAttribute("message", "Success!! The startup owner will contact you shortly");
		} catch (UnsupportedEncodingException | MessagingException e) {
			attributes.addFlashAttribute("message", e);
		
		}
				
		return"redirect:/investments";
	}
	@GetMapping("/invest")
	public String viewInvestmentsPage(Model model) {
		return viewInvestments(model,1,"date","desc");
	}
	@GetMapping("/page/{pageNum}")
	public String viewInvestments(Model model,@PathVariable(name="pageNum") int pageNum, @Param("sortField") String sortField,
	        @Param("sortDir") String sortDir) {
		Page<Investment> page=investService.getAllInvestments(pageNum, sortField, sortDir);
		List<Investment> listInvestments=page.getContent();
		model.addAttribute("currentPage", pageNum);
	    model.addAttribute("totalPages", page.getTotalPages());
	    model.addAttribute("totalItems", page.getTotalElements());
	    
	    model.addAttribute("sortField", sortField);
	    model.addAttribute("sortDir", sortDir);
	    model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
	    
	    model.addAttribute("listInvestments", listInvestments);
	    return "backend/investments";
	}
}
