/**
 * 
 */
package com.rng.site.web.controllers;

import com.rng.catalog.CatalogService;
import com.rng.entities.Results;
import com.rng.entities.Sample;
import com.rng.entities.TestCategory;
import com.rng.entities.Tests;
import com.rng.site.service.ResultService;
import com.rng.site.service.SampleService;
import com.rng.site.web.validators.ResultValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;


@Controller
public class SamplesController extends SiteBaseController
{
	@Autowired
	private SampleService sampleService;
	@Autowired
	private ResultService resultService;
	@Autowired
	private CatalogService catalogService;
	@Autowired private ResultValidator resultValidator;

	@Override
	protected String getHeaderTitle()
	{
		return "Sample";
	}

	@RequestMapping("/sample/{id}")
	public String subject(@PathVariable Integer id, Model model)
	{
		Sample samples = sampleService.getSampleById(id);
		model.addAttribute("samples", samples);
		return "sample";
	}

	@RequestMapping("/sample")
	public String getSample(@RequestParam(name="id", defaultValue="") Integer id, Model model)
	{
		//List<Sample> samples = sampleService.getAllSamples();
		List<Sample> samples = sampleService.findBySubject(id);
		model.addAttribute("samples", samples);
		return "sample";
	}

	@RequestMapping(value="/analyze", method=RequestMethod.GET)
	public String analyzeSample(@RequestParam(name="id", defaultValue="") Integer id, Model model)
	{
		//List<Sample> samples = sampleService.getAllSamples();
		Sample sample = sampleService.getSampleById(id);
		model.addAttribute("sample", sample);
		model.addAttribute("result", new Results());
		return "analyze";
	}

	@RequestMapping(value="/analyze", method=RequestMethod.POST)
	public String analyze(@RequestParam(name="sample_id", defaultValue="") Integer sample_id, @Valid @ModelAttribute("test_id")  Integer test_id, BindingResult bindingResult,
						  Model model, RedirectAttributes redirectAttributes)
	{
		String redirect="redirect:/result/"+sample_id.toString();

		resultValidator.validate(test_id,sample_id, bindingResult);
		if(bindingResult.hasErrors()){
			return redirect;
		}

		Sample sample = sampleService.getSampleById(sample_id);
		Tests test= catalogService.getTestsById(test_id);
		Results result=new Results();
		result.setSample(sample);
		result.setTest(test);



		Results persistedResult = resultService.createResult(result);
		logger.debug("Created new result with id : {} ", persistedResult.getId());
		redirectAttributes.addFlashAttribute("info", "Result created successfully");
		return redirect;
	}

	@RequestMapping(value="/analyze_category", method=RequestMethod.POST)
	public String analyz_category(@RequestParam(name="sample_id", defaultValue="") Integer sample_id, @Valid @ModelAttribute("category_id")  Integer category_id, BindingResult bindingResult,
						  Model model, RedirectAttributes redirectAttributes)
	{
		String redirect="redirect:/result?category_id="+category_id.toString()+"&sample_id="+sample_id.toString();
		TestCategory category=catalogService.getCategoryById(category_id);
		List<Tests> tests= category.getTests();
		for (int i =0; i< tests.size(); i++)
		{
			resultValidator.validate(tests.get(i).getId(),sample_id, bindingResult);
			if(bindingResult.hasErrors()){
			}
			else{
				Sample sample = sampleService.getSampleById(sample_id);
				//Tests test= catalogService.getTestsById(tests.get(i).getId());
				Results result=new Results();
				result.setSample(sample);
				result.setTest(tests.get(i));
				Results persistedResult = resultService.createResult(result);
				logger.debug("Created new result with id : {} ", persistedResult.getId());
			}
		}

		redirectAttributes.addFlashAttribute("info", "Result created successfully");
		return redirect;
	}
}

