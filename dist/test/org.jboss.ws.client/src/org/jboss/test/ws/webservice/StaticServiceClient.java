package org.jboss.test.ws.webservice;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class StaticServiceClient {

	public static void main(String[] args) throws MalformedURLException {

		QName serviceName = new QName("jee6.ws.webservice", "EchoService");
		URL wsdlURL = new URL("http://127.0.0.1:8080/jsr181PojoBean?wsdl");
		Service service = Service.create(wsdlURL, serviceName);
		Echo echo = service.getPort(Echo.class);
		System.out.println(echo.echo("fucker"));
	}

}
