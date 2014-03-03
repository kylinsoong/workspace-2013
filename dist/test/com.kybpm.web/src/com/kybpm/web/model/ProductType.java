package com.kybpm.web.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductType implements java.io.Serializable {

	private Integer productTypeId;
	private String productType;
	private List<Product> products = new ArrayList<Product>(0);

	public ProductType() {
	}

	public ProductType(String productType) {
		this.productType = productType;
	}
	public ProductType(String productType, List<Product> products) {
		this.productType = productType;
		this.products = products;
	}

	public Integer getProductTypeId() {
		return this.productTypeId;
	}

	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getProductType() {
		return this.productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.ProductType toOrderServiceProductType() {
		com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.ProductType wsProductType =
				new com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.ProductType();
		wsProductType.setProductTypeId(this.productTypeId);
		wsProductType.setProductType(this.productType);
		return wsProductType;
	}
	
	public com.kybpm.web.ws.client.CalculateServiceImplServiceStub.ProductType toCalculateServiceProductType() {
		com.kybpm.web.ws.client.CalculateServiceImplServiceStub.ProductType wsProductType =
				new com.kybpm.web.ws.client.CalculateServiceImplServiceStub.ProductType();
		wsProductType.setProductTypeId(this.productTypeId);
		wsProductType.setProductType(this.productType);
		return wsProductType;
	}
}
