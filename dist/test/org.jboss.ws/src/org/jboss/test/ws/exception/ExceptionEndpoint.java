package org.jboss.test.ws.exception;

import javax.jws.WebService;


@WebService
public interface ExceptionEndpoint
{
   public void throwRuntimeException();

   public void throwSoapFaultException();

   public void throwApplicationException() throws UserException;
}
