package com.kylin.ejb.remote.client;

import com.redhat.gss.ejb.TestManagerRemote;



public class TestManagerClient extends ClientBase{

	public void test() throws Exception {
		
		String jndiName = "ejb:/ejb-remote-server/TestManager!com.redhat.gss.ejb.TestManagerRemote" ;
		
		TestManagerRemote testManager = (TestManagerRemote) getContext().lookup(jndiName);
		
		testManager.test();
	}

	public static void main(String[] args) throws Exception {
		new TestManagerClient().test();
	}
	
}
