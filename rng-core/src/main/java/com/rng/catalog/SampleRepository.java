package com.rng.catalog;

import com.rng.entities.Sample;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SampleRepository extends JpaRepository<Sample, Integer> {
    Sample findById(Integer id);
//    @Query("select p from Sample p where p.subject like ?1")
//    List<Sample> searchBySubject(String query);
    List<Sample> findBySubjectId(Integer id);


}
