/**
 * 
 */
package com.rng.catalog;

import com.rng.RNGException;
import com.rng.entities.Subject;
import com.rng.entities.TestCategory;
import com.rng.entities.Tests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CatalogService {
	@Autowired TestCategoryRepository categoryRepository;
	@Autowired SubjectsRepository subjectsRepository;
	@Autowired TestsRepository testsRepository;
	
	public List<TestCategory> getAllCategories() {
		
		return categoryRepository.findAll();
	}
	
	public List<Subject> getAllSubjects() {
		
		return subjectsRepository.findAll();
	}
	public List<Tests> getAllTests() {

		return testsRepository.findAll();
	}

	public List<Tests> getAllCategoryTests(String category_name) {

		return categoryRepository.getAllCategoryTests(category_name);
	}

	public TestCategory getCategoryByName(String name) {
		return categoryRepository.getByName(name);
	}
	
	public TestCategory getCategoryById(Integer id) {
		return categoryRepository.findOne(id);
	}

	public TestCategory createCategory(TestCategory category) {
		TestCategory persistedCategory = getCategoryByName(category.getName());
		if(persistedCategory != null){
			throw new RNGException("Category "+category.getName()+" already exist");
		}
		return categoryRepository.save(category);
	}

	public TestCategory updateCategory(TestCategory category) {
		TestCategory persistedCategory = getCategoryById(category.getId());
		if(persistedCategory == null){
			throw new RNGException("Category "+category.getId()+" doesn't exist");
		}
		persistedCategory.setDescription(category.getDescription());
		persistedCategory.setName(category.getName());
		return categoryRepository.save(persistedCategory);
	}

	public Subject getSubjectById(Integer id) {
		return subjectsRepository.findOne(id);
	}

	public Subject getSubjectbyEmail(String email) {
		return subjectsRepository.findByEmail(email);
	}

	public Subject createSubject (Subject subject) {
		Subject persistedSubject = getSubjectbyEmail(subject.getEmail());
		if(persistedSubject != null){
			throw new RNGException("Subject SKU "+ subject.getEmail()+" already exist");
		}
		return subjectsRepository.save(subject);
	}
	
	public Subject updateSubject(Subject subject) {
		Subject persistedSubject = getSubjectById(subject.getId());
		if(persistedSubject == null){
			throw new RNGException("Subject "+ subject.getId()+" doesn't exist");
		}
		persistedSubject.setEmail(subject.getEmail());
		persistedSubject.setAge(subject.getAge());
		persistedSubject.setGender(subject.getGender());

		return subjectsRepository.save(persistedSubject);
	}

	public List<Subject> searchSubject(String query) {
		return subjectsRepository.search("%"+query+"%");
	}



	public Tests getTestsById(Integer id) {
		return testsRepository.findOne(id);
	}

	public Tests getTestsByName(String name) {
		return testsRepository.findByName(name);
	}

	public Tests createTests (Tests test) {
		Tests persistedTests = getTestsByName(test.getName());
		if(persistedTests != null){
			throw new RNGException("Test name  "+ test.getName()+" already exist");
		}
		return testsRepository.save(test);
	}

	public Tests updateTests(Tests test) {
		Tests persistedTests = getTestsById(test.getId());
		if(persistedTests == null){
			throw new RNGException("Tests "+ test.getId()+" doesn't exist");
		}
		persistedTests.setName(test.getName());
		persistedTests.setDescription(test.getDescription());
		persistedTests.setResults(test.getResults());

		return testsRepository.save(persistedTests);
	}



}
