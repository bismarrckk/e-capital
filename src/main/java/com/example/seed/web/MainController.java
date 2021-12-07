package com.example.seed.web;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.seed.entity.User;
import com.example.seed.service.ApplicationService;
import com.example.seed.service.UserService;
import com.example.seed.util.BaseUrlUtility;
import com.example.seed.web.dto.FloatingApplication;
import com.example.seed.web.dto.UserDto;

import net.bytebuddy.utility.RandomString;


@Controller
public class MainController {
	@Autowired
	UserService userService;
	@Autowired
	ModelMapper mapper;
	@Autowired
	ApplicationService applicationService;
	@GetMapping("/")
	public String showHomePage(Model model) {
		Page<FloatingApplication> applications=applicationService.getNewFloatingApplications();
		model.addAttribute("applications", applications);
		return "frontend/index"; 
	}
	
	@GetMapping("/about")
	public String showAboutPage() {
		return "frontend/about";
	}
	
	@GetMapping("/contact")
	public String showContactPage() {
		return "frontend/contact";
	}
	@GetMapping("/login")
	public String showLoginPage() {
		return "frontend/login";
	}
	
	@GetMapping("/register")
	public String showRegistrationPage(Model model) {
		UserDto userDto=new UserDto();
		model.addAttribute("user", userDto);
		return "frontend/register";
	}
	@PostMapping("/save")
	public String saveUser(@Valid @ModelAttribute("user") UserDto userDto,BindingResult bindingResult,
			HttpServletRequest request,RedirectAttributes attributes) {
		User existing=userService.getUserByEmail(userDto.getEmail());
		if (existing != null) {
            bindingResult.rejectValue("email", null, "There is an account already registered with this email");
        }
		
		if(bindingResult.hasErrors()) {
			return "frontend/register";
		}
		String url = BaseUrlUtility.getSiteURL(request);
		String role=userDto.getAccountType();
		User user=mapper.map(userDto, User.class);
		try {
			userService.save(user, url,role);
		} catch (UnsupportedEncodingException e) {
			attributes.addFlashAttribute("message","Sorry could not send a verification to your mail,please contact our support team!");
		} catch (MessagingException e) {
			attributes.addFlashAttribute("message","Sorry could not send a verification to your mail,please contact our support team!");
		}
		attributes.addFlashAttribute("message","Registration successful, a verification link has been e-mailed to you!");
		return "redirect:/register";
	}
	
	@GetMapping("/verify")
	public String verifyEmail(@Param("code") String code,RedirectAttributes attributes) {
		boolean verification=userService.verifyCode(code);
		if(verification) {
			attributes.addFlashAttribute("message","Your account has been verified successfully!!");
			return "redirect:/login";
		}
		else {
			attributes.addFlashAttribute("message","Sorry verification failed,please contact our support team!!");
			return "redirect:/login";
		}
	}
	
	@GetMapping("/forgot-password")
	public String showForgotPasswordForm(){
		return "frontend/forgot_password";
	}
	@PostMapping("/forgot-password")
	public String processForgotPassword(HttpServletRequest request, Model model) {
		String email = request.getParameter("email");
	    String token = RandomString.make(30);
	     
	    try {
	        userService.updateResetPasswordToken(token, email);
	        String resetPasswordLink =BaseUrlUtility.getSiteURL(request) + "/reset-password?token=" + token;
	        userService.sendResetEmail(email, resetPasswordLink);
	        model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
	        return "frontend/forgot_password";
	    }
	    catch (RuntimeException ex) {
	    	 model.addAttribute("error", ex.getMessage());
		        return "frontend/forgot_password";
	        }
	    catch (UnsupportedEncodingException | MessagingException e) {
	    	 model.addAttribute("error2", "Sorry an error occurred while sending password reset link");
		        return "frontend/forgot_password";
	    }
	         
	    
	}
	@GetMapping("/reset-password")
	public String showResetPasswordForm(@Param(value = "token") String token, RedirectAttributes attributes) {
	    User user = userService.getByResetPasswordToken(token);
	    	     
	    if (user == null) {
	        attributes.addFlashAttribute("message", "Invalid Token");
	        return "redirect:/reset-password";
	    }
	     
	    return "frontend/reset_password";
	}
	
	@PostMapping("/reset-password")
	public String processResetPassword(HttpServletRequest request, RedirectAttributes attributes) {
	    String token = request.getParameter("token");
	    String password = request.getParameter("password");
	     
	    User user = userService.getByResetPasswordToken(token);
	   	     
	    if (user == null) {
	    	attributes.addFlashAttribute("message", "Invalid Token");
	    	 return "redirect:/reset-password";
	    } else {           
	        userService.updatePassword(user, password);
	         
	    }
	    attributes.addFlashAttribute("message", "Password reset successful!!");
	    return "redirect:/login";
	}
	
}
