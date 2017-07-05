package com.rng.site.web.controllers;

import com.rng.catalog.CatalogService;
import com.rng.entities.Tests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class TestController extends JCartSiteBaseController {


    @Autowired
    private CatalogService catalogService;

    @Override
    protected String getHeaderTitle()
    {
        return "Tests";
    }

	@RequestMapping("/tests/{name}")
	public String subject(@PathVariable String name, Model model)
	{
		Tests tests = catalogService.getTestsName(name);
		model.addAttribute("tests", tests);
		return "tests";
	}


}
