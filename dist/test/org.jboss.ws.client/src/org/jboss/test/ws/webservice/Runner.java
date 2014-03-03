package org.jboss.test.ws.webservice;

public class Runner {


	public static void main(String[] args) {
		
		EchoService service = new EchoService();
		Echo echo = service.getEchoPort();
		System.out.println(echo.echo("fucker"));
	}

}
