package com.springmysql.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springmysql.entity.User;
import com.springmysql.service.MyService;

@RestController
public class MyController {
	
	public MyService service;
	
	public MyController(MyService service) {
		this.service = service;
	}
	
	@GetMapping("/")
	public String hello() {
		return "Welcome to Spring Boot with MySQL!";
	}
	
	@GetMapping("/getUser")
	public List<User> getUser() {
		return service.getUser();
	}
	
	@PostMapping("/addUser")
	public User addUser(@RequestBody User user) {
		return service.addUser(user);
	}

}
