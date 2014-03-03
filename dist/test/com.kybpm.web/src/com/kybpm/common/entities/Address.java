package com.kybpm.common.entities;
// Generated Jan 19, 2009 5:27:42 PM by Hibernate Tools 3.2.2.GA

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * Address generated by hbm2java
 */
@Entity
@Table(name = "address", catalog = "jb336")
public class Address implements java.io.Serializable {

	private Integer addressId;
	private String street1;
	private String street2;
	private String city;
	private String state;
	private String zip;
	//private Set<Order> ordersForBillAddressId = new HashSet<Order>(0);
	//private Set<Customer> customers = new HashSet<Customer>(0);
	//private Set<Order> ordersForShipAddressId = new HashSet<Order>(0);
	//private Set<PaymentSource> paymentSources = new HashSet<PaymentSource>(0);

	public Address() {
	}

	public Address(String street1, String city, String state, String zip) {
		this.street1 = street1;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	public Address(String street1, String street2, String city, String state,
			String zip, Set<Order> ordersForBillAddressId,
			Set<Customer> customers, Set<Order> ordersForShipAddressId,
			Set<PaymentSource> paymentSources) {
		this.street1 = street1;
		this.street2 = street2;
		this.city = city;
		this.state = state;
		this.zip = zip;
		//this.ordersForBillAddressId = ordersForBillAddressId;
		//this.customers = customers;
		//this.ordersForShipAddressId = ordersForShipAddressId;
		//this.paymentSources = paymentSources;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "address_id", unique = true)
	public Integer getAddressId() {
		return this.addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	@Column(name = "street1", nullable = false, length = 45)
	@NotNull
	@Length(max = 45)
	public String getStreet1() {
		return this.street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	@Column(name = "street2", length = 45)
	@Length(max = 45)
	public String getStreet2() {
		return this.street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	@Column(name = "city", nullable = false, length = 45)
	@NotNull
	@Length(max = 45)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "state_code", nullable = false, length = 2)
	@NotNull
	
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "zip", nullable = false, length = 45)
	@NotNull
	@Length(max = 45)
	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	/*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "addressByBillAddressId")
	public Set<Order> getOrdersForBillAddressId() {
		return this.ordersForBillAddressId;
	}

	public void setOrdersForBillAddressId(Set<Order> ordersForBillAddressId) {
		this.ordersForBillAddressId = ordersForBillAddressId;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "address")
	public Set<Customer> getCustomers() {
		return this.customers;
	}

	public void setCustomers(Set<Customer> customers) {
		this.customers = customers;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "addressByShipAddressId")
	public Set<Order> getOrdersForShipAddressId() {
		return this.ordersForShipAddressId;
	}

	public void setOrdersForShipAddressId(Set<Order> ordersForShipAddressId) {
		this.ordersForShipAddressId = ordersForShipAddressId;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "address")
	public Set<PaymentSource> getPaymentSources() {
		return this.paymentSources;
	}

	public void setPaymentSources(Set<PaymentSource> paymentSources) {
		this.paymentSources = paymentSources;
	}
*/
}
