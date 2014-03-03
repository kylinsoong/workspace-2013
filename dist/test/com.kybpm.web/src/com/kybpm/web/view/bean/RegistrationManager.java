package com.kybpm.web.view.bean;


import javax.ejb.Local;
import javax.faces.event.ActionEvent;

/**
 * Interface that allows a customer to register a new user into
 * the eBikes application.
 * 
 * @author Brandon Cox (Amentra Inc.)
 * @version 1.0
 * @since Jan 20, 2009
 */
@Local
public interface RegistrationManager {
	
	public void registerCustomer(ActionEvent actionEvent);
}
