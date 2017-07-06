/**
 * 
 */
package com.rng.admin.web.controllers;

import com.rng.admin.security.SecurityUtil;
import com.rng.admin.web.validators.TestsValidator;
import com.rng.catalog.CatalogService;
import com.rng.entities.TestCategory;
import com.rng.entities.Tests;
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
public class TestController extends AdminBaseController
{
	private static final String viewPrefix = "tests/";
	@Autowired
	private CatalogService catalogService;
	
	@Autowired private TestsValidator testsValidator;
	
	@Override
	protected String getHeaderTitle()
	{
		return "Manage tests";
	}
	
	@ModelAttribute("categoriesList")
	public List<TestCategory> categoriesList()
	{
		return catalogService.getAllCategories();
	}
	
	@RequestMapping(value="/tests", method=RequestMethod.GET)
	public String listTests(Model model) {
		model.addAttribute("tests",catalogService.getAllTests());
		return viewPrefix+"tests";
	}

	@RequestMapping(value="/tests/new", method=RequestMethod.GET)
	public String createSubject(Model model) {
		Tests test = new Tests();
		model.addAttribute("tests",test);
		//model.addAttribute("categoriesList",catalogService.getAllCategories());
		return viewPrefix+"create_subject";
	}

	@RequestMapping(value="/tests", method=RequestMethod.POST)
	public String createProduct(@Valid @ModelAttribute("tests") Tests test, BindingResult result,
			Model model, RedirectAttributes redirectAttributes) {
		testsValidator.validate(test, result);
		if(result.hasErrors()){
			return viewPrefix+"create_subject";
		}
		Tests persistedTest = catalogService.createTests(test);
		test.setId(test.getId());
		logger.debug("Created new subject with id : {} and name : {}", persistedTest.getId(), persistedTest.getName());
		redirectAttributes.addFlashAttribute("info", "Test created successfully");
		return "redirect:/tests";
	}
	
	@RequestMapping(value="/tests/{id}", method=RequestMethod.GET)
	public String editProductForm(@PathVariable Integer id, Model model) {
		Tests tests = catalogService.getTestsById(id);
		model.addAttribute("tests",tests);
		return viewPrefix+"edit_tests";
	}

	
	@RequestMapping(value="/tests/{id}", method=RequestMethod.POST)
	public String updateProduct(@Valid @ModelAttribute("tests") Tests tests, BindingResult result,
			Model model, RedirectAttributes redirectAttributes) {
		testsValidator.validate(tests, result);
		if(result.hasErrors()){
			return viewPrefix+"edit_tests";
		}
		Tests persistedTests = catalogService.updateTests(tests);
		logger.debug("Updated tests with id : {} and name : {}", persistedTests.getId(), persistedTests.getName());
		redirectAttributes.addFlashAttribute("info", "Test updated successfully");
		return "redirect:/tests";
	}

}
