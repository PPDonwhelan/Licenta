/**
 * 
 */
package com.rng.admin.web.controllers;

import com.rng.admin.security.SecurityUtil;
import com.rng.admin.web.validators.SubjectValidator;
import com.rng.catalog.CatalogService;
import com.rng.entities.Subject;
import com.rng.entities.TestCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;


@Controller
@Secured(SecurityUtil.MANAGE_SUBJECTS)
public class SubjectController extends AdminBaseController
{
	private static final String viewPrefix = "subject/";
	@Autowired
	private CatalogService catalogService;
	
	@Autowired private SubjectValidator subjectValidator;
	
	@Override
	protected String getHeaderTitle()
	{
		return "Manage subject";
	}
	
	@ModelAttribute("categoriesList")
	public List<TestCategory> categoriesList()
	{
		return catalogService.getAllCategories();
	}
	
	@RequestMapping(value="/subjects", method=RequestMethod.GET)
	public String listProducts(Model model) {
		model.addAttribute("subject",catalogService.getAllSubjects());
		return viewPrefix+"subject";
	}

	@RequestMapping(value="/subjects/new", method=RequestMethod.GET)
	public String createSubject(Model model) {
		Subject subject = new Subject();
		model.addAttribute("subject",subject);
		//model.addAttribute("categoriesList",catalogService.getAllCategories());
		return viewPrefix+"create_subject";
	}

	@RequestMapping(value="/subjects", method=RequestMethod.POST)
	public String createProduct(@Valid @ModelAttribute("subject") Subject subject, BindingResult result,
			Model model, RedirectAttributes redirectAttributes) {
		subjectValidator.validate(subject, result);
		if(result.hasErrors()){
			return viewPrefix+"create_subject";
		}
		Subject persistedSubject = catalogService.createSubject(subject);
		subject.setId(subject.getId());
		logger.debug("Created new subject with id : {} and name : {}", persistedSubject.getId(), persistedSubject.getEmail());
		redirectAttributes.addFlashAttribute("info", "Subject created successfully");
		return "redirect:/subjects";
	}
	
	@RequestMapping(value="/subjects/{id}", method=RequestMethod.GET)
	public String editProductForm(@PathVariable Integer id, Model model) {
		Subject subject = catalogService.getSubjectById(id);
		model.addAttribute("subject",subject);
		return viewPrefix+"edit_subject";
	}

	
	@RequestMapping(value="/subjects/{id}", method=RequestMethod.POST)
	public String updateProduct(@Valid @ModelAttribute("subject") Subject subject, BindingResult result,
			Model model, RedirectAttributes redirectAttributes) {
		subjectValidator.validate(subject, result);
		if(result.hasErrors()){
			return viewPrefix+"edit_subject";
		}
		Subject persistedSubject = catalogService.updateSubject(subject);
		logger.debug("Updated subject with id : {} and name : {}", persistedSubject.getId(), persistedSubject.getEmail());
		redirectAttributes.addFlashAttribute("info", "Subject updated successfully");
		return "redirect:/subjects";
	}

}
