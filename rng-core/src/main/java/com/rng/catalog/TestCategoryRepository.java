package com.rng.catalog;

import com.rng.entities.TestCategory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TestCategoryRepository extends JpaRepository<TestCategory, Integer> {
    TestCategory getByName(String name);
   //@Query("select  t from Tests t join t.tests CategoryTests  where t.name like ?1")
   //List<Tests> getAllTestsByCategoryName(String category_name);
}
