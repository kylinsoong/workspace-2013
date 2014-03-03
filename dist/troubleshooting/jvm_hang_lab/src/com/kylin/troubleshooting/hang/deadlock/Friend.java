package com.kylin.troubleshooting.hang.deadlock;
 
public class Friend {

	private final String name;

	public Friend(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public synchronized void bow(String threadName, Friend bower) {
		System.out.format("[" + threadName + "] " + "%s: %s" + "  has bowed to me!%n", this.name, bower.getName());
		bower.bowBack(threadName, this);
	}

	public synchronized void bowBack(String threadName, Friend bower) {
		System.out.format("[" + threadName + "] " + "%s: %s" + " has bowed back to me!%n", this.name, bower.getName());
	}
}
