/**
 * 
 */
package com.rng.admin.web.validators;

import com.rng.catalog.CatalogService;
import com.rng.entities.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SubjectValidatorAdmin implements Validator
{
	@Autowired protected MessageSource messageSource;
	@Autowired protected CatalogService catalogService;
	
	@Override
	public boolean supports(Class<?> clazz)
	{
		return Subject.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors)
	{
		Subject subject = (Subject) target;
		String email = subject.getEmail();
		Subject p = catalogService.getSubjectbyEmail(email);
		if(p != null){
			errors.rejectValue("email", "error.exists", new Object[]{email}, "Subject email: "+email+" already exists");
		}
	}
	
	
}
