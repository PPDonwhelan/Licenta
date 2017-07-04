/**
 * 
 */
package com.rng.site.web.controllers;

import com.rng.catalog.CatalogService;
import com.rng.entities.Subject;
import com.rng.entities.TestCategory;
import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * @author Siva
 *
 */
@Controller
public class HomeController extends JCartSiteBaseController
{	
	@Autowired
	private CatalogService catalogService;
	
	@Override
	protected String getHeaderTitle()
	{
		return "Home";
	}
	
	@RequestMapping("/home")
	public String home(Model model)
	{
		List<TestCategory> previewCategories = new ArrayList<>();
		List<TestCategory> categories = catalogService.getAllCategories();
		for (TestCategory category : categories)
		{
			Set<Test> tests = category.getTests();
			Set<Test> previewTests = new HashSet<>();
			int noOfProductsToDisplay = 4;
			if(tests.size() > noOfProductsToDisplay){
				Iterator<Test> iterator = tests.iterator();
				for (int i = 0; i < noOfProductsToDisplay; i++)
				{
					previewTests.add(iterator.next());
				}
			} else {
				previewTests.addAll(tests);
			}	
			category.setTests(previewTests);
			previewCategories.add(category);
		}
		model.addAttribute("categories", previewCategories);
		return "home";
	}
	
	@RequestMapping("/categories/{name}")
	public String category(@PathVariable String name, Model model)
	{
		TestCategory category = catalogService.getCategoryByName(name);
		model.addAttribute("category", category);
		return "category";
	}
	
}
