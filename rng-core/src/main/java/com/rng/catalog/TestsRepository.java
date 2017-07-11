package com.rng.catalog;

import com.rng.entities.Tests;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TestsRepository extends JpaRepository<Tests, Integer> {
    Tests findByName(String name);
    //List<Tests> getAllTestsByCategoryId(Integer id);
}
