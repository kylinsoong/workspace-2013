package com.kybpm.web.view.bean;



import javax.ejb.Local;
import javax.faces.event.ActionEvent;

/**
 * Interface that provides a view into the eBikes inventory.
 * 
 * @author James Darst (Amentra Inc.)
 * @version 1.0
 * @since  1/29/09
 */
@Local
public interface Inventory {
	
	/**
	 * search Action method that returns a list of products
	 * @return
	 */ 
	public String search();
	
	/**
	 * addToCart Action method that adds the selected product to customers shopping cart
	 * @return
	 */
	public String addToCart();
}
