/**
 * 
 */
package com.rng.admin.web.controllers;

import com.rng.admin.security.SecurityUtil;
import com.rng.admin.web.validators.UserValidatorAdmin;
import com.rng.entities.Role;
import com.rng.entities.User;
import com.rng.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Secured(SecurityUtil.MANAGE_USERS)
public class UserControllerAdmin extends AdminBaseController
{
	private static final String viewPrefix = "users/";
	@Autowired protected SecurityService securityService;
	@Autowired private UserValidatorAdmin userValidatorAdmin;
	@Autowired protected PasswordEncoder passwordEncoder;
	
	@Override
	protected String getHeaderTitle()
	{
		return "Manage Users";
	}
	
	@ModelAttribute("rolesList")
	public List<Role> rolesList(){
		return securityService.getAllRoles();
	}
	
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public String listUsers(Model model) {
		List<User> list = securityService.getAllUsers();
		model.addAttribute("users",list);
		return viewPrefix+"users";
	}
	
	@RequestMapping(value="/users/new", method=RequestMethod.GET)
	public String createUserForm(Model model) {
		User user = new User();
		model.addAttribute("user",user);
		//model.addAttribute("rolesList",securityService.getAllRoles());		
		
		return viewPrefix+"create_user";
	}

	@RequestMapping(value="/users", method=RequestMethod.POST)
	public String createUser(@Valid @ModelAttribute("user") User user, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes) {
		userValidatorAdmin.validate(user, result);
		if(result.hasErrors()){
			return viewPrefix+"create_user";
		}
		String password = user.getPassword();
		String encodedPwd = passwordEncoder.encode(password);
		user.setPassword(encodedPwd);
		User persistedUser = securityService.createUser(user);
		logger.debug("Created new User with id : {} and name : {}", persistedUser.getId(), persistedUser.getName());
		redirectAttributes.addFlashAttribute("info", "User created successfully");
		return "redirect:/users";
	}
	
	@RequestMapping(value="/users/{id}", method=RequestMethod.GET)
	public String editUserForm(@PathVariable Integer id, Model model) {
		User user = securityService.getUserById(id);
		Map<Integer, Role> assignedRoleMap = new HashMap<>();
		List<Role> roles = user.getRoles();
		for (Role role : roles)
		{
			assignedRoleMap.put(role.getId(), role);
		}
		List<Role> userRoles = new ArrayList<>();
		List<Role> allRoles = securityService.getAllRoles();
		for (Role role : allRoles)
		{
			if(assignedRoleMap.containsKey(role.getId())){
				userRoles.add(role);
			} else {
				userRoles.add(null);
			}
		}
		user.setRoles(userRoles);
		model.addAttribute("user",user);
		//model.addAttribute("rolesList",allRoles);		
		return viewPrefix+"edit_user";
	}
	
	@RequestMapping(value="/users/{id}", method=RequestMethod.POST)
	public String updateUser(@ModelAttribute("user") User user, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes) {
		if(result.hasErrors()){
			return viewPrefix+"edit_user";
		}
		User persistedUser = securityService.updateUser(user);
		logger.debug("Updated user with id : {} and name : {}", persistedUser.getId(), persistedUser.getName());
		redirectAttributes.addFlashAttribute("info", "User updates successfully");
		return "redirect:/users";
	}

}
