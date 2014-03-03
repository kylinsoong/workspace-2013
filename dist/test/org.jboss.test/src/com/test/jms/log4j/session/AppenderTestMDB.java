package com.test.jms.log4j.session;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.jboss.jms.message.ObjectMessageProxy;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "topic/MyErrorsTopic"),
		@ActivationConfigProperty(propertyName = "DLQMaxResent", propertyValue = "10") })
public class AppenderTestMDB implements MessageListener {

	private final Logger logger = Logger.getLogger(AppenderTestMDB.class);
	
	public void onMessage(Message msg) {
		
		try {
			System.out.println("--------------onMessage--------------");
			System.out.println("Message ID: " + msg.getJMSMessageID());
			System.out.println("-------------------------------------");
			System.out.println();
		} catch (JMSException e) {
			logger.error(e);
		}
	}

}
