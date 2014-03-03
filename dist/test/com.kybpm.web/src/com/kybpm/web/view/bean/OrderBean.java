package com.kybpm.web.view.bean;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;

import com.kybpm.web.model.Cart;
import com.kybpm.web.model.ProductOrder;
import com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub;
import com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.ProcessOrder;
import com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.ProcessOrderResponse;

@Name("order")
public class OrderBean extends BaseBean implements Order {

	@Logger
	private Log log;

	@In	@Out
	private Cart cart;
	
	@In
	FacesMessages facesMessages;

	public String submitOrder() {
		
		try {
			
/*			facesMessages.add("shipping = " + this.cart.getShipping() 
					+ "\ntax = " + this.cart.getTax()
					+ "\ntotal = " + this.cart.getTotal()); */
			
			OrderManagerServiceImplServiceStub stub = new OrderManagerServiceImplServiceStub();
			ProcessOrderResponse processOrderResponse;
			ProcessOrder processOrder = new ProcessOrder();
			com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.Order wsOrder = this.cart.getOrder().toOrderServiceOrder();
			processOrder.setArg0(wsOrder);
			processOrderResponse = stub.processOrder(processOrder);
			log.info("Test");
			com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.OrderWrapper result = processOrderResponse.get_return();
			this.cart.getOrder().setOrderId(result.getOrderId());
			for(ProductOrder po : this.cart.getOrder().getProductOrders()) {
				// Set generated order ID on product orders
				po.getOrder().setOrderId(result.getOrderId());
				// Set generated product order IDs on the product orders
				for(int i = 0; i < result.getProductOrders().length; i++) {
					if(result.getProductOrders()[i].getProduct().getProductId() == po.getProduct().getProductId()) {
						po.setProductOrderId(result.getProductOrders()[i].getProductOrderId());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			facesMessages.addToControl("errormsg", "An exception occurred to when " +
					"submitting the order.");
			return "";
		}
		return "/OrderConfirmation.xhtml";
		// TODO Auto-generated method stub
		
		//map and create SalesOrder
		//map and create ShippingAddress
		//map and create Payment
		//map and create ProductOrder for all items in Cart
		
	}
}
