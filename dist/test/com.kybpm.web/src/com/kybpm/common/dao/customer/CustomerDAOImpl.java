package com.kybpm.common.dao.customer;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;

import com.kybpm.common.entities.Customer;

/**
 * @author Brandon Cox (Amentra Inc.)
 *
 */
@Stateless
public class CustomerDAOImpl implements CustomerDAO {

	@PersistenceContext(unitName="kybpm")
	EntityManager em;
	
	/* (non-Javadoc)
	 * @see com.common.dao.customer.CustomerDAO#createCustomer(com.common.entities.Customer)
	 */
	public Customer createCustomer(Customer customer) {
		Query query = em.createQuery("select c FROM com.common.entities.Customer c WHERE c.userId = ?1");
		query.setParameter(1, customer.getUserId());
		List<Customer> customerList = query.getResultList();
		if(customerList.size() == 0){
			Session session = (Session)em.getDelegate();
			session.beginTransaction();
			session.save(customer.getAddress());
			session.saveOrUpdate(customer);
		}
		return customer;
	}

	/* (non-Javadoc)
	 * @see com.common.dao.customer.CustomerDAO#getCustomerByUserAndPass(com.common.entities.Customer)
	 */
	public List<Customer> getCustomerByUserAndPass(Customer customer) {
		List<Customer> customerList = new ArrayList<Customer>();
		try{
			Query q = em.createQuery("select c FROM com.common.entities.Customer c where c.userId = ?1");
			q.setParameter(1, customer.getUserId());
			customerList = q.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return customerList;
	}
	

	public List<Customer> getUnApprovedCustomers() {
		List<Customer> customerList = new ArrayList<Customer>();
		try{
			Query q = em.createQuery("select c FROM com.common.entities.Customer c where c.approved = false");
			customerList = q.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return customerList;
	}

}
