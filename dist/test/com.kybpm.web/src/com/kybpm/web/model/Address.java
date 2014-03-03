package com.kybpm.web.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.annotations.Create;

public class Address implements Serializable {

	private Integer addressId;
	private String street1;
	private String street2;
	private String city;
	private String state;
	private String zip;
	private List<Order> ordersForBillAddressId = new ArrayList<Order>(0);
	private List<Customer> customers = new ArrayList<Customer>(0);
	private List<Order> ordersForShipAddressId = new ArrayList<Order>(0);
	private List<PaymentSource> paymentSources = new ArrayList<PaymentSource>(0);

	public Address() {
	}

	@Create
	public void create(){
		this.addressId = 0;
		this.street1 = "";
		this.city = "";
		this.state = "";
		this.zip = "";
	}
	public Address(Integer addressId, String street1, String city, String state, String zip) {
		this.addressId = addressId;
		this.street1 = street1;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	public Address(Integer addressId, String street1, String street2, String city, String state,
			String zip, List<Order> ordersForBillAddressId,
			List<Customer> customers, List<Order> ordersForShipAddressId,
			List<PaymentSource> paymentSources) {
		this.addressId = addressId;
		this.street1 = street1;
		this.street2 = street2;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.ordersForBillAddressId = ordersForBillAddressId;
		this.customers = customers;
		this.ordersForShipAddressId = ordersForShipAddressId;
		this.paymentSources = paymentSources;
	}

	public Integer getAddressId() {
		return this.addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public String getStreet1() {
		return this.street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return this.street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public List<Order> getOrdersForBillAddressId() {
		return this.ordersForBillAddressId;
	}

	public void setOrdersForBillAddressId(List<Order> ordersForBillAddressId) {
		this.ordersForBillAddressId = ordersForBillAddressId;
	}

	public List<Customer> getCustomers() {
		return this.customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public List<Order> getOrdersForShipAddressId() {
		return this.ordersForShipAddressId;
	}

	public void setOrdersForShipAddressId(List<Order> ordersForShipAddressId) {
		this.ordersForShipAddressId = ordersForShipAddressId;
	}

	public List<PaymentSource> getPaymentSources() {
		return this.paymentSources;
	}

	public void setPaymentSources(List<PaymentSource> paymentSources) {
		this.paymentSources = paymentSources;
	}
	
	public com.kybpm.web.ws.client.CalculateServiceImplServiceStub.Address toCalculateServiceAddress() {
		com.kybpm.web.ws.client.CalculateServiceImplServiceStub.Address wsAddress = 
				new com.kybpm.web.ws.client.CalculateServiceImplServiceStub.Address();
		wsAddress.setAddressId(this.addressId);
		wsAddress.setStreet1(this.street1);
		wsAddress.setStreet2(this.street2);
		wsAddress.setCity(this.city);
		wsAddress.setState(this.state);
		wsAddress.setZip(this.zip);
		return wsAddress;
	}

}
