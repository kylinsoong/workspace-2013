package com.redhat.gss;

import org.apache.log4j.Logger;

public class Log4JTest {
	
	private static Logger logger = Logger.getLogger(Log4JTest.class);


	public static void main(String[] args) {
		logger.info("Test");
	}

}
