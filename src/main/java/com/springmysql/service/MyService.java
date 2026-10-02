package com.springmysql.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springmysql.entity.User;
import com.springmysql.repo.MyRepo;

@Service
public class MyService {

	private MyRepo repository;
	
	 public MyService(MyRepo repository) {
	        this.repository = repository;
	 }
	 
	public List<User> getUser() {
		return repository.findAll();
	}
	
	public User addUser(User user) {
		return repository.save(user);
	}
	
	
}
