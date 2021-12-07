package com.example.seed.service;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.seed.entity.User;
import com.example.seed.repository.RoleRepo;
import com.example.seed.repository.UserRepo;

import net.bytebuddy.utility.RandomString;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	@Autowired
	JavaMailSender javaMailSender;
	@Override
	public void save(User user, String siteURL,String role) throws UnsupportedEncodingException, MessagingException {
		// TODO Auto-generated method stub
		String randomCode=RandomString.make(64);
		user.setVerificationCode(randomCode);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList(roleRepo.findByName(role)));
		userRepo.save(user);
		sendVerificationMail(user,siteURL);
		
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user=userRepo.findByEmail(email);
		if(user==null) {
			throw new UsernameNotFoundException("User with email address"+email+" does not exist");
		}
		return new UserDetailsImpl(user);
	}

	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		User user =userRepo.findByEmail(email);
		return user;
	}

	@Override
	public void sendVerificationMail(User user, String siteURL) throws UnsupportedEncodingException, MessagingException {
		String toAddress = user.getEmail();
	    String fromAddress = "b77kibet@gmail.com";
	    String senderName = "E-Capital";
	    String subject = "Please verify your account";
	    String content = "Dear [[name]],<br>"
	            + "Thankyou for registering with us,Please click the link below to verify your account:<br>"
	            + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
	            + "Thank you,<br>"
	            + "E-Capital Sourcing";
	    
	   
	    MimeMessage message = javaMailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	     
	    helper.setFrom(fromAddress, senderName);
	    helper.setTo(toAddress);
	    helper.setSubject(subject);
	     
	    content = content.replace("[[name]]", user.getFirstName());
	    String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();
	     
	    content = content.replace("[[URL]]", verifyURL);
	     
	    helper.setText(content, true);
	     
	    javaMailSender.send(message);
		
	}

	@Override
	public boolean verifyCode(String code) {
		User user=userRepo.findByVerificationCode(code);
		if(user==null || user.isEnabled()){
			return false;
		}else {
			user.setVerificationCode(null);
			user.setEnabled(true);
			userRepo.save(user);
			return true;
		}
		
	}

	@Override
	public void updateResetPasswordToken(String token, String email) {
		User user=userRepo.findByEmail(email);
		if(user !=null ) {
			user.setResetPasswordToken(token);
			userRepo.save(user);
		}else {
			throw new RuntimeException("Email not found for address :"+ email+" !!");
		}
		
		
	}

	@Override
	public User getByResetPasswordToken(String token) {
		
		return userRepo.findByResetPasswordToken(token);
	}

	@Override
	public void sendResetEmail(String recipientEmail, String link)
			throws UnsupportedEncodingException, MessagingException {
		 MimeMessage message = javaMailSender.createMimeMessage();
		 MimeMessageHelper helper = new MimeMessageHelper(message);
	     
	    helper.setFrom("b77kibet@gmail.com", "E-Capital Sourcing");
	    helper.setTo(recipientEmail);
	     
	    String subject = "Here's the link to reset your password";
	     
	    String content = "<p>Hello,</p>"
	            + "<p>You have requested to reset your password.</p>"
	            + "<p>Click the link below to change your password:</p>"
	            + "<p><a href=\"" + link + "\">Change my password</a></p>"
	            + "<br>"
	            + "<p>Ignore this email if you remember your password, "
	            + "or if you have not made the request.</p>";
	     
	    helper.setSubject(subject);
	     
	    helper.setText(content, true);
	     
	    javaMailSender.send(message);
		
	}

	@Override
	public void updatePassword(User user, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        userRepo.save(user);

	}

	@Override
	public void deleteUser(long id) {
		User user=getUserById(id);
		userRepo.delete(user);
		
	}

	@Override
	public void updateUser(User user) {
		userRepo.save(user);
		
	}

	@Override
	public User getUserById(long id) {
		Optional<User> optional=userRepo.findById(id);
		User user=null;
		if(optional.isPresent()) {
			user=optional.get();
		}else {
			throw new RuntimeException("User not found!!");
		}
		return user;
	}

	@Override
	public Page<User> getAllUsers(int pageNum, String sortField, String sortDir) {
		int pageSize=10;
		Pageable pageable=PageRequest.of(pageNum - 1, pageSize,sortDir.equals("asc") ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending());
		return userRepo.findAll(pageable);
	}

	@Override
	public long getNumberOfUsers() {
		return userRepo.count();
		
	}
	@Override
	public void updatePassword(String pwd, String email) {
		User user=getUserByEmail(email);
		user.setPassword(pwd);
		userRepo.save(user);
		
		
	}

}
