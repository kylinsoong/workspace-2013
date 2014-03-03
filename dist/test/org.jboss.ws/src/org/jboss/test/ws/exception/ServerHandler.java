package org.jboss.test.ws.exception;

import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.jboss.wsf.common.handler.GenericSOAPHandler;

/**
 * A simple server side handler applying uppercase function to the error message strings.
 *
 * @author alessio.soldano@jboss.org
 * @since 12-Feb-2008
 */
public class ServerHandler extends GenericSOAPHandler
{
   @SuppressWarnings("unchecked")
   public boolean handleFault(MessageContext msgContext)
   {
      try
      {
         SOAPMessage soapMessage = ((SOAPMessageContext)msgContext).getMessage();
         SOAPBody soapBody = soapMessage.getSOAPBody();
         SOAPBodyElement soapBodyElement = (SOAPBodyElement)soapBody.getChildElements().next();
         SOAPElement faultStringElement = (SOAPElement)soapBodyElement.getChildElements(new QName("faultstring")).next();
         faultStringElement.setValue(faultStringElement.getValue().toUpperCase());
         Iterator<SOAPElement> itDetail = soapBodyElement.getChildElements(new QName("detail"));
         if (itDetail.hasNext())
         {
            Iterator<SOAPElement> itException = itDetail.next().getChildElements(new QName("http://server.exception.samples.jaxws.ws.test.jboss.org/","UserException"));
            if (itException.hasNext())
            {
               SOAPElement messageElement = (SOAPElement)itException.next().getChildElements(new QName("message")).next();
               messageElement.setValue(messageElement.getValue().toUpperCase());
            }
         }
      }
      catch (Exception e)
      {
         throw  new WebServiceException(e);
      }
      return true;
   }
}
