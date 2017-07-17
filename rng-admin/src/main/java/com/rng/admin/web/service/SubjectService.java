/**
 * 
 */
package com.rng.admin.web.service;

import com.rng.RNGException;
import com.rng.catalog.SubjectsRepository;
import com.rng.entities.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SubjectService
{
	@Autowired
	SubjectsRepository subjectsRepository;

	public Subject findSubjectbyName(String name)
	{
		return subjectsRepository.findByEmail(name);
	}



	public Subject getSubjectbyId(Integer id)
	{
		return subjectsRepository.findOne(id);
	}

	public  List<Subject> getAllSubjects() {
		return subjectsRepository.findAll();
	}

	public Subject createSubject(Subject subject)
	{
		Subject new_subject = getSubjectbyId(subject.getId());
		if(new_subject != null){
			throw new RNGException("Id "+subject.getId()+" already in use");
		}


		return subjectsRepository.save(new_subject);
	}

	public Subject updateSubject(Subject subject)
	{
		Subject persistedSubject = getSubjectbyId(subject.getId());
		if(persistedSubject == null){
			throw new RNGException("Subject "+subject.getId()+" doesn't exist");
		}


		return subjectsRepository.save(persistedSubject);
	}

}
