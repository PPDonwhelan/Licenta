/**
 * 
 */
package com.rng.catalog;

import com.rng.RNGException;
import com.rng.entities.Subject;
import com.rng.entities.TestCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CatalogService {
	@Autowired TestCategoryRepository categoryRepository;
	@Autowired SubjectsRepository subjectsRepository;
	
	public List<TestCategory> getAllCategories() {
		
		return categoryRepository.findAll();
	}
	
	public List<Subject> getAllSubjects() {
		
		return subjectsRepository.findAll();
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

	public Subject getProductById(Integer id) {
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
		Subject persistedSubject = getProductById(subject.getId());
		if(persistedSubject == null){
			throw new RNGException("Subject "+ subject.getId()+" doesn't exist");
		}
		persistedSubject.setEmail(subject.getEmail());
		persistedSubject.setAge(subject.getAge());
		persistedSubject.setGender(subject.getGender());

		return subjectsRepository.save(persistedSubject);
	}

	public List<Subject> searchProducts(String query) {
		return subjectsRepository.search("%"+query+"%");
	}
}
