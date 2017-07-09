/**
 * 
 */
package com.rng.site.service;

import com.rng.catalog.SampleRepository;
import com.rng.entities.Sample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class SampleService {
	@Autowired
	SampleRepository sampleRepository;

	public List<Sample> findBySubject(Integer  id) {
		System.out.println(id);
		return sampleRepository.findBySubjectId(id);
	}

	public Sample createSample(Sample sample) {
		return sampleRepository.save(sample);
	}

	public List<Sample> getAllSamples() {
		return sampleRepository.findAll();
	}

	public Sample getSampleById(Integer id) {
		return sampleRepository.findOne(id);
	}



}
