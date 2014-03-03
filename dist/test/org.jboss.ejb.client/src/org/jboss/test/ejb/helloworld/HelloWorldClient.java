package org.jboss.test.ejb.helloworld;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

public class HelloWorldClient {

	public static void main(String[] args) throws Exception {

		Properties jndiProps = new Properties();
		jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		jndiProps.put(Context.PROVIDER_URL,"remote://localhost:4447");
		jndiProps.put("jboss.naming.client.ejb.context", true);
		Context ctx = new InitialContext(jndiProps);
		
		ctx.lookup("java:jboss/exported/helloworld-1.0/HelloWorldSession!org.jboss.test.ejb.helloworld.HelloWorldService");
	}

}
