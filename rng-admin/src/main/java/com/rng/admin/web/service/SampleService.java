/**
 * 
 */
package com.rng.admin.web.service;

import com.rng.RNGException;
import com.rng.catalog.SampleRepository;
import com.rng.entities.Sample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SampleService
{
	@Autowired
	SampleRepository sampleRepository;
	
	public List<Sample> findSamplebySubject(Integer subject_id)
	{
		return sampleRepository.findBySubjectId(subject_id);
	}



	public Sample getSamplebyId(Integer id)
	{
		return sampleRepository.findOne(id);
	}
	
	public  List<Sample> getAllSamples() {
		return sampleRepository.findAll();
	}
	
	public Sample createSample(Sample sample)
	{
		Sample samples = getSamplebyId(sample.getId());
		if(samples != null){
			throw new RNGException("Id "+sample.getId()+" already in use");
		}

		
		return sampleRepository.save(samples);
	}
	
	public Sample updateSample(Sample sample)
	{
		Sample persistedSample = getSamplebyId(sample.getId());
		if(persistedSample == null){
			throw new RNGException("Sample "+sample.getId()+" doesn't exist");
		}
		
		String  real_sample ="";
		real_sample = sample.getSamples();
		if(real_sample != null){
			persistedSample.setSamples(real_sample);
		}

		return sampleRepository.save(persistedSample);
	}

}
