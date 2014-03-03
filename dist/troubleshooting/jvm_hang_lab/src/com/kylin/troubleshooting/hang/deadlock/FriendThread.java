package com.kylin.troubleshooting.hang.deadlock;

public class FriendThread implements Runnable {
	
	private String name;
	
	private Friend kobe, kylin;
	
	private boolean tag;

	public FriendThread(String name, Friend kobe, Friend kylin, boolean tag) {
		this.name = name;
		this.kobe = kobe;
		this.kylin = kylin;
		this.tag = tag;
	}

	public void run() {
		if(tag) {
			kylin.bow(name, kobe);
		} else {
			kobe.bow(name, kylin);
		}
	}

}
