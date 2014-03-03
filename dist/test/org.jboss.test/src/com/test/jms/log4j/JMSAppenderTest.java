package com.test.jms.log4j;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;


public class JMSAppenderTest {
	
	public void test() {
		
		String destinationName = "topic/MyAppenderTopic";

		InitialContext ic = null;
		Connection connection = null;
		
		try {
			ic = new InitialContext();
         
			ConnectionFactory cf = (ConnectionFactory) ic.lookup("/ConnectionFactory");
			Topic topic = (Topic) ic.lookup(destinationName);
			log("Topic " + destinationName + " exists");
         
			connection = cf.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer publisher = session.createProducer(topic);
			MessageConsumer subscriber = session.createConsumer(topic);
         
			JMSAppenderListener messageListener = new JMSAppenderListener();
			subscriber.setMessageListener(messageListener);
			connection.start();
         
			TextMessage message = session.createTextMessage("Hello!");
			publisher.send(message);
			log("The message was successfully published on the topic");    
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (ic != null) {
				try {
					ic.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			closeConnection(connection);
		}
	}

	private void closeConnection(Connection con) {
		
		try {
			if (con != null) {
				con.close();
			}
		} catch (JMSException jmse) {
			log("Could not close connection " + con + " exception was " + jmse);
		}
	}

	private void log(String string) {
		System.out.println(string);
	}

	public static void main(String[] args) {
		
		new JMSAppenderTest().test();
	}

}
