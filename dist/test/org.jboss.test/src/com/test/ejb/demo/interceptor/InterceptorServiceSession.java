package com.test.ejb.demo.interceptor;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.RemoteBinding;

@Stateless
@Remote(InterceptorService.class)
@Local(InterceptorServiceLocal.class)
@RemoteBinding(jndiBinding = "/ejb3/InterceptorService")

@Interceptors(value={InterceptorMethodBean.class})
public class InterceptorServiceSession implements InterceptorServiceLocal {

	private static Logger logger = Logger.getLogger(InterceptorServiceSession.class);
	
	public String testInterceptor() {
		
		Integer integers[] = new Integer[Integer.MAX_VALUE];
				
		System.out.println(InterceptorServiceSession.class.getName() + ".testInterceptor() invoking");
		
		return "test success";
	}

}
