/**
 * 
 */
package com.rng.admin.web.validators;

import com.rng.catalog.CatalogService;
import com.rng.entities.Tests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TestsValidatorAdmin implements Validator
{
	@Autowired protected MessageSource messageSource;
	@Autowired protected CatalogService catalogService;
	
	@Override
	public boolean supports(Class<?> clazz)
	{
		return Tests.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors)
	{
		Tests tests = (Tests) target;
		String name = tests.getName();
		Tests p = catalogService.getTestsByName(name);
		if(p != null){
			errors.rejectValue("name", "error.exists", new Object[]{name}, "Tests name: "+name+" already exists");
		}
	}
	
	
}
