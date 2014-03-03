package com.kylin.troubleshooting.oom.gc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GCLogTester {

	public static void main(String[] args) throws InterruptedException {
		new GCLogTester().test(50);
		System.out.println("Complete");
	}

	private void test(int count) throws InterruptedException {
		
		while(count >= 0){
		
			addToMap();
			
			Thread.sleep(100);
			
			System.out.println(count -- );
		}
	}

	Map map = new HashMap();
	
	private void addToMap() {
		for (int i = 0; i < 20; i++) {
			map.put(UUID.randomUUID().toString(), new byte[1024 * 1000]);
		}
	}

}
