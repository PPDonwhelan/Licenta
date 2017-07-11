package com.rng.entities;

import javax.persistence.*;

@Entity
@Table(name="results")
public class Results {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="id_test")
    private Tests test;

    @Column(nullable=true)
    private Double p_value;

    @Column(nullable=true)
    private Double low;

    @Column(nullable=true)
    private Double high;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="id_sample")
    private Sample sample;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tests getTest() {
        return test;
    }

    public void setTest(Tests test) {
        this.test = test;
    }

    public Double getP_value() {
        return p_value;
    }

    public void setP_value(Double p_value) {
        this.p_value = p_value;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }
}
