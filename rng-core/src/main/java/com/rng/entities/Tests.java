package com.rng.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="tests")
public class Tests implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false,length=1024)
    private String description;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Results> results;




    public Tests()
    {
        this.results = new HashSet<Results>();

    }

    public Integer getId()
    {
        return id;
    }
    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Results> getResults() {
        return results;
    }

    public void setResults(Set<Results> results) {
        this.results = results;
    }



}

