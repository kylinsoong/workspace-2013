package com.kybpm.web.model;

import static org.jboss.seam.ScopeType.SESSION;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("customer")
@Scope(SESSION)
@AutoCreate
public class Customer implements Serializable {

	private Integer customerId;
	private Address address;
	private String firstName;
	private String lastName;
	private String userId;
	private String password;
	private List <Order> orders;
	private List <PaymentSource> paymentSources;

	public Customer() {
	}
	
	@Create
	public void create(){
		this.customerId = 0;
		this.address = new Address();
		this.address.setAddressId(0);
		this.firstName = "";
		this.lastName = "";
		this.userId = "";
		this.password = "";
		this.orders = new ArrayList<Order>(0);
		this.paymentSources = new ArrayList<PaymentSource>(0);
		this.paymentSources.add(new PaymentSource(0));
	}
	public Customer(Integer customerId, Address address, String firstName, String lastName,
			String userId, String password) {
		this.customerId = customerId;
		this.address = address;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userId = userId;
		this.password = password;
	}
	public Customer(Integer customerId, Address address, String firstName, String lastName,
			String userId, String password, List<Order> orders,
			List<PaymentSource> paymentSources) {
		this.customerId = customerId;
		this.address = address;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userId = userId;
		this.password = password;
		this.orders = orders;
		this.paymentSources = paymentSources;
	}

	public Integer getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<PaymentSource> getPaymentSources() {
		return this.paymentSources;
	}

	public void setPaymentSources(List<PaymentSource> paymentSources) {
		this.paymentSources = paymentSources;
	}
	
	public PaymentSource getPaymentSource() {
		return this.paymentSources.get(0);

	}

	public void setPaymentSource(PaymentSource paymentSource) {
	}
}
