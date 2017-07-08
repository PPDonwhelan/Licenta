package com.rng.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="test_category")
public class TestCategory implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false,length=1024)
    private String description;


    @OneToMany(cascade=CascadeType.ALL, mappedBy="category")
    private List<Sample> samples;

    public TestCategory()
    {
        this.samples = new ArrayList<Sample>();

    }
    @ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(
            name="tests_to_category",
            joinColumns={@JoinColumn(name="category_id")},
            inverseJoinColumns={@JoinColumn(name="test_id")})
    private List<Tests> tests;

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

    public List<Tests> getTests() {
        return tests;
    }

    public void setTests(List<Tests> tests) {
        this.tests = tests;
    }

    public List<Sample> getSamples() {
        return samples;
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }
}
