package com.test.jms.log4j;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class JMSAppenderListener implements MessageListener {

	public void onMessage(Message msg) {
		
		try {
			System.out.println("--------------onMessage--------------");
			System.out.println("Message ID: " + msg.getJMSMessageID());
			if(msg instanceof TextMessage){
				TextMessage text = (TextMessage) msg;
				System.out.println("Message Content: [" + text.getText() + "]");
			}
			System.out.println("-------------------------------------");
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
