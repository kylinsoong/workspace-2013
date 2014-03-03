package com.kybpm.common.dao.customer;

import java.util.List;

import javax.ejb.Local;

import com.kybpm.common.entities.Customer;

@Local
public interface CustomerDAO {
	public Customer createCustomer(Customer customer);
	public List<Customer> getCustomerByUserAndPass(Customer customer);
	public List<Customer> getUnApprovedCustomers();
}
