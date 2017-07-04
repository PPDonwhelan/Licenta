package com.rng.entities;

import javax.persistence.*;

@Entity
@Table(name="results")
public class Results {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="id_test")
    private Tests test;

    @Column(nullable=false)
    private float p_value;

    @Column(nullable=false)
    private float low;

    @Column(nullable=false)
    private float high;

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

    public float getP_value() {
        return p_value;
    }

    public void setP_value(float p_value) {
        this.p_value = p_value;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }
}
