package org.jboss.test.ws.exception;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import junit.framework.Test;

import org.jboss.test.ws.exception.ExceptionEndpoint;
import org.jboss.wsf.test.JBossWSTestSetup;

/**
 * Test JAX-WS exception handling with EJB3 endpoints
 *
 * @author <a href="jason.greene@jboss.com">Jason T. Greene</a>
 * @author alessio.soldano@jboss.com
 */
public class ExceptionEJB3TestCase extends ExceptionTestCase
{
   public static Test suite()
   {
      return new JBossWSTestSetup(ExceptionEJB3TestCase.class, "jaxws-samples-exception.jar");
   }

   @Override
   protected ExceptionEndpoint getProxy() throws Exception
   {
      QName serviceName = new QName(targetNS, "ExceptionEndpointEJB3ImplService");
      URL wsdlURL = new URL("http://" + getServerHost() + ":8080/jaxws-samples-exception/ExceptionEndpointEJB3Impl?wsdl");

      Service service = Service.create(wsdlURL, serviceName);
      return service.getPort(ExceptionEndpoint.class);
   }
}
