/**
 * 
 */
package com.rng.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rng.entities.User;


public interface UserRepository extends JpaRepository<User, Integer>
{

	User findByEmail(String email);

}
