package com.redhat.gss.ejb;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

@Stateless
@Remote(TestManagerRemote.class)
public class TestManager implements TestManagerRemote {

	@EJB
	private TestService testService;
	
	public void test() throws Exception {
		testService.test();
		throw new RuntimeException("mandatory throw exception");
	}

}
