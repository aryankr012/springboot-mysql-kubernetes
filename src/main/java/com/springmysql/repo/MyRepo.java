package com.springmysql.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springmysql.entity.User;

public interface MyRepo extends JpaRepository<User, Integer> {
	
	// Custom query methods can be defined here if needed

}
