package com.example.seed.web;

import java.util.List;

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
import com.example.seed.entity.BusinessLine;
import com.example.seed.entity.Patent;
import com.example.seed.service.ApplicationPhaseService;
import com.example.seed.service.ApplicationService;
import com.example.seed.service.InvestmentService;
import com.example.seed.service.PatentService;
import com.example.seed.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	UserService userService;
	@Autowired
	InvestmentService investmentService;
	@Autowired
	ApplicationService applicationService;
	@Autowired ApplicationPhaseService phaseService;
	@Autowired
	PatentService patentService;
	@GetMapping
	public String home(Model model) {
		long applications=applicationService.getNumberOfApplications();
		long investments=investmentService.getNumberOfInvestments();
		long users=userService.getNumberOfUsers();
		model.addAttribute("applications", applications);
		model.addAttribute("investments",investments);
		model.addAttribute("users", users);
		return "backend/index";
	}
	@GetMapping("/applications")
	public String viewApplications(Model model) {
		return viewPage(model,1,"applicationDate","asc"); 
	}
	@GetMapping("/page/{pageNum}")
	public String viewPage(Model model,@PathVariable(name="pageNum") int pageNum, @Param("sortField") String sortField,
	        @Param("sortDir") String sortDir) {
		Page<Application> page=applicationService.getAllApplications(pageNum, sortField, sortDir);
		List<Application> listApplications=page.getContent();
		model.addAttribute("currentPage", pageNum);
	    model.addAttribute("totalPages", page.getTotalPages());
	    model.addAttribute("totalItems", page.getTotalElements());
	    
	    model.addAttribute("sortField", sortField);
	    model.addAttribute("sortDir", sortDir);
	    model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
	    
	    model.addAttribute("listApplications", listApplications);
	    return "backend/applications";
	}
	@GetMapping("/details/{id}")
	public String getApplicationDetails(@PathVariable(name="id") long id,Model model,RedirectAttributes attributes) {
		try {
			Application application=applicationService.getApplicationById(id);
			//check if business Line has a patent
			BusinessLine line=application.getBusinessLine();
			Patent patent=patentService.getPatentByLine(line);
			int patentAvailable;
			if(patent ==null) {
			patentAvailable=0;
			}else {
			patentAvailable=1;
			}
			model.addAttribute("patent", patentAvailable);
			ApplicationPhase phase=phaseService.getByName("LOGGING");
			application.setApplicationPhase(phase);
			applicationService.save(application);
			model.addAttribute("applicationDetails", application);
			return"backend/application_details";
		}catch(RuntimeException ex) {
			attributes.addFlashAttribute("message", "Fund application not found!!");
			return "redirect:/admin/applications";
		}
		
		
	}
	@PostMapping("/saveVerdict")
	public String saveApprovalVerdict(RedirectAttributes attributes,HttpServletRequest request) {
		String verdict=request.getParameter("verdict");
		long id=Long.parseLong(request.getParameter("id"));
		try {
			Application application=applicationService.getApplicationById(id);
			ApplicationPhase phase=phaseService.getByName(verdict);
			application.setApplicationPhase(phase);
			applicationService.save(application);
			attributes.addFlashAttribute("message", "Application updated successfully !!");
		}catch(RuntimeException ex) {
			attributes.addFlashAttribute("message", "Application not found!!");
		}
		return "redirect:/admin/applications";
	}
	
}













