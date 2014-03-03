package com.kylin.troubleshooting.hang.deadlock;

public class FriendRunner {

	public static void main(String[] args) {
		
		Friend kylin = new Friend("Kylin Soong");
		Friend kobe = new Friend("Kobe Bryant");

		new Thread(new FriendThread("jvm-hang-lab-3-1", kylin, kobe, true )).start();
		
		new Thread(new FriendThread("jvm-hang-lab-3-2", kylin, kobe, false )).start();
	}

}
