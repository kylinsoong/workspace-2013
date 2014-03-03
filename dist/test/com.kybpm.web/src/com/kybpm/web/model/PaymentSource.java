package com.kybpm.web.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.io.Serializable;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

public class PaymentSource implements Serializable{

	private Integer paymentSourceId;
	private Customer customer;
	private Address address;
	private String cardType;
	private long cardNumber;
	private Date expirationDate;
	private int cid;

	public PaymentSource() {
	}
	
	public PaymentSource(Integer paymentSourceId) {
		this.paymentSourceId = paymentSourceId;
	}

	public PaymentSource(Integer paymentSourceId, Customer customer, Address address, String cardType,
			long cardNumber, Date expirationDate, int cid) {
		this.paymentSourceId = paymentSourceId;
		this.customer = customer;
		this.address = address;
		this.cardType = cardType;
		this.cardNumber = cardNumber;
		this.expirationDate = expirationDate;
		this.cid = cid;
	}

	public Integer getPaymentSourceId() {
		return this.paymentSourceId;
	}

	public void setPaymentSourceId(Integer paymentSourceId) {
		this.paymentSourceId = paymentSourceId;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getCardType() {
		return this.cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public long getCardNumber() {
		return this.cardNumber;
	}

	public void setCardNumber(long cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Date getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public int getCid() {
		return this.cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

}
