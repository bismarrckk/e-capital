package com.example.seed.web;


import java.security.Principal;
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

import com.example.seed.entity.BusinessEntity;
import com.example.seed.entity.BusinessLine;
import com.example.seed.entity.User;
import com.example.seed.service.BusinessEntityService;
import com.example.seed.service.BusinessLineService;
import com.example.seed.service.UserService;
import com.example.seed.web.dto.BusinessLineDto;
import com.example.seed.web.dto.BusinessLinesByUser;

@Controller
@RequestMapping("/businessLine")
public class BusinessLineController {
	@Autowired
	BusinessEntityService entityService;
	@Autowired
	ModelMapper mapper;
	@Autowired
	BusinessLineService lineService;
	@Autowired
	UserService userService;
	
	@GetMapping
	public String viewLines(Model model,HttpServletRequest request) {
		Principal principal=request.getUserPrincipal();
		String email=principal.getName();
		User user=userService.getUserByEmail(email);
		List<BusinessLinesByUser> listLines=lineService.getLinesByUser(user);
		model.addAttribute("lines", listLines);
		return"frontend/business_lines";
	}
	
	
	@GetMapping("/create/{id}")
	public String showNewLinePage(@PathVariable(name="id") long id,Model model,RedirectAttributes attributes) {
		BusinessLineDto lineDto=new BusinessLineDto();
		
		try {
			BusinessEntity entity=entityService.getEntityById(id);
			long entity_id=entity.getId();
			model.addAttribute("entity", entity_id);
			model.addAttribute("line", lineDto);
			return "frontend/new_business_line";
		}catch(RuntimeException ex) {
			attributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/entities";		    
		}
	}
	@PostMapping("/save")
	public String saveLine(@Valid @ModelAttribute("line") BusinessLineDto lineDto,BindingResult bindingResult, RedirectAttributes attributes,
			HttpServletRequest request) {
		if(bindingResult.hasErrors()) {
			return"frontend/new_business_line";
		}
		long entityId=Long.parseLong(request.getParameter("entity_id"));
		BusinessEntity entity=entityService.getEntityById(entityId);
		lineDto.setBusinessEntity(entity);    
		BusinessLine line=mapper.map(lineDto, BusinessLine.class);
		lineService.save(line);
		if(lineDto.getId()<1) {
		attributes.addFlashAttribute("message","Business Line created successfully!!");
		}else {
		attributes.addFlashAttribute("message","Business Line updated successfully!!");	
		}
		return "redirect:/businessLine";
	}
	@GetMapping("edit/{id}")
	public String editLine(@PathVariable(name="id") long id,Model model,RedirectAttributes attributes) {
		try {
			BusinessLine line=lineService.getLineById(id);
			model.addAttribute("line", line);
			long entity_id=line.getBusinessEntity().getId();
			model.addAttribute("entity", entity_id);
			return "frontend/new_business_line";
		}catch(RuntimeException ex) {
			attributes.addFlashAttribute("message","Business Line not found!!");
			return "redirect:/businessLine";
		}
		
	}
	@GetMapping("delete/{id}")
	public String deleteLine(@PathVariable(name="id") long id,RedirectAttributes attributes) {
		try {
			lineService.deleteLine(id);
			attributes.addFlashAttribute("message", "Business Line deleted successfully!!");
		}catch(RuntimeException ex) {
			attributes.addFlashAttribute("message", "Business Line not found!!");
		}
		return "redirect:/businessLine";
	}
}
