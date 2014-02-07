package com.kylin.ejb.remote.client;

import com.redhat.gss.ejb.CaseReproduceRemote;



public class TestManagerClient extends ClientBase{

	public void test() throws Exception {
		
		String jndiName = "ejb:/ejb-remote-server/TestManager!com.redhat.gss.ejb.TestManagerRemote" ;
		
		CaseReproduceRemote testManager = (CaseReproduceRemote) getContext().lookup(jndiName);
		
		testManager.reproduce();
	}

	public static void main(String[] args) throws Exception {
		new TestManagerClient().test();
	}
	
}
