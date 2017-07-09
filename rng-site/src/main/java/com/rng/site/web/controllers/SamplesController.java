/**
 * 
 */
package com.rng.site.web.controllers;

import com.rng.entities.Sample;
import com.rng.site.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class SamplesController extends SiteBaseController
{
	@Autowired
	private SampleService sampleService;

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

	@RequestMapping("/analyze")
	public String analyzeSample(@RequestParam(name="id", defaultValue="") Integer id, Model model)
	{
		//List<Sample> samples = sampleService.getAllSamples();
		Sample sample = sampleService.getSampleById(id);
		model.addAttribute("sample", sample);
		return "analyze";
	}
}

