package org.jboss.test.ws.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.apache.log4j.Logger;

@WebService(name = "Echo", targetNamespace = "jee6.ws.webservice", serviceName = "EchoService", wsdlLocation = "WEB-INF/wsdl/TestService.wsdl")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class JSR181POJOTestBean {
	
	private static final Logger logger = Logger.getLogger(JSR181POJOTestBean.class);
	
	@WebMethod
	public String echo(String input) {
		
		logger.info("WebMethod echo invoked, with input parameter (" + input + ")");
		
		return "echo: " + input;
	}
}
