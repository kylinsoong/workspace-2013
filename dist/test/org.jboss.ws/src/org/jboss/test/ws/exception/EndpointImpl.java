package org.jboss.test.ws.exception;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;
import javax.xml.ws.soap.SOAPFaultException;

public class EndpointImpl
{
   public void throwRuntimeException()
   {
      throw new RuntimeException("oh no, a runtime exception occured.");
   }

   public void throwSoapFaultException()
   {
      // This should be thrown as-is
      try
      {
         SOAPFactory factory = SOAPFactory.newInstance();
         SOAPFault fault = factory.createFault("this is a fault string!", new QName("http://foo", "FooCode"));
         fault.setFaultActor("mr.actor");
         fault.addDetail().addChildElement("test");
         throw new SOAPFaultException(fault);
      }
      catch (SOAPException s)
      {
         throw new RuntimeException(s);
      }
   }

   public void throwApplicationException() throws UserException
   {
      throw new UserException("validation", 123, "Some validation error");
   }
}
