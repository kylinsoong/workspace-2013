package com.test.ejb.demo.client;

import com.test.demo.client.TestClientBase;
import com.test.ejb.demo.systemprops.SystemPropertiesService;

public class SystemPropertiesServiceClient extends TestClientBase {

	public void test() throws Exception {
		SystemPropertiesService service = (SystemPropertiesService) getContext().lookup("/ejb3/SystemPropertiesService");
//		service.showProps();
		service.showXmlInjectProps();
	}
	
	public static void main(String[] arvs) throws Exception {
		new SystemPropertiesServiceClient().test();
	}

}
