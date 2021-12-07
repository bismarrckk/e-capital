package com.example.seed.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.seed.entity.Role;
import com.example.seed.repository.RoleRepo;
@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	RoleRepo roleRepo;
	@Override
	public List<Role> getAllRoles() {

		return roleRepo.findAll();
	}

}
