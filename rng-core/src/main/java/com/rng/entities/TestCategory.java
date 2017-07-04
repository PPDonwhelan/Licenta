package com.rng.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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


    @OneToMany(cascade=CascadeType.ALL, mappedBy="id")
    private Set<Samples> samples;

    public TestCategory()
    {
        this.samples = new HashSet<Samples>();

    }
    @ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(
            name="test_to_category",
            joinColumns={@JoinColumn(name="test_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="category_id", referencedColumnName="id")})
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

    public Set<Samples> getSamples() {
        return samples;
    }

    public void setSamples(Set<Samples> samples) {
        this.samples = samples;
    }
}
