package com.example.seed.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.seed.entity.User;

public interface UserService extends UserDetailsService{
	void save(User user,String siteURL,String role) throws UnsupportedEncodingException, MessagingException;;
	User getUserByEmail(String email);
	void sendVerificationMail(User user,String siteURL) throws UnsupportedEncodingException, MessagingException;
	boolean verifyCode(String code);
	void updateResetPasswordToken(String token, String email);
	User getByResetPasswordToken(String token);
	void sendResetEmail(String recipientEmail, String link) throws UnsupportedEncodingException, MessagingException;
	void updatePassword(User user,String password);
	void deleteUser(long id);
	void updateUser(User user);
	User getUserById(long id);
	Page<User> getAllUsers(int pageNum,String sortField,String sortDir);
	long getNumberOfUsers();
	void updatePassword(String pwd, String email);
}
