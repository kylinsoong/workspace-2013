package com.kybpm.customer.service;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.kybpm.common.entities.Customer;

/**
 * @author Amentra Inc.
 *
 */
@WebService
@SOAPBinding(style = Style.RPC)
public interface CustomerManagerService {
	public Customer createCustomer(Customer customer);
	public Customer validateCustomer(Customer customer);
}
