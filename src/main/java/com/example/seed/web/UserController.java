package com.example.seed.web;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.seed.entity.Role;
import com.example.seed.entity.User;
import com.example.seed.service.RoleService;
import com.example.seed.service.UserService;
import com.example.seed.web.dto.UserDto;

@Controller
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	@Autowired
	ModelMapper mapper;
	@GetMapping
	public String users(Model model) {
		return viewPage(model,1,"firstName","asc");
	}
	
	@GetMapping("/page/{pageNum}")
	public String viewPage(Model model,@PathVariable(name="pageNum") int pageNum, @Param("sortField") String sortField,
	        @Param("sortDir") String sortDir) {
		Page<User> page=userService.getAllUsers(pageNum,sortField,sortDir);
		List<User> listUsers=page.getContent();
		
		model.addAttribute("currentPage", pageNum);
	    model.addAttribute("totalPages", page.getTotalPages());
	    model.addAttribute("totalItems", page.getTotalElements());
	    
	    model.addAttribute("sortField", sortField);
	    model.addAttribute("sortDir", sortDir);
	    model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
	    
		model.addAttribute("listUsers",listUsers);
		return "backend/users";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable(name="id") long id,RedirectAttributes attributes) {
		try {
			userService.deleteUser(id);
			attributes.addFlashAttribute("message", "User deleted successfully");
			
		}catch(RuntimeException ex) {
			attributes.addFlashAttribute("message", ex.getMessage());
		}
		return "redirect:/users";
	}
	
	@GetMapping("/edit/{id}")
	public String editUser(@Valid @PathVariable(name="id") long id,Model model,RedirectAttributes attributes) {
		try {
			User user=userService.getUserById(id);
			List<Role> roles=roleService.getAllRoles();
			model.addAttribute("user",user);
			model.addAttribute("roles", roles);
			return "backend/edit_user";
		}catch(RuntimeException Ex) {
			 attributes.addFlashAttribute("message", Ex.getMessage());
			 return "redirect:/users";
		}
	}
	@PostMapping("/update")
	public String updateUser(@ModelAttribute("user")  UserDto userDto,RedirectAttributes attribute ) {
		long id=userDto.getId();		
		try {
		User user=userService.getUserById(id);
		user.setEnabled(userDto.isEnabled());
		user.setRoles(userDto.getRoles());
		userService.updateUser(user);
		attribute.addFlashAttribute("message", "User updated successfully!!");
		}catch(RuntimeException Ex) {
			attribute.addFlashAttribute("message", Ex.getMessage());
		}
		return"redirect:/users";
	}
}
