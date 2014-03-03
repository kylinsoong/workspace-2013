package com.kybpm.common.dao.order;

import java.util.List;

import javax.ejb.Local;

import com.kybpm.common.entities.Order;
import com.kybpm.common.wrappers.OrderWrapper;

@Local
public interface OrderDAO {
	public OrderWrapper createOrder(Order order);
}
