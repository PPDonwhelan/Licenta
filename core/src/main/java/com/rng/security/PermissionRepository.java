/**
 * 
 */
package com.rng.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rng.entities.Permission;


public interface PermissionRepository extends JpaRepository<Permission, Integer>
{

}
