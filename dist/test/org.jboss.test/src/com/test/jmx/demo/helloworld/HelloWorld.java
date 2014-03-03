package com.test.jmx.demo.helloworld;

public class HelloWorld implements HelloWorldMBean {

	private String greeting = null;  
	  
    public HelloWorld() {  
        this.greeting = "Hello World! I am a Standard MBean";  
    }  
  
    public HelloWorld(String greeting) {  
        this.greeting = greeting;  
    }  
	
	public void setGreeting(String greeting) {
		this.greeting = greeting; 
		System.out.println("set: " + greeting); 
	}

	public String getGreeting() {
		System.out.println("get: " + greeting); 
		return greeting;
	}

	public void printGreeting() {
		System.out.println("print: " + greeting); 
	}

}
