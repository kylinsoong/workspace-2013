package org.jboss.test.ws.context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.security.Principal;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.namespace.QName;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.MessageContext;

import org.apache.log4j.Logger;
import org.jboss.util.xml.DOMUtils;
import org.jboss.wsf.common.DOMWriter;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

@Stateless
@WebService(name = "ContextTest", targetNamespace = "jee6.ws.context", serviceName = "ContextTestService")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class ContectTestSession implements ContectTestService{
	
	private static Logger logger  = Logger.getLogger(ContectTestSession.class);
	
	@Resource
	WebServiceContext wsCtx;
	
	@WebMethod
	public String testGetMessageContext() {
		
		logger.info("testGetMessageContext() invoked");
		
		MessageContext msgContext = (MessageContext)wsCtx.getMessageContext();
		return msgContext == null ? "fail" : "pass";
	}

	@WebMethod
	public String testGetUserPrincipal() {
		
		logger.info("testGetUserPrincipal() invoked");
		
		Principal principal = wsCtx.getUserPrincipal();
		return principal == null ? "null" : principal.getName();
	}

	@WebMethod
	public boolean testIsUserInRole(String role) {
		
		logger.info("testIsUserInRole() invoked");
		
		return wsCtx.isUserInRole(role);
	}
	
	@WebMethod
	public String testMessageContextProperties() {
		
		logger.info("testMessageContextProperties() invoked");
		
		MessageContext msgContext = (MessageContext)wsCtx.getMessageContext();
		if (msgContext == null){
			return "fail";
		}

		// Check standard jaxws properties
		Object wsdl = msgContext.get(MessageContext.WSDL_DESCRIPTION);
		QName service = (QName) msgContext.get(MessageContext.WSDL_SERVICE);
		QName portType = (QName) msgContext.get(MessageContext.WSDL_INTERFACE);
		QName port = (QName) msgContext.get(MessageContext.WSDL_PORT);
		QName operation = (QName) msgContext.get(MessageContext.WSDL_OPERATION);
	     
		logger.info("wsdl: " + wsdl);
		logger.info("service: " + service);
		logger.info("portType: " + portType);
		logger.info("port: " + port);
		logger.info("operation: " + operation);
	      
		try {
			Element root = null;
			if (wsdl instanceof InputSource) {
	            root = DOMUtils.parse((InputSource)wsdl);
			} else if (wsdl instanceof URI) {
	            root = DOMUtils.parse(((URI)wsdl).toURL().openStream());
	         }
	         ByteArrayOutputStream out = new ByteArrayOutputStream();
	         DOMWriter writer = new DOMWriter(out);
	         writer = writer.setPrettyprint(true);
	         writer.print(root);
	         
	         TransformerFactory transFactory = TransformerFactory.newInstance();
	         Transformer transformer = transFactory.newTransformer();
	         StringWriter buffer = new StringWriter();
	         transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	         transformer.transform(new DOMSource(root), new StreamResult(buffer));
	         String str = buffer.toString();
	         
	         logger.info("Contect: " + str);
	         
	         
	        
		} catch (IOException ex) {
			throw new WebServiceException("Cannot parse MessageContext.WSDL_DESCRIPTION", ex);
		} catch (TransformerException e) {
			e.printStackTrace();
		}

		return "pass";
	}
}
