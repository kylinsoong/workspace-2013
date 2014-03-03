package com.kybpm.order.service;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.kybpm.common.entities.Order;
import com.kybpm.common.wrappers.OrderWrapper;

/**
 * @author Amentra Inc.
 *
 */
@WebService
@SOAPBinding(style = Style.RPC)
public interface OrderManagerService {
	public OrderWrapper processOrder(Order order);
}
