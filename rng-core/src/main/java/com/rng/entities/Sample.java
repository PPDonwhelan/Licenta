package com.rng.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="samples")
public class Sample implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="timestamp")
    private Date timestamp;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="id_subject")
    private Subject subject;

    @Column(nullable=false,length=1024)
    private String samples;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="id_test_category")
    private TestCategory category;


//    @OneToMany(cascade=CascadeType.MERGE, mappedBy = "id")
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private List<Results> results;
//
//    public Sample()
//    {
//        this.results = new ArrayList<Results>();
//
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public TestCategory getCategory() {
        return category;
    }

    public void setCategory(TestCategory category) {
        this.category = category;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getSamples() {
        return samples;
    }

    public void setSamples(String samples) {
        this.samples = samples;
    }

//    public List<Results> getResults() {
//        return results;
//    }
//
//    public void setResults(List<Results> results) {
//        this.results = results;
//    }
}
