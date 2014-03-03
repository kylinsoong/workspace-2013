package com.kylin.troubleshooting.hang.deadlock;

public class Util {

	public static void sleep(String threadName, Long milliseconds) {
		
		System.out.println("[" + threadName + "] " + "Sleep " + milliseconds);
		
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
