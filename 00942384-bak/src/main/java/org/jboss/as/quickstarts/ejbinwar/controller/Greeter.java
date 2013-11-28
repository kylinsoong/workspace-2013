/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the 
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.ejbinwar.controller;

import org.jboss.as.quickstarts.ejbinwar.ejb.GreeterEJB;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.io.Serializable;

/**
 * A simple managed bean that is used to invoke the GreeterEJB and store the response. The response is obtained by
 * invoking getMessage().
 *
 * @author paul.robinson@redhat.com, 2011-12-21
 */
@Named("greeter")
@SessionScoped
public class Greeter implements Serializable {


	private static final long serialVersionUID = 5782760103486440828L;

	/**
	 * Injected GreeterEJB client
	 */
	@EJB
	private GreeterEJB greeterEJB;

	/**
	 * Stores the response from the call to greeterEJB.sayHello(...)
	 */
	private String message;

	/**
	 * Invoke greeterEJB.sayHello(...) and store the message
     *
     * @param name The name of the person to be greeted
	 */
	public void setName(String name) {
		testJNDIlookup();
		message = greeterEJB.sayHello(name);
	}

    private void testJNDIlookup() {
    	
    	System.out.println("JNDI lookup test");
    	
    	Context ctx = null;

    	try {
    		ctx = new InitialContext();
		} catch (NamingException e) {
			System.out.println(e.getMessage());
		}
    	
    	try {
    		String jndi = "global/jboss-as-ejb-in-war/GreeterEJB!org.jboss.as.quickstarts.ejbinwar.ejb.GreeterEJB";
    		GreeterEJB  service = (GreeterEJB) ctx.lookup(jndi);
			System.out.println(service.sayHello(jndi));
		} catch (NamingException e) {
			System.out.println(e.getMessage());
		}
    	
    	try {
    		String jndi = "java:global/jboss-as-ejb-in-war/GreeterEJB!org.jboss.as.quickstarts.ejbinwar.ejb.GreeterEJB";
    		GreeterEJB  service = (GreeterEJB) ctx.lookup(jndi);
    		System.out.println(service.sayHello(jndi));
		} catch (NamingException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
     * Get the greeting message, customized with the name of the person to be greeted.
     *
     * @return message. The greeting message.
     */
	public String getMessage() {
		return message;
	}

}
