/**
 * 
 */
package com.rng.site.web.controllers;

import com.rng.catalog.CatalogService;
import com.rng.entities.TestCategory;
import com.rng.entities.Tests;
import com.rng.entities.Tuple;
import com.rng.site.service.ResultService;
import com.rng.site.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
public class HomeController extends SiteBaseController
{	
	@Autowired
	private CatalogService catalogService;
	@Autowired
	private ResultService resultService;
	@Autowired
	private StatisticsService statisticsService;
	
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
		List<Integer> stats=statisticsService.getStatisticsCategory(category);
		List<Integer> tests_stats=statisticsService.getStatisticsTests(category);
		List<Tuple<Integer,Tests>> stats_corelation=new ArrayList<>();
		for (int i=0; i<tests_stats.size(); i++)
		{
			Tuple<Integer,Tests> val= new Tuple<>(tests_stats.get(i),category.getTests().get(i));
			stats_corelation.add(val);
		}
		List<String> gb=new ArrayList<>();
		gb.add("Good");
		gb.add("Bad");
		List<Tuple<Integer,String>> stats_overall=new ArrayList<>();
		for (int i=0; i<stats.size(); i++)
		{
			Tuple<Integer,String> val= new Tuple<>(stats.get(i),gb.get(i));
			stats_overall.add(val);
		}
		model.addAttribute("category", category);
		model.addAttribute("statistics", stats_corelation);
		model.addAttribute("overall_stats", stats_overall);
		//model.addAttribute("results", results);
		return "category";
	}
	
}

