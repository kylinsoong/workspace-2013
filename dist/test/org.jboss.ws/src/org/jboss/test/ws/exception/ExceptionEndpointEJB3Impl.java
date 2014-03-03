package org.jboss.test.ws.exception;

import javax.ejb.Stateless;
import javax.jws.HandlerChain;
import javax.jws.WebService;

@Stateless
@WebService(endpointInterface = "org.jboss.test.ws.exception.ExceptionEndpoint")
@HandlerChain(file = "jaxws-handlers-server.xml")
public class ExceptionEndpointEJB3Impl extends EndpointImpl implements ExceptionEndpoint
{
   
}
