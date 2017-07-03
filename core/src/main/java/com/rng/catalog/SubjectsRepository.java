package com.rng.catalog;

import com.rng.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface SubjectsRepository extends JpaRepository<Subject, Integer> {
    Subject findByEmail(String email);
    @Query("select p from Subject p where p.email like ?1 or p.age like ?1 or p.gender like ?1")
    List<Subject> search(String query);


}
