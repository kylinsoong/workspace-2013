package com.redhat.gss.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TestEntiry")
public class TestEntity implements Serializable {

	private static final long serialVersionUID = -956976297976306001L;
	
	private Long id;
	
	private String name;
	
	public TestEntity() {
		this("");
	}

	public TestEntity(String name) {
		super();
		this.name = name;
	}

	@Column
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
