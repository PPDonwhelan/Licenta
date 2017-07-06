package com.rng.catalog;

import com.rng.entities.TestCategory;
import com.rng.entities.Tests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TestCategoryRepository extends JpaRepository<TestCategory, Integer> {
    TestCategory getByName(String name);
   @Query("select  t from TestCategory t join t.tests Tests  where t.name like ?1")
   List<Tests> getAllCategoryTests(String category_name);
}
