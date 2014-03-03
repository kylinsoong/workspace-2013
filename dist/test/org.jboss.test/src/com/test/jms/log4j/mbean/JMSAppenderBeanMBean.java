package com.test.jms.log4j.mbean;

import org.jboss.system.ListenerServiceMBean;

public interface JMSAppenderBeanMBean extends ListenerServiceMBean{

	public void addJmsAppender();
}
