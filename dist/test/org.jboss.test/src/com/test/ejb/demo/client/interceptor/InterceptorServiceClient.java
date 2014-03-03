package com.test.ejb.demo.client.interceptor;

import com.test.demo.client.TestClientBase;
import com.test.ejb.demo.interceptor.InterceptorService;

public class InterceptorServiceClient extends TestClientBase{

	public static void main(String[] args) throws Exception {
		new InterceptorServiceClient().test();
	}

	public void test() throws Exception {
		InterceptorService interceptor = (InterceptorService)getContext().lookup("ejb3/InterceptorService");
		System.out.println(interceptor.testInterceptor());
	}

}
