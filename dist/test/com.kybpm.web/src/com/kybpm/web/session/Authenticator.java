package com.kybpm.web.session;

import javax.ejb.Local;

/**
 * Interface that provides authentication into the eBikes application.
 * 
 * @author Brandon Cox (Amentra Inc.)
 * @version 1.0
 * @since  1/29/09
 */
@Local
public interface Authenticator {
	public void loadCustomer(); 
}
