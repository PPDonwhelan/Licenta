/**
 * 
 */
package com.rng.site.service;

import com.rng.entities.User;
import com.rng.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class UserService {
	@Autowired
	UserRepository customerRepository;
	
	public User getUserByEmail(String email) {
		return customerRepository.findByEmail(email);
	}

	public User createUser(User customer) {
		return customerRepository.save(customer);
	}

	public List<User> getAllUsers() {
		return customerRepository.findAll();
	}

	public User getUserById(Integer id) {
		return customerRepository.findOne(id);
	}



}
