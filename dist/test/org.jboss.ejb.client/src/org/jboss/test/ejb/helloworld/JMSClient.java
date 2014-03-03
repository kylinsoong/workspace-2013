package org.jboss.test.ejb.helloworld;

import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.naming.Context;
import javax.naming.InitialContext;

public class JMSClient {

	public static void main(String[] args) throws Exception {

		final Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		env.put(Context.PROVIDER_URL, "remote://localhost:4447");
		env.put(Context.SECURITY_PRINCIPAL, "admin");
		env.put(Context.SECURITY_CREDENTIALS, "redhat");
		Context context = new InitialContext(env);
		ConnectionFactory cf = (ConnectionFactory) context.lookup("jms/RemoteConnectionFactory");
		Destination destination = (Destination) context.lookup("jms/queue/test");
		context.close();
	}

}
