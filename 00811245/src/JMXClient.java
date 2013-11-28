import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;


public class JMXClient {

	public static void main(String[] args) throws Exception {
		System.out.println("Start");
//		JMXServiceURL address = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + "10.66.192.48" +":"+"8083"+"/jmxrmi");
		JMXServiceURL address = new JMXServiceURL("service:jmx:rmi://10.66.192.48:" + 8083 + "/jndi/rmi://10.66.192.48:" + 8083 + "/jmxrmi");
		JMXConnector connector = JMXConnectorFactory.connect(address,null);
		MBeanServerConnection conn = connector.getMBeanServerConnection();
		System.out.println(conn);
	}
}
