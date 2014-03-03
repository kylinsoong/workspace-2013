/**
 * 
 */
package com.kybpm.common.domains.calculate;

/**
 * @author Amentra, Inc.
 *
 */
public class SalesTotal {
	
	private Double productTotal;
	private Double taxTotal;
	private Double shippingTotal;
	private Double grandTotal;
	
	
	
	
	
	/**
	 * Default constructor
	 */
	public SalesTotal() {
	}


	/**
	 * @param productTotal
	 * @param taxTotal
	 * @param shippingTotal
	 */
	public SalesTotal(Double productTotal, Double taxTotal, Double shippingTotal) {
		this.productTotal = productTotal;
		this.taxTotal = taxTotal;
		this.shippingTotal = shippingTotal;
		grandTotal = productTotal + taxTotal + shippingTotal;
	}
	
	
	public Double getProductTotal() {
		return productTotal;
	}
	public void setProductTotal(Double productTotal) {
		this.productTotal = productTotal;
	}
	public Double getTaxTotal() {
		return taxTotal;
	}
	public void setTaxTotal(Double taxTotal) {
		this.taxTotal = taxTotal;
	}
	public Double getShippingTotal() {
		return shippingTotal;
	}
	public void setShippingTotal(Double shippingTotal) {
		this.shippingTotal = shippingTotal;
	}
	
	public Double getGrandTotal(){
		return productTotal + taxTotal + shippingTotal;
	}


	/**
	 * @param grandTotal the grandTotal to set
	 */
	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
	}
	
	

}
