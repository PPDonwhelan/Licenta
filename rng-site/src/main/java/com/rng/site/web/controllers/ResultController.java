package com.rng.site.web.controllers;

import com.rng.entities.Results;
import com.rng.site.service.ResultService;
import com.rng.site.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * Created by MoldovanM2 on 7/7/2017.
 */
@Controller
public class ResultController extends SiteBaseController{

    @Autowired
    private ResultService resultService;
    @Autowired
    private SampleService sampleService;

    @Override
    protected String getHeaderTitle()
    {
        return "Results";
    }

//    @RequestMapping(value="/results", method= RequestMethod.GET)
//    protected String result(Model model)
//    {
//        List<Results> results=resultService.getAllResults();
//        model.addAttribute("results", results);
//        return "result";
//    }



    @RequestMapping(value="/result/{sample_id}", method= RequestMethod.GET)
    protected String result_sample(@PathVariable Integer sample_id,Model model)
    {
        List<Results> results=resultService.getAllResultsBySample(sample_id);
        model.addAttribute("results", results);
        return "result";
    }

    @RequestMapping(value="/result", method= RequestMethod.GET)
    protected String result_category(@RequestParam(name="category_id", defaultValue="") Integer category_id,@RequestParam(name="sample_id", defaultValue="") Integer sample_id, Model model)
    {
        List<Results> results=resultService.getAllResultsBySampleAndCategory(category_id,sample_id);
        model.addAttribute("results", results);
        return "result";
    }

}
