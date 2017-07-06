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
	
	public User getCustomerByEmail(String email) {
		return customerRepository.findByEmail(email);
	}

	public User createCustomer(User customer) {
		return customerRepository.save(customer);
	}

	public List<User> getAllCustomers() {
		return customerRepository.findAll();
	}

	public User getCustomerById(Integer id) {
		return customerRepository.findOne(id);
	}



}
