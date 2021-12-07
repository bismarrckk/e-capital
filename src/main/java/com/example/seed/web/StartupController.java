package com.example.seed.web;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.example.seed.entity.User;
import com.example.seed.service.ApplicationPhaseService;
import com.example.seed.service.ApplicationService;
import com.example.seed.service.UserService;
import com.example.seed.web.dto.UserDto;

@Controller
@RequestMapping("/dashboard")
public class StartupController {
	@Autowired
	ApplicationService applicationService;
	@Autowired
	ApplicationPhaseService phaseService;
	@Autowired
	UserService userService;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	@GetMapping
	public String showFloatingApplications(Model model) {
		ApplicationPhase phase=phaseService.getByName("FLOATING");
		List<Application> appliactions=applicationService.getApplicationByPhase(phase);
		model.addAttribute("applications", appliactions);
		return"frontend/auth_home";
	}
	@GetMapping("/details/{id}")
	public String startupDeatils(@PathVariable(name="id") long id,Model model,RedirectAttributes attributes) {
		try {
			Application application=applicationService.getApplicationById(id);
			model.addAttribute("applicationDetails", application);
			return"frontend/application_details_home";
		}catch(RuntimeException ex) {
			attributes.addFlashAttribute("message", "Startup not found!!");
			return "redirect:/dashboard";
		}
	}
	@GetMapping("/myStartups")
	public String showStartups() {
		return "redirect:/mystartups";
	}
	@GetMapping("/newStartup")
	public String showBusinessEntities() {
		return "redirect:/entities";
	}
	
	@GetMapping("/profile")
	public String editProfile(Model model,HttpServletRequest request) {
		Principal principal=request.getUserPrincipal();
		String email=principal.getName();
		User user=userService.getUserByEmail(email);
		model.addAttribute("user", user);
		return "frontend/profile";
	}
	@PostMapping("/profile")
	public String updateProfile(@Valid @ModelAttribute("user")  UserDto userDto,BindingResult bindingResult,RedirectAttributes attribute) {
		if(bindingResult.hasErrors()) {
			return "frontend/profile";
		}
		long id=userDto.getId();		
		try {
		User user=userService.getUserById(id);
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setPhoneNumber(userDto.getPhoneNumber());
		user.setIdType(userDto.getIdType());
		user.setIdNumber(userDto.getIdNumber());
		user.setPhysicalAddress(userDto.getPhysicalAddress());

		userService.updateUser(user);
		attribute.addFlashAttribute("message", "Profile updated successfully!!");
		}catch(RuntimeException Ex) {
			attribute.addFlashAttribute("message", Ex.getMessage());
		}
		return "redirect:/dashboard/profile";
	}
	@PostMapping("/changePassword")
	public String updatePassword(HttpServletRequest request,RedirectAttributes attributes) {
		Principal principal=request.getUserPrincipal();
		String email=principal.getName();
		User user=userService.getUserByEmail(email);
		String registeredPwd=user.getPassword();
		
		String currentPwd=request.getParameter("currentPwd");		
		String newPwd=request.getParameter("newPwd");
		String confirmPwd=request.getParameter("confirmPwd");
		
		if((passwordEncoder.matches(currentPwd, registeredPwd))&&(newPwd.equals(confirmPwd))) {
			
				String newPassword=passwordEncoder.encode(confirmPwd);
				userService.updatePassword(newPassword, email);
				
			
			attributes.addFlashAttribute("message", "Password updated successfully!!");
			return "redirect:/dashboard/profile";
		}
		else {
		attributes.addFlashAttribute("message", "Passwords do not match!!");
		return "redirect:/dashboard/profile";
		}
	}
}
