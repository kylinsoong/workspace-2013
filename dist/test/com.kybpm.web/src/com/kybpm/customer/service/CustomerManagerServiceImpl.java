/**
 * 
 */
package com.kybpm.customer.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebService;

import com.kybpm.common.dao.customer.CustomerDAO;
import com.kybpm.common.entities.Customer;

/**
 * @author Amentra Inc.
 *
 */
@Stateless
@WebService(endpointInterface="com.kybpm.customer.service.CustomerManagerService")
@Remote(value=CustomerManagerService.class)
public class CustomerManagerServiceImpl{

	@EJB(mappedName="CustomerDAOImpl/local")
	private CustomerDAO customerDAO;
	
	/**
	 * @param customer
	 * @return
	 */
	public Customer createCustomer(Customer customer) {
		Customer filledOutCustomer = new Customer();
		try{
			filledOutCustomer = customerDAO.createCustomer(customer);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return filledOutCustomer;
	}

	/**
	 * @param customer
	 * @return
	 */
	public Customer validateCustomer(Customer customer) {
		try{
			List<Customer> customerList = customerDAO.getCustomerByUserAndPass(customer);
			if(customerList.size() == 1) {
				return customerList.get(0);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
