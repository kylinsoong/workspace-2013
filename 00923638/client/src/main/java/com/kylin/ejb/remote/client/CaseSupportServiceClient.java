package com.kylin.ejb.remote.client;

import com.redhat.gss.reproduce.model.GssCase;
import com.redhat.gss.reproduce.service.CaseSupportService;



public class CaseSupportServiceClient extends ClientBase{

	public void test() throws Exception {
		
		String jndiName = "CaseSupportSession/remote-com.redhat.gss.reproduce.service.CaseSupportService" ;
		
		CaseSupportService  service = (CaseSupportService ) getContext().lookup(jndiName);
		
		for(int i = 0 ; i < 10 ; i ++) {
			service.add(new GssCase("00923638"));
		}
		
		System.out.println("Add 10 GssCase Entity");
		
	}

	public static void main(String[] args) throws Exception {
		new CaseSupportServiceClient().test();
	}
	
}
