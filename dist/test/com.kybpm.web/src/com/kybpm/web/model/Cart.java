package com.kybpm.web.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("cart")
@Scope(ScopeType.SESSION)
public class Cart extends ItemList implements Serializable{
	
	private Order order;
	private Double shipping;
	private Double tax;
	private Double total;
	private Double grandTotal;
	
	public Cart () {}
	
	public Cart (Order order, Double shipping, Double tax, Double total) {	
		this.order = order;
		this.shipping = shipping;
		this.tax = tax;
		this.total = total;
	}

	@Create
	public void init() {
		this.order = new Order(0);
		this.order.setProductOrders(new ArrayList<ProductOrder>());
		this.shipping = 5.99;
		this.tax = 3.58;
		this.total = 0.0;
	}
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Double getShipping() {
		return shipping;
	}

	public Double getTax() {
		return tax;
	}

	public void setShipping(Double shipping) {
		this.shipping = shipping;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	/**
	 * @return the grandTotal
	 */
	public Double getGrandTotal() {
		return grandTotal;
	}

	/**
	 * @param grandTotal the grandTotal to set
	 */
	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
	}
	
	
}
