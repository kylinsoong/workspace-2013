package org.jboss.test.ejb.helloworld;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

@Stateless
@Remote(HelloWorldService.class)
@Local(HelloWorldServiceLocal.class)
public class HelloWorldSession implements HelloWorldServiceLocal {

	public String helloworld() {
		
		System.out.println("Hello, World!");
		
		return "Hello, World!";
	}

}
