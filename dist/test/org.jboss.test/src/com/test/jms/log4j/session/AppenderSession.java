package com.test.jms.log4j.session;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.RemoteBinding;

@Stateless
@Remote(AppenderService.class)
@RemoteBinding(jndiBinding = "/ejb3/AppenderService")
public class AppenderSession implements AppenderService {
	
	private static Logger logger = Logger.getLogger(AppenderSession.class);

	public void errorEmitting() {
		
		logger.info("emitting a error");
		
		try {
			throw new Exception("Test JMS Appender Exception");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
	}

}
