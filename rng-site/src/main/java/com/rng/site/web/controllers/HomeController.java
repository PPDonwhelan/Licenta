/**
 * 
 */
package com.rng.site.web.controllers;

import com.rng.catalog.CatalogService;
import com.rng.entities.TestCategory;
import com.rng.entities.Tests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Controller
public class HomeController extends SiteBaseController
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
			List<Tests> tests = category.getTests();
			List<Tests> previewTests = new ArrayList<>();
			int noOfTestsToDisplay = 10;
			if(tests.size() > noOfTestsToDisplay){
				Iterator<Tests> iterator = tests.iterator();
				for (int i = 0; i < noOfTestsToDisplay; i++)
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
