/**
 * 
 */
package com.rng.site.security;

import com.rng.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

public class AuthenticatedUser extends org.springframework.security.core.userdetails.User
{

	private static final long serialVersionUID = 1L;
	private User customer;
	
	public AuthenticatedUser(User customer)
	{
		super(customer.getEmail(), customer.getPassword(), getAuthorities(customer));
		this.customer = customer;
	}
	public User getCustomer()
	{
		return customer;
	}
	private static Collection<? extends GrantedAuthority> getAuthorities(User customer)
	{
		Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
		return authorities;
	}
}
