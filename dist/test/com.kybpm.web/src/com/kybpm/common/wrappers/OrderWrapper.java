package com.kybpm.common.wrappers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kybpm.common.entities.Address;
import com.kybpm.common.entities.Customer;
import com.kybpm.common.wrappers.ProductOrderWrapper;

public class OrderWrapper {

	private Integer orderId;
	private Address addressByBillAddressId;
	private Customer customerId;
	private Address addressByShipAddressId;
	private Date orderTimestamp;
	private String status;
	private List<ProductOrderWrapper> productOrders = new ArrayList<ProductOrderWrapper>(0);

	public OrderWrapper() {
	}

	public OrderWrapper(Address addressByBillAddressId, Customer customer,
			Address addressByShipAddressId, Date orderTimestamp, String status) {
		this.addressByBillAddressId = addressByBillAddressId;
		this.customerId = customer;
		this.addressByShipAddressId = addressByShipAddressId;
		this.orderTimestamp = orderTimestamp;
		this.status = status;
	}
	public OrderWrapper(Address addressByBillAddressId, Customer customer,
			Address addressByShipAddressId, Date orderTimestamp, String status,
			List<ProductOrderWrapper> productOrders) {
		this.addressByBillAddressId = addressByBillAddressId;
		this.customerId = customer;
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
		return this.customerId;
	}

	public void setCustomer(Customer customer) {
		this.customerId = customer;
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

	public List<ProductOrderWrapper> getProductOrders() {
		return this.productOrders;
	}

	public void setProductOrders(List<ProductOrderWrapper> productOrders) {
		this.productOrders = productOrders;
	}

}
