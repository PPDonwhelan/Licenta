/**
 * 
 */
package com.rng.site.service;

import com.rng.catalog.CatalogService;
import com.rng.catalog.ResultRepository;
import com.rng.entities.Results;
import com.rng.entities.Sample;
import com.rng.entities.TestCategory;
import com.rng.entities.Tests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class ResultService {
	@Autowired
	ResultRepository resultRepository;
	@Autowired
	private CatalogService catalogService;
	Analyze analiza= new Analyze();

	public Results createResult(Results result) {

		Tests test=result.getTest();
		Sample sample=result.getSample();
		System.out.println(sample.getSamples());
		System.out.println(sample.getSamples().replaceAll("\\s+",""));
		String[] sam=sample.getSamples().replaceAll("\\s+","").split(",");
		int[] sampl=new int[sam.length];
		for (int i=0; i<sam.length-1; i++) {
			sampl[i] = Integer.parseInt(sam[i]);
		}
		System.out.println(test.getName());
		Object[] res=analiza.analyze(test.getName(),sampl);
		result.setP_value((Double)res[1]);
		result.setLow((Double)res[2]);
		result.setHigh((Double)res[3]);

		return resultRepository.save(result);
	}

	public List<Results> getAllResults() {
		return resultRepository.findAll();
	}
	public List<Results> getAllResultsBySample(Integer id) {
		return resultRepository.getAllResultsBySampleId(id);
	}
	public Results getResultById(Integer id) {
		return resultRepository.findOne(id);
	}


	public List<Results> getResultsByTestAndSampleId(Integer test_id, Integer sample_id) {
		List<Results> result=resultRepository.getAllResultsBySampleId(sample_id);
		List<Results> actual_results=new ArrayList<Results>();
		int j=0;
		int ok=0;
		for (int i =0; i<result.size(); i++){
			if(test_id==result.get(i).getTest().getId())
			{
				actual_results.add(j,result.get(i));
				j+=1;
				ok=1;
			}
		}
		if (ok==0){ return null;}
		return actual_results;
	}

	public List<Results> getAllResultsBySampleAndCategory(Integer category_id, Integer sample_id) {
		TestCategory category=catalogService.getCategoryById(category_id);
		List<Tests> tests= category.getTests();
		List<Results> result=new ArrayList<Results>();
		int ok=0;
		for (int i =0; i< tests.size(); i++) {
			List<Results> try_found=getResultsByTestAndSampleId(tests.get(i).getId(), sample_id);
			if(try_found!=null){
				ok=1;
				result.addAll(try_found);
			}

		}
		if (ok==0){ return null;}
		return result;
	}
	public List<Results> getAllResultsBySampleCategoryTest( Integer sample_id,Integer category_id, Integer test_id) {
		TestCategory category=catalogService.getCategoryById(category_id);
		List<Tests> tests= category.getTests();
		List<Results> result=new ArrayList<Results>();
		int ok=0;
		for (int i =0; i< tests.size(); i++) {
			if (tests.get(i).getId()==test_id) {
				List<Results> try_found = getResultsByTestAndSampleId(tests.get(i).getId(), sample_id);
				if (try_found != null) {
					ok = 1;
					result.addAll(try_found);
				}
			}

		}
		if (ok==0){ return null;}
		return result;
	}
}
