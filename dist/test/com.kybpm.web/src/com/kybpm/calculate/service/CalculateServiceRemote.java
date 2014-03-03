package com.kybpm.calculate.service;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.kybpm.common.domains.calculate.SalesTotal;
import com.kybpm.common.entities.Order;

/**
 * @author Garvin Dean (Amentra, Inc.)
 *
 */
@WebService
@SOAPBinding(style = Style.RPC)
public interface CalculateServiceRemote {
	public SalesTotal calculateGrandTotal(Order cart);
	public Double calculateProductTotal(Order cart);
	public Double calculateSalesTax(Order cart);
	public Double calculateShippingTotal(Order cart);
}
