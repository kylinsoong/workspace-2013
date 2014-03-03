package com.kybpm.web.view.bean;

import javax.faces.event.ActionEvent;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;

import com.kybpm.web.model.Address;
import com.kybpm.web.model.RegistrationCustomer;
import com.kybpm.web.utils.Constants;
import com.kybpm.web.ws.client.CustomerManagerServiceImplServiceStub;
import com.kybpm.web.ws.client.CustomerManagerServiceImplServiceStub.CreateCustomer;
import com.kybpm.web.ws.client.CustomerManagerServiceImplServiceStub.CreateCustomerResponse;

@Name(value="registerBean")
public class RegistrationManagerImpl implements RegistrationManager{

	@Logger
	private Log log;

	@In(create=true) @Out
	private RegistrationCustomer registrationCustomer;
	
	@In
	FacesMessages facesMessages;
	
	@In @Out
	Credentials credentials;
	
    @In	@Out
    Identity identity;	
	
	public void registerCustomer(ActionEvent actionEvent) {
		try {
			CustomerManagerServiceImplServiceStub stub = new CustomerManagerServiceImplServiceStub(Constants.WS_CUSTOMER);
			CreateCustomerResponse customerResponse;
			CreateCustomer createCustomer = new CreateCustomer();
			com.kybpm.web.ws.client.CustomerManagerServiceImplServiceStub.Customer wsCustomer = new com.kybpm.web.ws.client.CustomerManagerServiceImplServiceStub.Customer();
			
			registrationCustomer.setUserId(credentials.getUsername());
			registrationCustomer.setPassword(credentials.getPassword());
			wsCustomer.setFirstName(registrationCustomer.getFirstName());
			wsCustomer.setLastName(registrationCustomer.getLastName());
			wsCustomer.setUserId(registrationCustomer.getUserId());
			wsCustomer.setPassword(registrationCustomer.getPassword());
			Address address = registrationCustomer.getAddress();
			CustomerManagerServiceImplServiceStub.Address stubAddress = new CustomerManagerServiceImplServiceStub.Address();
			stubAddress.setStreet1(address.getStreet1());
			stubAddress.setStreet2(address.getStreet2());
			stubAddress.setCity(address.getCity());
			stubAddress.setState(address.getState());
			stubAddress.setZip(address.getZip());
			wsCustomer.setAddress(stubAddress);	
			createCustomer.setArg0(wsCustomer);
			customerResponse = stub.createCustomer(createCustomer);
			log.info("Test");
			//credentials = new Credentials();
			//credentials.setUsername(customerResponse.get_return().getUserId());
			//credentials.setPassword(customerResponse.get_return().getPassword());
		} 
		catch (Exception e) {
			e.printStackTrace();
			facesMessages.addToControl("errormsg", "An exception occurred to when " +
					"registering the customer");
		}

	}

	public RegistrationCustomer getRegistrationCustomer() {
		return registrationCustomer;
	}

	public void setRegistrationCustomer(RegistrationCustomer registrationCustomer) {
		this.registrationCustomer = registrationCustomer;
	}

}
