/**
 * 
 */
package com.rng.admin.web.controllers;

import com.rng.admin.security.SecurityUtil;
import com.rng.admin.web.service.SampleService;
import com.rng.catalog.CatalogService;
import com.rng.entities.Sample;
import com.rng.entities.Subject;
import com.rng.entities.Tests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;


@Controller
@Secured(SecurityUtil.MANAGE_SAMPLES)
public class SampleControllerAdmin extends AdminBaseController
{
	private static final String viewPrefix = "samples/";
	@Autowired
	private SampleService sampleService;
	@Autowired
	private CatalogService catalogService;

	
	@Override
	protected String getHeaderTitle()
	{
		return "Manage Samples";
	}
	
	@ModelAttribute("subjectList")
	public List<Subject> subjectList()
	{
		return catalogService.getAllSubjects();
	}
	@ModelAttribute("testsList")
	public List<Tests> testsList()
	{
		return catalogService.getAllTests();
	}
	
	@RequestMapping(value="/samples", method=RequestMethod.GET)
	public String listSamples(Model model) {
		model.addAttribute("samples",sampleService.getAllSamples());
		return viewPrefix+"samples";
	}

	@RequestMapping(value="/sample/new", method=RequestMethod.GET)
	public String createSample(Model model) {
		Sample sample = new Sample();
		model.addAttribute("sample",sample);
		model.addAttribute("subjectList",catalogService.getAllSubjects());
		model.addAttribute("categoryList",catalogService.getAllCategories());
		return viewPrefix+"create_sample";
	}

	@RequestMapping(value="/sample", method=RequestMethod.POST)
	public String createSample(@Valid @ModelAttribute("sample") Sample sample,
			Model model, RedirectAttributes redirectAttributes) {

		Sample persistedSample = sampleService.createSample(sample);
		persistedSample.setId(sample.getId());
		logger.debug("Created new sample with id : {} ", persistedSample.getId());
		redirectAttributes.addFlashAttribute("info", "Sample created successfully");
		return "redirect:/samples";
	}
	
	@RequestMapping(value="/sample/{id}", method=RequestMethod.GET)
	public String editSampleForm(@PathVariable Integer id, Model model) {
		Sample sample = sampleService.getSamplebyId(id);
		model.addAttribute("sample",sample);
		model.addAttribute("subjectList",catalogService.getAllSubjects());
		model.addAttribute("categoryList",catalogService.getAllCategories());
		return viewPrefix+"edit_sample";
	}

	
	@RequestMapping(value="/sample/{id}", method=RequestMethod.POST)
	public String updateSample(@Valid @ModelAttribute("sample") Sample sample,
			Model model, RedirectAttributes redirectAttributes) {

		Sample persistedSample = sampleService.updateSample(sample);
		logger.debug("Updated sample with id : {} ", persistedSample.getId());
		redirectAttributes.addFlashAttribute("info", "Sample updated successfully");
		return "redirect:/samples";
	}

}
