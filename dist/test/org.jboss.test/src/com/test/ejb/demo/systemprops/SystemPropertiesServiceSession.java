package com.test.ejb.demo.systemprops;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.RemoteBinding;


@Stateless
@Remote(SystemPropertiesService.class)
@Local(SystemPropertiesServiceLocal.class)
@RemoteBinding(jndiBinding = "/ejb3/SystemPropertiesService")
public class SystemPropertiesServiceSession implements SystemPropertiesServiceLocal {

	private static final Logger logger = Logger.getLogger(SystemPropertiesServiceSession.class);
	
	public void showProps() {
		
		String path = System.getProperty("propertyPath");
		
		logger.info("propertyPath: " + path);
		
		if(path != null) {
			Properties prop = new Properties();
			try {
				prop.load(new FileInputStream(new File(path)));
				for(Object obj : prop.keySet()) {
					logger.info(obj + " -> " + prop.get(obj));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void showXmlInjectProps() {
		logger.info("test.queue.jndi -> " + System.clearProperty("test.queue.jndi"));
	}

}
