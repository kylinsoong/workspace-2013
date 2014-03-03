package com.test.jms.log4j.mbean;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.net.JMSAppender;
import org.jboss.system.ListenerServiceMBeanSupport;

public class JMSAppenderBean extends ListenerServiceMBeanSupport implements JMSAppenderBeanMBean {

	private static Logger logger = Logger.getLogger(JMSAppenderBean.class);
	
	public void addJmsAppender() {
		
		logger.info("add JMS Appender");
		
		JMSAppender jmsAppender = new JMSAppender();
		
		jmsAppender.setTopicConnectionFactoryBindingName("java:JmsXA");
		jmsAppender.setTopicBindingName("topic/MyErrorsTopic");
		jmsAppender.setName("allEventsJMSAppender");
		jmsAppender.setThreshold(Priority.ERROR);
		jmsAppender.activateOptions();

		Logger root = Logger.getRootLogger();
		root.addAppender(jmsAppender);
	}

	protected void startService() throws Exception {
		addJmsAppender();
	}

}
