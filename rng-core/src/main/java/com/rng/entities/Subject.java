/**
 * 
 */
package com.rng.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="subjects")
public class Subject implements Serializable
{
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	@Column(name="email",nullable=false)
	private String email;
	@Column(name="age",nullable=false)
	private int age;
	@Column(name="gender",nullable=false)
	private String gender;


	@OneToMany(cascade=CascadeType.ALL, mappedBy="subject")
	private Set<Sample> samples;
	public  Subject()
	{
		this.samples = new HashSet<Sample>();
	}
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public int getAge()
	{
		return age;
	}
	public void setAge(int age)
	{
		this.age = age;
	}
	public String getGender()
	{
		return gender;
	}
	public void setGender(String description)
	{
		this.gender = gender;
	}

	public Set<Sample> getSamples() {
		return samples;
	}

	public void setSamples(Set<Sample> samples) {
		this.samples = samples;
	}
}
