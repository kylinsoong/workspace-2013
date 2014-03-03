package com.test.ws.demo.client.echo;

import com.test.demo.client.TestClientBase;
import com.test.ws.demo.echo.EchoService;

public class EchoServiceClient extends TestClientBase {

	public void test() throws Exception {
		
		EchoServiceSessionService service = new EchoServiceSessionService();
		
		EchoServiceSession echo = service.getEchoServiceSessionPort();
		
		System.out.println(echo.echo("Kobe Bryant"));
		
	}
	
	public static void main(String[] args) throws Exception {
		
		new EchoServiceClient().test();
	}

}
