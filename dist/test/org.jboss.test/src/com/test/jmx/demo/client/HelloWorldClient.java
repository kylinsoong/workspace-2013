package com.test.jmx.demo.client;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.jboss.security.client.SecurityClient;
import org.jboss.security.client.SecurityClientFactory;

import com.test.demo.client.TestClientBase;

public class HelloWorldClient extends TestClientBase{

	public static void main(String[] args) throws Exception {
		new HelloWorldClient().test();
	}

	public void test() throws Exception {
		
//		SecurityClient client = SecurityClientFactory.getSecurityClient();
//		client.setSimple("admin", "admin");
//		client.login();
//		
//		Object obj = getContext().lookup("jmx/invoker/RMIAdaptor");
//		MBeanServerConnection server = (MBeanServerConnection)obj;
//		System.out.println(server.invoke(new ObjectName("kylindomain:id=HelloWorld"), "getGreeting", null, null));
	
		JMXServiceURL address = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://"+"localhost"+":"+"8083"+"/jmxrmi");
		JMXConnector connector = JMXConnectorFactory.connect(address,null);
		MBeanServerConnection conn = connector.getMBeanServerConnection();
		System.out.println(conn/*.invoke(new ObjectName("kylindomain:id=HelloWorld"), "getGreeting", null, null)*/);
	}

}
