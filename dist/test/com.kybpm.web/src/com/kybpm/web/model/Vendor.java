package com.kybpm.web.model;

import java.io.Serializable;

public class Vendor implements Serializable {
	
	private Integer vendorId;
	private ProductType vendorType;
	private String vendorName;
	private String vendorDescription;
	
	public Vendor (){	
	}

	public Vendor(Integer vendorId, ProductType vendorType, String vendorName,
			String vendorDescription) {
		this.vendorId = vendorId;
		this.vendorType = vendorType;
		this.vendorName = vendorName;
		this.vendorDescription = vendorDescription;
	}

	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public ProductType getVendorType() {
		return vendorType;
	}
	public void setVendorType(ProductType vendorType) {
		this.vendorType = vendorType;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getVendorDescription() {
		return vendorDescription;
	}
	public void setVendorDescription(String vendorDescription) {
		this.vendorDescription = vendorDescription;
	}
	
	public com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.Vendor toOrderServiceVendor() {
		com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.Vendor wsVendor = 
				new com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.Vendor();
		wsVendor.setVendorId(this.vendorId);
		wsVendor.setVendorName(this.vendorName);
		return wsVendor;
	}
	
	public com.kybpm.web.ws.client.CalculateServiceImplServiceStub.Vendor toCalculateServiceVendor() {
		com.kybpm.web.ws.client.CalculateServiceImplServiceStub.Vendor wsVendor = 
				new com.kybpm.web.ws.client.CalculateServiceImplServiceStub.Vendor();
		wsVendor.setVendorId(this.vendorId);
		wsVendor.setVendorName(this.vendorName);
		return wsVendor;
	}
}
