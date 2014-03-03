package com.kybpm.common.dao.order;

import java.util.List;
import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;

import com.kybpm.common.entities.Order;
import com.kybpm.common.entities.ProductOrder;
import com.kybpm.common.wrappers.OrderWrapper;
import com.kybpm.common.wrappers.ProductOrderWrapper;

/**
 * @author Brandon Cox (Amentra Inc.)
 *
 */
@Stateless
public class OrderDAOImpl implements OrderDAO {

	@PersistenceContext(unitName="kybpm")
	EntityManager em;
	
	/* (non-Javadoc)
	 * @see com.common.dao.customer.OrderDAO#createOrder(com.common.entities.Order)
	 */
	public OrderWrapper createOrder(Order order) {
		Query query = em.createQuery("select o FROM com.common.entities.Order o WHERE o.orderId = ?1");
		query.setParameter(1, order.getOrderId());
		List orderList = query.getResultList();
		if(orderList.size() == 0){
			Session session = (Session)em.getDelegate();
			session.beginTransaction();
			for(ProductOrder productOrder : order.getProductOrders()) {
				if(productOrder.getOrder().getOrderId() == null || productOrder.getOrder().getOrderId() == 0) {
					productOrder.setOrder(order);
				}
			}
			session.save(order);
/*			List<ProductOrder> productOrderList = order.getProductOrders();
			for(Object object : productOrderList) {
				ProductOrder productOrder = (ProductOrder)object;
				productOrder.setOrder(order);
				session.save(productOrder);
			}
/*			session.save(productOrderArray);
/*			for(Iterator<Product> i = order.getProductOrders().iterator(); i.hasNext(); ) {
				Product product = i.next();
				ProductOrder productOrder = new ProductOrder();
				productOrder.setProduct(product);
				productOrder.setOrder(order);
				productOrder.setQuantity(product.getQuantity());
				session.save(productOrder);
			} */
		} 
/*		for(ProductOrder productOrder : order.getProductOrders()) {
			Order dummyOrder = new Order();
			productOrder.setOrder(dummyOrder);
		} */
		OrderWrapper orderWrapper = new OrderWrapper(order.getAddressByBillAddressId(),order.getCustomer(), order.getAddressByShipAddressId(), order.getOrderTimestamp(), order.getStatus());
		orderWrapper.setOrderId(order.getOrderId());
		List<ProductOrderWrapper> productOrderWrapperList = new ArrayList<ProductOrderWrapper>(0);
		for(ProductOrder po : order.getProductOrders()) {
			ProductOrderWrapper productOrderWrapper = new ProductOrderWrapper(po.getOrder(),po.getProduct(),po.getQuantity());
			productOrderWrapper.setProductOrderId(po.getProductOrderId());
			productOrderWrapper.setOrder(null);
			productOrderWrapperList.add(productOrderWrapper);
		}
		orderWrapper.setProductOrders(productOrderWrapperList);
		return orderWrapper;
	}
}
