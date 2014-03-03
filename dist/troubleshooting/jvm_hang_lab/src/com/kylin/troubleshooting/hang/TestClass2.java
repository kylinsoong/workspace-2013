package com.kylin.troubleshooting.hang;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestClass2 {
	
	public static Log log = LogFactory.getLog("TestClass2");

	public static boolean getFLG() {
		try {
			Thread.sleep(10 * 1000);
			log.info("---- TestClass2 FLG");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}
}
