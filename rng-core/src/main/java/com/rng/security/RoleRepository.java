/**
 * 
 */
package com.rng.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rng.entities.Role;


public interface RoleRepository extends JpaRepository<Role, Integer>
{

	Role findByName(String name);

}
