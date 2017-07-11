package com.rng.catalog;

import com.rng.entities.Results;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ResultRepository extends JpaRepository<Results, Integer> {
    Results findById(Integer id);
//    @Query("select p from Sample p where p.subject like ?1")
//    List<Sample> searchBySubject(String query);
    List<Results> getAllResultsBySampleId(Integer id);

    List<Results>  getTestByTestId(Integer id);
}
