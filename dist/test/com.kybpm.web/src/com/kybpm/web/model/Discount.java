package com.kybpm.web.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Discount implements Serializable {

	private Integer discountId;
	private Product product;
	private Date startTimestamp;
	private Date endTimestamp;
	private double percentDiscount;
	private double amountOff;

	public Discount() {
	}

	public Discount(Integer discountId, Product product, Date startTimestamp, Date endTimestamp,
			double percentDiscount, double amountOff) {
		this.discountId = discountId;
		this.product = product;
		this.startTimestamp = startTimestamp;
		this.endTimestamp = endTimestamp;
		this.percentDiscount = percentDiscount;
		this.amountOff = amountOff;
	}

	public Integer getDiscountId() {
		return this.discountId;
	}

	public void setDiscountId(Integer discountId) {
		this.discountId = discountId;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Date getStartTimestamp() {
		return this.startTimestamp;
	}

	public void setStartTimestamp(Date startTimestamp) {
		this.startTimestamp = startTimestamp;
	}

	public Date getEndTimestamp() {
		return this.endTimestamp;
	}

	public void setEndTimestamp(Date endTimestamp) {
		this.endTimestamp = endTimestamp;
	}

	public double getPercentDiscount() {
		return this.percentDiscount;
	}

	public void setPercentDiscount(double percentDiscount) {
		this.percentDiscount = percentDiscount;
	}

	public double getAmountOff() {
		return this.amountOff;
	}

	public void setAmountOff(double amountOff) {
		this.amountOff = amountOff;
	}

	public com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.Discount toOrderServiceDiscount() {
		com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.Discount wsDiscount = 
				new com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.Discount();
		wsDiscount.setAmountOff(this.amountOff);
		wsDiscount.setDiscountId(this.discountId);
		Calendar endTime = Calendar.getInstance();
		Calendar startTime = Calendar.getInstance();
		if(this.endTimestamp != null) endTime.setTime(this.endTimestamp);
		if(this.startTimestamp != null) startTime.setTime(this.startTimestamp);
		wsDiscount.setEndTimestamp(endTime);
		wsDiscount.setStartTimestamp(startTime);
		wsDiscount.setPercentDiscount(this.percentDiscount);
		com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.Product wsProduct = new com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.Product();
		wsProduct.setProductId(this.product.getProductId());
		wsDiscount.setProduct(wsProduct);
		return wsDiscount;
	}
	
	public com.kybpm.web.ws.client.CalculateServiceImplServiceStub.Discount toCalculateServiceDiscount() {
		com.kybpm.web.ws.client.CalculateServiceImplServiceStub.Discount wsDiscount = 
				new com.kybpm.web.ws.client.CalculateServiceImplServiceStub.Discount();
		wsDiscount.setAmountOff(this.amountOff);
		wsDiscount.setDiscountId(this.discountId);
		Calendar endTime = Calendar.getInstance();
		Calendar startTime = Calendar.getInstance();
		if(this.endTimestamp != null) endTime.setTime(this.endTimestamp);
		if(this.startTimestamp != null) startTime.setTime(this.startTimestamp);
		wsDiscount.setEndTimestamp(endTime);
		wsDiscount.setStartTimestamp(startTime);
		wsDiscount.setPercentDiscount(this.percentDiscount);
		com.kybpm.web.ws.client.CalculateServiceImplServiceStub.Product wsProduct = new com.kybpm.web.ws.client.CalculateServiceImplServiceStub.Product();
		wsProduct.setProductId(this.product.getProductId());
		wsDiscount.setProduct(wsProduct);
		return wsDiscount;
	}
}
