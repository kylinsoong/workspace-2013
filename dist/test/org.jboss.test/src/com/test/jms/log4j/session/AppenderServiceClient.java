package com.test.jms.log4j.session;

import com.test.demo.client.TestClientBase;

public class AppenderServiceClient extends TestClientBase {

	public void test() throws Exception {

		AppenderService test = (AppenderService) getContext().lookup("ejb3/AppenderService");
		test.errorEmitting();
	}


	public static void main(String[] args) throws Exception {
		new AppenderServiceClient().test();
	}

}
