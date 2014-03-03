package com.test.demo.client;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public abstract class TestClientBase {
	
	public abstract void test() throws Exception;
	
	public Context getContext(){
	
		Properties props = new Properties();  
        props.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");  
        props.setProperty("java.naming.provider.url", "localhost:1099");  
        props.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
	
        Context ctx= null;
        
        try {
			ctx = new InitialContext(props);
		} catch (NamingException e) {
			throw new RuntimeException("Create JNDI Local Context Error");
		}
        
        return ctx;
	}
}
