package com.kybpm.web.view.bean;

import javax.ejb.Local;

/**
 * Interface that provides a view into the eBikes inventory.
 * 
 * @author Adam Desautels (Amentra Inc.)
 * @version 1.0
 * @since  2/02/09
 */
@Local
public interface Order {
	
	/**
	 * addToCart Action method that creates a sales order and commits items to the order, and finalizes payment
	 * @return
	 */
	public String submitOrder();

}