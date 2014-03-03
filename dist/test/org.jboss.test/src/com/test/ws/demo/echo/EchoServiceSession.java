package com.test.ws.demo.echo;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.jboss.ejb3.annotation.RemoteBinding;

@Stateless
@Remote(EchoService.class)
@Local(EchoServiceLocal.class)
@RemoteBinding(jndiBinding = "/ejb3/EchoService")

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class EchoServiceSession implements EchoServiceLocal {

	@WebMethod
	public String echo(String input) {
		
		return "echo: " + input;
	}

}
