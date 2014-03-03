package com.kybpm.web.view.bean;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;

import com.kybpm.web.model.Cart;
import com.kybpm.web.model.Customer;
import com.kybpm.web.model.ProductOrder;
import com.kybpm.web.ws.client.CalculateServiceImplServiceStub;
import com.kybpm.web.ws.client.CalculateServiceImplServiceStub.CalculateGrandTotal;
import com.kybpm.web.ws.client.CalculateServiceImplServiceStub.CalculateGrandTotalResponse;

@Name("checkout")
public class CheckoutBean extends BaseBean implements Checkout {

	@Logger
	private Log log;

	@In(create=true) @Out
	private Cart cart;
	
	@In
	private Customer customer;
	
	@In
	FacesMessages facesMessages;

	public String checkout() {
		String _ret = "/OrderProcess.xhtml";
		if (cart.getOrder().getProductOrders().size() < 1) {
			facesMessages.add("Cannot Checkout an Empty Cart!");
			_ret = "/CartView.xhtml";
		} 
		return _ret;
	}
	
	public String calculateTotals(){
		try {
			this.cart.getOrder().setAddressByBillAddressId(this.customer.getAddress());
			this.cart.getOrder().setAddressByShipAddressId(this.customer.getAddress());
			this.cart.getOrder().setCustomer(this.customer);
			this.cart.getOrder().setStatus(com.kybpm.web.model.Order.ORDER_STATUS_NEW);
			
			CalculateServiceImplServiceStub stub = new CalculateServiceImplServiceStub();
			CalculateGrandTotalResponse calculateGrandTotalResponse;
			CalculateGrandTotal calculateGrandTotal = new CalculateGrandTotal();
			com.kybpm.web.ws.client.CalculateServiceImplServiceStub.Order wsOrder = this.cart.getOrder().toCalculateServiceOrder();
			calculateGrandTotal.setArg0(wsOrder);
			calculateGrandTotalResponse = stub.calculateGrandTotal(calculateGrandTotal);
			SalesTotal st = new SalesTotal(calculateGrandTotalResponse.get_return().getProductTotal(), 
					calculateGrandTotalResponse.get_return().getTaxTotal(),
					calculateGrandTotalResponse.get_return().getShippingTotal());
			System.out.println("CheckoutBean:calculateTotals::SalesTotal:::toString()" + st.toString());
			this.cart.setShipping( st.getShippingTotal());
			this.cart.setTax(st.getTaxTotal());
			this.cart.setTotal(st.getProductTotal());
			this.cart.setGrandTotal(st.getGrandTotal());
		}
		catch (Exception e) {
			e.printStackTrace();
			facesMessages.addToControl("errormsg", "An exception occurred to when " +
					"calculating the order totals.");
			return null;
		}
		return "";
	}

	public String removeFromCart() {
		
		int selectedId = this.cart.getSelectedItem();
		
		ProductOrder productToRemove = null;
		for(ProductOrder po : this.cart.getOrder().getProductOrders()) {
			if(po.getProduct().getProductId() == selectedId) {
				if(po.getQuantity() > 1) {
					po.setQuantity(po.getQuantity()-1);
				} else {
					productToRemove = po;
				}
				break;
			}
		}
		
		if(productToRemove != null) {
			this.cart.getOrder().getProductOrders().remove(productToRemove);
		}
		
		calculateTotals();

		return "/CartView.xhtml";		
	}
	
	
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
		
		public String toString() {
			return "SalesTotal:productTotal=" + productTotal + " taxTotal=" + taxTotal + " shippingTotal=" + shippingTotal + " grandTotal=" + grandTotal;
		}
	}
}
