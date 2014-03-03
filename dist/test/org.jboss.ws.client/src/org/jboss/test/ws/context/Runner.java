package org.jboss.test.ws.context;

public class Runner {

	public static void main(String[] args) throws InterruptedException {
		for(;;){
			ContextTestService service = new ContextTestService();
			ContextTest test = service.getContextTestPort();
			
			System.out.println(test.testGetMessageContext());
			Thread.sleep(1000);
			
//			System.out.println(test.testGetUserPrincipal());
			System.out.println(test.testIsUserInRole("admin"));
			Thread.sleep(1000);
			
			System.out.println(test.testMessageContextProperties());
			Thread.sleep(1000);
		}
		
	}

}
