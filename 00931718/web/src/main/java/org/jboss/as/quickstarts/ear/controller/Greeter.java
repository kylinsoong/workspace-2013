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
package org.jboss.as.quickstarts.ear.controller;

import org.jboss.as.quickstarts.ear.ejb.GreeterEJB;
import org.jboss.as.quickstarts.ear.ejb.GreeterEJB1;
import org.jboss.as.quickstarts.ear.ejb.GreeterEJB2;
import org.jboss.as.quickstarts.ear.ejb.GreeterEJB3;
import org.jboss.as.quickstarts.ear.ejb.GreeterEJB4;
import org.jboss.as.quickstarts.ear.ejb.GreeterEJB5;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import java.io.Serializable;

/**
 * A simple managed bean that is used to invoke the GreeterEJB and store the
 * response. The response is obtained by invoking getMessage().
 * 
 * @author paul.robinson@redhat.com, 2011-12-21
 */
@Named("greeter")
@SessionScoped
public class Greeter implements Serializable {


	private static final long serialVersionUID = -8567835159926229483L;

	@EJB
	transient GreeterEJB greeterEJB;
	
	@EJB
	transient GreeterEJB1 greeterEJB1;
	
	@EJB
	transient GreeterEJB2 greeterEJB2;
	
	@EJB
	transient GreeterEJB3 greeterEJB3;
	
	@EJB
	transient GreeterEJB4 greeterEJB4;
	
	@EJB
	transient GreeterEJB5 greeterEJB5;

	/**
	 * Stores the response from the call to greeterEJB.sayHello(...)
	 */
	private String message;

	/**
	 * Invoke greeterEJB.sayHello(...) and store the message
	 * 
	 * @param name
	 *            The name of the person to be greeted
	 */
	public void setName() {
		
		try {
			injectionNullChecking();
			message = "All @EJB inject successful";
		} catch (InjectNullException e) {
			message = e.getMessage();
		}
		
		
		
//		message = greeterEJB.sayHello(name);
	}

	private void injectionNullChecking() {
		
		System.out.println("Injection null checking");
		
		if (null == greeterEJB)
			throw new InjectNullException("greeterEJB injection failed");
		
		if (null == greeterEJB1)
			throw new InjectNullException("greeterEJB1 injection failed");
		
		if (null == greeterEJB2)
			throw new InjectNullException("greeterEJB2 injection failed");
		
		if (null == greeterEJB3)
			throw new InjectNullException("greeterEJB3 injection failed");
		
		if (null == greeterEJB4)
			throw new InjectNullException("greeterEJB4 injection failed");
		
		if (null == greeterEJB5)
			throw new InjectNullException("greeterEJB5 injection failed");
		
		System.out.println("All EJB inject success");
		
	}

	/**
	 * Get the greeting message, customized with the name of the person to be
	 * greeted.
	 * 
	 * @return message. The greeting message.
	 */
	public String getMessage() {
		return message;
	}

}
