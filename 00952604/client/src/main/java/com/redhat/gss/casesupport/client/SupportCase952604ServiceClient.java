package com.redhat.gss.casesupport.client;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.redhat.gss.casesupport.SupportCase952604Service;



public class SupportCase952604ServiceClient {
	
	protected Context getContext() throws NamingException {
		Hashtable<String, String> jndiProperties = new Hashtable<String, String>();
		jndiProperties.put(Context.URL_PKG_PREFIXES,"org.jboss.ejb.client.naming");
		Context context = new InitialContext(jndiProperties);
		return context;
	}

	public void test() throws Exception {
		
		String jndiName = "ejb:/ejb-remote-server/SupportCase952604Session!" + SupportCase952604Service.class.getName() ;
		
		SupportCase952604Service service = (SupportCase952604Service) getContext().lookup(jndiName);
		
		service.printClassLoader();
		
		service.printHibernateVersion();
		
	}

	public static void main(String[] args) throws Exception {
		new SupportCase952604ServiceClient().test();
	}
	
}
