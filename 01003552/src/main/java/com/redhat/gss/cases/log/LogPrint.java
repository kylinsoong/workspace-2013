package com.redhat.gss.cases.log;

import org.apache.log4j.Logger;

public class LogPrint {

	private final static Logger logger = Logger.getLogger(LogPrint.class); 
	
	public void test(){
		logger.error("Log4j Test ERROR");
		logger.warn("Log4j Test WARN");
		logger.info("Log4j Test INFO");
		logger.debug("Log4j Test DEBUG");
	}
}
