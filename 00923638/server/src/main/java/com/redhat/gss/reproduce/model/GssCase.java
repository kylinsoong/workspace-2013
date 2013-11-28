package com.redhat.gss.reproduce.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="GssCase")  
@Table(name="t_GssCase")  
public class GssCase implements Serializable{

	private static final long serialVersionUID = 2570226846499952309L;
	
	private Long id; 
	
	private String name;

	public GssCase(String name) {
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
