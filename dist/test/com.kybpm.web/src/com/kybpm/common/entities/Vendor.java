package com.kybpm.common.entities;
// Generated Jan 19, 2009 5:27:42 PM by Hibernate Tools 3.2.2.GA

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "vendor", catalog = "jb336")
public class Vendor {
	
	private Integer vendorId;
	private String vendorName;
	
	public Vendor() {
	}

	public Vendor(Integer vendorId, String vendorName) {
		super();
		this.vendorId = vendorId;
		this.vendorName = vendorName;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "vendor_id", unique = true, nullable = false)
	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	@Column(name = "vendor_name", nullable = false, length = 45)
	@NotNull
	@Length(max = 45)
	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
}
