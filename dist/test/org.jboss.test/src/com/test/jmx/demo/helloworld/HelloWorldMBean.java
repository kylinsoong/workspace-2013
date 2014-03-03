package com.test.jmx.demo.helloworld;

public interface HelloWorldMBean {

	public void setGreeting( String greeting );  
    public String getGreeting();  
    public void printGreeting();
}
