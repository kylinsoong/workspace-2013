package com.kybpm.common.wrappers;

import com.kybpm.common.entities.Order;
import com.kybpm.common.entities.Product;

public class ProductOrderWrapper {

	private Integer productOrderId;
	private Order order;
	private Product product;
	private long quantity;

	public ProductOrderWrapper() {
	}

	public ProductOrderWrapper(Order order, Product product, long quantity) {
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

}
