package com.example.seed.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.seed.entity.Role;

public interface RoleRepo extends JpaRepository<Role,Integer>{
	Role findByName(String name);
}
