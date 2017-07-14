/**
 * 
 */
package com.rng.site.web.controllers;

import com.rng.catalog.CatalogService;
import com.rng.entities.TestCategory;
import com.rng.entities.Tests;
import com.rng.site.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
public class HomeController extends SiteBaseController
{	
	@Autowired
	private CatalogService catalogService;
	@Autowired
	private ResultService resultService;
	
	@Override
	protected String getHeaderTitle()
	{
		return "Home";
	}
	
	@RequestMapping("/home")
	public String home(Model model)
	{
		List<TestCategory> categories = catalogService.getAllCategories();
		List<Tests> tests= catalogService.getAllTests();
		model.addAttribute("categories", categories);
		model.addAttribute("tests", tests);
		return "home";
	}
	
	@RequestMapping("/categories/{name}")
	public String category(@PathVariable String name, Model model)
	{
		TestCategory category = catalogService.getCategoryByName(name);
//		List<Tests> tests= category.getTests();
//		List<Sample> samples= category.getSamples();
//		List<ArrayList> results=new ArrayList<>();
//		ArrayList<Results> results2=new ArrayList<>();
//		for (int i = 0; i < tests.size()-1; i++) {
//			for (int j=0; j<samples.size()-1; j++) {
//				results2 =(ArrayList<Results>)resultService.getAllResultsBySampleCategoryTest(samples.get(j).getId(),category.getId(),tests.get(i).getId());
//			}
//			results.add(results2);
//		}
		model.addAttribute("category", category);
		//model.addAttribute("results", results);
		return "category";
	}
	
}
