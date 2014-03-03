package com.kybpm.order.service;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebService;

import com.kybpm.common.dao.order.OrderDAO;
import com.kybpm.common.entities.Order;
import com.kybpm.common.wrappers.OrderWrapper;

@Stateless
@WebService(endpointInterface="com.kybpm.order.service.OrderManagerService")
@Remote(value=OrderManagerService.class)
public class OrderManagerServiceImpl{

	@EJB(mappedName="OrderDAOImpl/local")
	private OrderDAO orderDAO;
	
	/**
	 * @param order
	 * @return
	 */
	public OrderWrapper processOrder(Order order) {
		OrderWrapper orderWrapper = new OrderWrapper();
		try{
			orderWrapper = orderDAO.createOrder(order);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return orderWrapper;
	}
}
