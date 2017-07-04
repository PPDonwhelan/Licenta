package com.rng.catalog;

import com.rng.entities.TestCategory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TestCategoryRepository extends JpaRepository<TestCategory, Integer> {
    TestCategory getByName(String name);
}
