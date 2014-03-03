package com.kybpm.web.model;

import java.io.Serializable;

public class ProductOrder implements Serializable{

	private Integer productOrderId;
	private Order order;
	private Product product;
	private long quantity;

	public ProductOrder() {
	}

	public ProductOrder(Integer productOrderId, Order order, Product product, long quantity) {
		this.productOrderId = productOrderId;
		this.order = order;
		this.product = product;
		this.quantity = quantity;
	}

	public Integer getProductOrderId() {
		return this.productOrderId;
	}

	public void setProductOrderId(Integer productOrderId) {
		this.productOrderId = productOrderId;
	}

	public Order getOrder() {
		return this.order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public long getQuantity() {
		return this.quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public double getTotalCost() {
		return this.quantity*this.product.getPrice();
	}
	
	public com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.ProductOrder toOrderServiceProductOrder() {
		com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.ProductOrder wsProductOrder = 
				new com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.ProductOrder();
		com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.Order wsOrder = 
				new com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.Order();
		wsOrder.setOrderId(0);
		wsProductOrder.setOrder(wsOrder);
		wsProductOrder.setQuantity(this.quantity);
		wsProductOrder.setProduct(this.product.toOrderServiceProduct());
		return wsProductOrder;
	}
	
	public com.kybpm.web.ws.client.CalculateServiceImplServiceStub.ProductOrder toCalculateServiceProductOrder() {
		com.kybpm.web.ws.client.CalculateServiceImplServiceStub.ProductOrder wsProductOrder = 
				new com.kybpm.web.ws.client.CalculateServiceImplServiceStub.ProductOrder();
		com.kybpm.web.ws.client.CalculateServiceImplServiceStub.Order wsOrder = 
				new com.kybpm.web.ws.client.CalculateServiceImplServiceStub.Order();
		wsOrder.setOrderId(0);
		wsProductOrder.setOrder(wsOrder);
		wsProductOrder.setQuantity(this.quantity);
		wsProductOrder.setProduct(this.product.toCalculateServiceProduct());
		return wsProductOrder;
	}
}
