package com.example.seed.web;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
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
import com.example.seed.entity.User;
import com.example.seed.service.BusinessEntityService;
import com.example.seed.service.UserService;
import com.example.seed.web.dto.BusinessEntityDto;

@Controller
@RequestMapping("/entities")
public class BusinessEntityController {
	@Autowired
	BusinessEntityService entityService;
	@Autowired
	ModelMapper mapper;
	@Autowired
	UserService userService;
	@GetMapping
	public String viewEntities(Model model,HttpServletRequest request) {
		return viewPage(model,1,"legalName","asc",request);
	}
	
	@GetMapping("/page/{pageNum}")
	public String viewPage(Model model,@PathVariable(name="pageNum") int pageNum, @Param("sortField") String sortField,
	        @Param("sortDir") String sortDir,HttpServletRequest request) {
		Principal principal=request.getUserPrincipal();
		String email=principal.getName();
		User user=userService.getUserByEmail(email);
		Page<BusinessEntity> page=entityService.getEntitiesByUser(user,pageNum, sortField, sortDir);
		List<BusinessEntity> listEntities=page.getContent();
		model.addAttribute("currentPage", pageNum);
	    model.addAttribute("totalPages", page.getTotalPages());
	    model.addAttribute("totalItems", page.getTotalElements());
	    
	    model.addAttribute("sortField", sortField);
	    model.addAttribute("sortDir", sortDir);
	    model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
	    
	    model.addAttribute("listEntities", listEntities);
	    return "frontend/business_entities";
		
	}
	
	@GetMapping("/create")
	public String showEntityForm(Model model,HttpServletRequest request) {
		BusinessEntityDto bEntityDto=new BusinessEntityDto();
		model.addAttribute("entity", bEntityDto);
		//Get Full Name
		Principal principal = request.getUserPrincipal();
		String email=principal.getName();
		User user=userService.getUserByEmail(email);
		String firstName=user.getFirstName();
		String lastName=user.getLastName();
		String fullName=firstName+ " "+lastName;
		model.addAttribute("fullName", fullName);
		return "frontend/new_business_entity";
	}
	@PostMapping("/save")
	public String saveEntity(@Valid @ModelAttribute("startup") BusinessEntityDto bEntityDto,
			BindingResult bindingResult,RedirectAttributes attributes,HttpServletRequest request) {
		if(bindingResult.hasErrors()) {
			return "frontend/new_business_entity";
		}
		Principal principal = request.getUserPrincipal();
		String email=principal.getName();
		User user=userService.getUserByEmail(email);
		bEntityDto.setContactPerson(user);
		BusinessEntity entity=mapper.map(bEntityDto, BusinessEntity.class);
		entityService.save(entity);
		if(bEntityDto.getId() < 1) {
		attributes.addFlashAttribute("message","Business Entity created Successfully!!");
		}else {
			attributes.addFlashAttribute("message","Business Entity updated Successfully!!");	
		}
		return "redirect:/entities";
		
	}
	
	@GetMapping("delete/{id}")
	public String deleteEntity(@PathVariable(name="id") long id,RedirectAttributes attributes) {
			try {
				entityService.deleteEntity(id);
				attributes.addFlashAttribute("message", "Business Entity deleted successfully!!");
				}catch(RuntimeException ex) {
				attributes.addFlashAttribute("message", "Business Entity not found!!");
			}
			return "redirect:/entities";
	}
	@GetMapping("edit/{id}")
	public String editEntity(@PathVariable(name="id") long id,Model model,RedirectAttributes attributes,HttpServletRequest request) {
		try {
		BusinessEntity entity=entityService.getEntityById(id);
		model.addAttribute("entity", entity);
		Principal principal = request.getUserPrincipal();
		String email=principal.getName();
		User user=userService.getUserByEmail(email);
		String firstName=user.getFirstName();
		String lastName=user.getLastName();
		String fullName=firstName+ " "+lastName;
		model.addAttribute("fullName", fullName);
		return "frontend/new_business_entity";
		}catch(RuntimeException Ex) {
			attributes.addFlashAttribute("message","Business Entity not found!!");
			return "redirect:/entities";
		}
	}

}
