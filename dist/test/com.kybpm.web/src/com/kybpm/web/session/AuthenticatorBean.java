package com.kybpm.web.session;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;

import com.kybpm.web.model.Address;
import com.kybpm.web.model.Customer;
import com.kybpm.web.model.RegistrationCustomer;
import com.kybpm.web.utils.Constants;
import com.kybpm.web.ws.client.CustomerManagerServiceImplServiceStub;
import com.kybpm.web.ws.client.CustomerManagerServiceImplServiceStub.ValidateCustomerResponse;


@Name("authenticator")
public class AuthenticatorBean implements Authenticator {
	@Logger
	private Log log;
	
	@In
	private Identity identity;

	@In
	private Credentials credentials ;

	
	@In(required=false,scope=ScopeType.SESSION)
	private RegistrationCustomer registrationCustomer;
	
	@Out(required=false,scope=ScopeType.SESSION)
	private Customer customer;
	
	public void loadCustomer() {
		
		log.info("authenticating {0}", credentials.getUsername());
		
		boolean authenticated = false;

		if (identity.isLoggedIn()) {
			
			log.info("Identity is logged in");
			
			try {
				CustomerManagerServiceImplServiceStub stub = new CustomerManagerServiceImplServiceStub(Constants.WS_CUSTOMER);
				ValidateCustomerResponse validateResponse;
				com.kybpm.web.ws.client.CustomerManagerServiceImplServiceStub.ValidateCustomer wsValidateCustomer = new com.kybpm.web.ws.client.CustomerManagerServiceImplServiceStub.ValidateCustomer();
				CustomerManagerServiceImplServiceStub.Customer stubCustomer = new CustomerManagerServiceImplServiceStub.Customer();

				log.info("credentials= "+credentials.getUsername());
				stubCustomer.setUserId(credentials.getUsername());
				stubCustomer.setPassword("dummy");
				wsValidateCustomer.setArg0(stubCustomer);				
				validateResponse = stub.validateCustomer(wsValidateCustomer); 
				int x = validateResponse.get_return().getCustomerId();
				if(x <= 0){
					authenticated = false;
				}
				else {
					customer = new Customer();
					this.customer.create();
					log.info("CustomerId= " + validateResponse.get_return().getCustomerId());
					int customerId = validateResponse.get_return().getCustomerId();
					this.customer.setCustomerId(customerId);
					this.customer.setUserId(validateResponse.get_return().getUserId());
					//this.customer.setPassword(validateResponse.get_return().getPassword());
					this.customer.setFirstName(validateResponse.get_return().getFirstName());
					this.customer.setLastName(validateResponse.get_return().getLastName());
					Address address = new Address();
					if (validateResponse.get_return().getAddress()!= null){
						address.setAddressId(validateResponse.get_return().getAddress().getAddressId());
						address.setStreet1(validateResponse.get_return().getAddress().getStreet1());
						address.setStreet2(validateResponse.get_return().getAddress().getStreet2());
						address.setCity(validateResponse.get_return().getAddress().getCity());
						address.setState(validateResponse.get_return().getAddress().getState());
						address.setZip(validateResponse.get_return().getAddress().getZip());
					}
					this.customer.setAddress(address);
					authenticated = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				authenticated = false;
			}

			if(authenticated){
				System.out.println("Authenticated");
			}
			else{
				System.out.println("not authenticated");
				identity.logout();
			}
		}
		else {
			log.info("Identity is NOT logged in");
		}
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}

