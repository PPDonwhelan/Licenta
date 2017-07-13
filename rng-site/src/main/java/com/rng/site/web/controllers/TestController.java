package com.rng.site.web.controllers;

import com.rng.catalog.CatalogService;
import com.rng.entities.Tests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class TestController extends SiteBaseController {


    @Autowired
    private CatalogService catalogService;

    @Override
    protected String getHeaderTitle()
    {
        return "Tests";
    }

	@RequestMapping("/tests/{name}")
	public String test_by_name(@PathVariable String name, Model model)
	{
		Tests tests = catalogService.getTestsByName(name);
		model.addAttribute("tests", tests);
		return "tests";
	}
//    @RequestMapping("/tests/{name}")
//    public String test_by_category(@PathVariable String name, Model model)
//    {
//        Tests tests = catalogService.getTestsByName(name);
//        model.addAttribute("tests", tests);
//        return "tests";
//    }
//    @RequestMapping("/tests")
//    public String searchProducts(@RequestParam(name="name", defaultValue="") String query, Model model)
//    {
//        Tests tests = catalogService.getTestsByName(query);
//        List<Tests> lists_test= new ArrayList<Tests>();
//        lists_test.add(tests);
//        model.addAttribute("tests", lists_test);
//        return "tests";
//    }


}
