package com.kybpm.common.entities;
// Generated Jan 19, 2009 5:27:42 PM by Hibernate Tools 3.2.2.GA

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * ProductType generated by hbm2java
 */
@Entity
@Table(name = "product_type", catalog = "jb336")
public class ProductType implements java.io.Serializable {

	private Integer productTypeId;
	private String productType;
	//private Set<Product> products = new HashSet<Product>(0);

	public ProductType() {
	}

	public ProductType(String productType) {
		this.productType = productType;
	}
	public ProductType(String productType, Set<Product> products) {
		this.productType = productType;
		//this.products = products;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "product_type_id", unique = true, nullable = false)
	public Integer getProductTypeId() {
		return this.productTypeId;
	}

	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}

	@Column(name = "product_type", nullable = false, length = 45)
	@NotNull
	@Length(max = 45)
	public String getProductType() {
		return this.productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
	/*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "productType")
	public Set<Product> getProducts() {
		return this.products;
	}*/

	/*public void setProducts(Set<Product> products) {
		this.products = products;
	}*/

}