package com.example.seed.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.seed.entity.User;

public interface UserRepo extends JpaRepository<User,Long>{
	User findByEmail(String email);
	User findByVerificationCode(String code);
	User findByResetPasswordToken(String token);
}
