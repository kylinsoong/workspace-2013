package com.kybpm.web.view.bean;

import java.awt.event.ActionEvent;

import javax.ejb.Local;

/**
 * Interface that provides a view into the eBikes inventory.
 * 
 * @author Adam Desautels (Amentra Inc.)
 * @version 1.0
 * @since  2/02/09
 */
@Local
public interface Checkout {
	
	/**
	 * removeFromCart Action method that removes selected items from the shopping cart
	 * @return
	 */
	public String removeFromCart();
	/**
	 * checkout Action method that calculates totals for items in the shopping cart
	 * @return
	 */
	public String checkout();
	
	/**
	 * 
	 * @return
	 */
	public String calculateTotals();
}
