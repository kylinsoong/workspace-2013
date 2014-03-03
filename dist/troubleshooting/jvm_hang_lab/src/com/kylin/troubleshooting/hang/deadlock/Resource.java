package com.kylin.troubleshooting.hang.deadlock;

public class Resource {

	private final String name;

	public Resource(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public synchronized void lock(String threadName, Resource resource) {
		
		System.out.println("[" + threadName + "] " + "Lock the " + resource.getName());
		
		Util.sleep(threadName, 1000L);
		
		resource.lockInternal(threadName, this);
	}
	
	public synchronized void lockInternal(String threadName, Resource resource) {
		
		System.out.println("[" + threadName + "] " + "Lock the " + resource.getName());
	}
}
