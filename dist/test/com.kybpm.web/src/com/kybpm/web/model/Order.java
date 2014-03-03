package com.kybpm.web.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Order implements java.io.Serializable {

	private Integer orderId;
	private Address addressByBillAddressId;
	private Customer customer;
	private Address addressByShipAddressId;
	private Date orderTimestamp;
	private String status;
	private List<ProductOrder> productOrders = new ArrayList<ProductOrder>(0);

	public static final String ORDER_STATUS_NEW = "NEW";
	
	public Order() {
	}
	
	/**
	 * minimal constructor
	 * @param orderId
	 */
	public Order(Integer orderId) {
		this.orderId = orderId;
	}

	public Order(Integer orderId, Address addressByBillAddressId, Customer customer,
			Address addressByShipAddressId, Date orderTimestamp, String status) {
		this.orderId = orderId;
		this.addressByBillAddressId = addressByBillAddressId;
		this.customer = customer;
		this.addressByShipAddressId = addressByShipAddressId;
		this.orderTimestamp = orderTimestamp;
		this.status = status;
	}
	public Order(Integer orderId, Address addressByBillAddressId, Customer customer,
			Address addressByShipAddressId, Date orderTimestamp, String status,
			List<ProductOrder> productOrders) {
		this.orderId = orderId;
		this.addressByBillAddressId = addressByBillAddressId;
		this.customer = customer;
		this.addressByShipAddressId = addressByShipAddressId;
		this.orderTimestamp = orderTimestamp;
		this.status = status;
		this.productOrders = productOrders;
	}

	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Address getAddressByBillAddressId() {
		return this.addressByBillAddressId;
	}

	public void setAddressByBillAddressId(Address addressByBillAddressId) {
		this.addressByBillAddressId = addressByBillAddressId;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Address getAddressByShipAddressId() {
		return this.addressByShipAddressId;
	}

	public void setAddressByShipAddressId(Address addressByShipAddressId) {
		this.addressByShipAddressId = addressByShipAddressId;
	}

	public Date getOrderTimestamp() {
		return this.orderTimestamp;
	}

	public void setOrderTimestamp(Date orderTimestamp) {
		this.orderTimestamp = orderTimestamp;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ProductOrder> getProductOrders() {
		return this.productOrders;
	}

	public void setProductOrders(List<ProductOrder> productOrders) {
		this.productOrders = productOrders;
	}
	
	public com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.Order toOrderServiceOrder() {
		com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.Order wsOrder = 
				new com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.Order();
		com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.ProductOrder[] wsProductOrders = 
			new com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.ProductOrder[this.productOrders.size()];
		for(int i = 0; i < this.productOrders.size(); i++) {
			wsProductOrders[i] = this.productOrders.get(i).toOrderServiceProductOrder();
		}
		wsOrder.setProductOrders(wsProductOrders);
		wsOrder.setOrderId(this.orderId);
		Calendar orderTimeStamp = Calendar.getInstance();
		if(this.orderTimestamp != null) orderTimeStamp.setTime(this.orderTimestamp);
		wsOrder.setOrderTimestamp(orderTimeStamp);
		wsOrder.setStatus(this.status);
		com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.Customer wsCustomer = 
				new com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.Customer();
		wsCustomer.setCustomerId(this.customer.getCustomerId());
		wsOrder.setCustomer(wsCustomer);
		com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.Address wsBAddress = 
				new com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.Address();
		wsBAddress.setAddressId(this.addressByBillAddressId.getAddressId());
		wsOrder.setAddressByBillAddressId(wsBAddress);
		com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.Address wsSAddress = 
				new com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.Address();
		wsSAddress.setAddressId(this.addressByShipAddressId.getAddressId());
		wsOrder.setAddressByShipAddressId(wsSAddress);
		return wsOrder;
	}
	
	public com.kybpm.web.ws.client.CalculateServiceImplServiceStub.Order toCalculateServiceOrder() {
		com.kybpm.web.ws.client.CalculateServiceImplServiceStub.Order wsOrder = 
				new com.kybpm.web.ws.client.CalculateServiceImplServiceStub.Order();
		com.kybpm.web.ws.client.CalculateServiceImplServiceStub.ProductOrder[] wsProductOrders = 
			new com.kybpm.web.ws.client.CalculateServiceImplServiceStub.ProductOrder[this.productOrders.size()];
		for(int i = 0; i < this.productOrders.size(); i++) {
			wsProductOrders[i] = this.productOrders.get(i).toCalculateServiceProductOrder();
		}
		wsOrder.setProductOrders(wsProductOrders);
		wsOrder.setOrderId(this.orderId);
		Calendar orderTimeStamp = Calendar.getInstance();
		if(this.orderTimestamp != null) orderTimeStamp.setTime(this.orderTimestamp);
		wsOrder.setOrderTimestamp(orderTimeStamp);
		wsOrder.setStatus(this.status);
		com.kybpm.web.ws.client.CalculateServiceImplServiceStub.Customer wsCustomer = 
				new com.kybpm.web.ws.client.CalculateServiceImplServiceStub.Customer();
		wsCustomer.setCustomerId(this.customer.getCustomerId());
		wsOrder.setCustomer(wsCustomer);
		wsOrder.setAddressByBillAddressId(this.customer.getAddress().toCalculateServiceAddress());
		wsOrder.setAddressByShipAddressId(this.customer.getAddress().toCalculateServiceAddress());
		return wsOrder;
	}

}
