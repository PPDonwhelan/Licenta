/**
 * 
 */
package com.rng.site.web.controllers;

import com.rng.catalog.CatalogService;
import com.rng.entities.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class SubjectsController extends SiteBaseController
{
	@Autowired
	private CatalogService catalogService;

	@Override
	protected String getHeaderTitle()
	{
		return "Subject";
	}

	@RequestMapping("/subjects/{email}")
	public String subject(@PathVariable String email, Model model)
	{
		Subject subject = catalogService.getSubjectbyEmail(email);
		model.addAttribute("subject", subject);
		return "subject";
	}

	@RequestMapping("/subjects")
	public String searchProducts(@RequestParam(name="name", defaultValue="") String query, Model model)
	{
		List<Subject> subjects = catalogService.searchSubject(query);
		model.addAttribute("subjects", subjects);
		return "subjects";
	}
}
