package com.example.seed.web;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.seed.entity.Application;
import com.example.seed.entity.ApplicationPhase;
import com.example.seed.entity.ApplicationType;
import com.example.seed.entity.BusinessLine;
import com.example.seed.entity.Patent;
import com.example.seed.entity.User;
import com.example.seed.service.ApplicationPhaseService;
import com.example.seed.service.ApplicationService;
import com.example.seed.service.ApplicationTypeService;
import com.example.seed.service.BusinessLineService;
import com.example.seed.service.PatentService;
import com.example.seed.service.UserService;
import com.example.seed.web.dto.ApplicationDto;
import com.example.seed.web.dto.ApplicationsByUser;

@Controller
@RequestMapping("/applications")
public class ApplicationController {
	@Autowired
	BusinessLineService lineService;
	@Autowired
	ApplicationTypeService typeService;
	@Autowired 
	ApplicationPhaseService phaseService;
	@Autowired
	ApplicationService applicationService;
	@Autowired
	UserService userService;
	@Autowired
	ModelMapper mapper;
	@Autowired
	PatentService patentService;

	@GetMapping
	public String viewApplications(Model model,HttpServletRequest request) {
		Principal principal=request.getUserPrincipal();
		String email=principal.getName();
		User user=userService.getUserByEmail(email);
		List<ApplicationsByUser> listApplications=applicationService.getApplicationsByUser(user);
		model.addAttribute("applications", listApplications);
		return "frontend/applications";
	}
	 
	@GetMapping("/create/{id}")
	public String showCreateForm(@PathVariable(name="id") long id,Model model,RedirectAttributes attributes) {
		try {
			
		ApplicationDto applicationDto=new ApplicationDto();
		model.addAttribute("application", applicationDto);
		BusinessLine line=lineService.getLineById(id);
		//check whether application has been made
		Application application=applicationService.getBusinessLine(line);
		if(application != null) {
			attributes.addFlashAttribute("message", "Funding application for the selected business exists!!");
			return "redirect:/businessLine";
		}
		//end
		long lineId=line.getId();
		model.addAttribute("line", lineId);
		List<ApplicationType> type=typeService.getAllTypes();
		model.addAttribute("types", type);
		return "frontend/new_application";
		}catch(RuntimeException ex) {
			attributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/businessLine";
		}
	}
	
	@PostMapping("/save")
	public String saveApplication(@Valid @ModelAttribute("application") ApplicationDto applicationDto,
			BindingResult bindingResult,RedirectAttributes attributes,HttpServletRequest request) {
		if(bindingResult.hasErrors()) {
			return "frontend/new_application";
		}
		long lineId=Long.parseLong(request.getParameter("businessLine"));
		BusinessLine line=lineService.getLineById(lineId);
		applicationDto.setBusinessLine(line);
		Date date=new Date();
		applicationDto.setApplicationDate(date);
		 
		ApplicationPhase phase=phaseService.getByName("PENDING");
		applicationDto.setApplicationPhase(phase);
		 
		
		Application application=mapper.map(applicationDto, Application.class);
		applicationService.save(application);
		if(applicationDto.getId()<1) {
		attributes.addFlashAttribute("message","Application created successfully!!");
		}else {
		attributes.addFlashAttribute("message","Application details updated successfully!!");	
		}
		return "redirect:/applications";
	}
	@GetMapping("/edit/{id}")
	public String editApplication(@PathVariable(name="id") long id,Model model,RedirectAttributes attributes) {
		try {
			Application application=applicationService.getApplicationById(id);
			BusinessLine line=application.getBusinessLine();
			long lineId=line.getId();
			model.addAttribute("line", lineId);
			model.addAttribute("application", application);
			List<ApplicationType> type=typeService.getAllTypes();
			model.addAttribute("types", type);			
			return "frontend/new_application";
		}catch(RuntimeException ex) {
			attributes.addFlashAttribute("message", "Application details not found!!");
			return"redirect:/applications";
		}
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
			model.addAttribute("applicationDetails", application);
			return"frontend/application_details";
		}catch(RuntimeException ex) {
			attributes.addFlashAttribute("message", "Funding details not found!!");
			return "redirect:/businessLine";
		}
		
		
	}
	
		
}
