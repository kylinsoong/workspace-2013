package com.test.ws.demo.endpointJSE;

import java.security.Principal;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;

@WebService
public class EndpointJSEService {
	
	@Resource
	WebServiceContext wsCtx;

	@WebMethod
	public String testGetMessageContext() {
		SOAPMessageContext jaxwsContext = (SOAPMessageContext) wsCtx.getMessageContext();
		return jaxwsContext != null ? "pass" : "fail";
	}
	
	@WebMethod
	public String testGetUserPrincipal() {
		Principal principal = wsCtx.getUserPrincipal();
		return principal.getName();
	}
	
	@WebMethod
	public boolean testIsUserInRole(String role) {
	return wsCtx.isUserInRole(role);
	}

}
