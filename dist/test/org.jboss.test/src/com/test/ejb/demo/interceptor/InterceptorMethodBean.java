package com.test.ejb.demo.interceptor;

import javax.annotation.PostConstruct;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.apache.log4j.Logger;

public class InterceptorMethodBean {
	

	@AroundInvoke
    public Object aroundInvoke(InvocationContext ictx) throws Exception{
        System.out.println("Invoking method: " + ictx.getMethod());
        return ictx.proceed();
    }
	
    @PostConstruct
    public void postConstruct(InvocationContext ictx) throws Exception{
		System.out.println("PostConstruct");
		// ictx.proceed();
    }
}
