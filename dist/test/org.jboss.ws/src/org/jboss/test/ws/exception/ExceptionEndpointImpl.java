package org.jboss.test.ws.exception;

import javax.jws.HandlerChain;
import javax.jws.WebService;

@WebService(endpointInterface = "org.jboss.test.ws.exception.ExceptionEndpoint")
@HandlerChain(file = "jaxws-handlers-server.xml")
public class ExceptionEndpointImpl extends EndpointImpl implements ExceptionEndpoint
{
   
}
