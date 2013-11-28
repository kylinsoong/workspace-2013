package com.redhat.gss;

import org.h2.tools.Server;

public class H2Wrapper {
	
	public static Server startH2Server() {
		try {
			// start h2 in memory database
			Server server = Server.createTcpServer(new String[0]);
	        server.start();
	        return server;
		} catch (Throwable t) {
			throw new RuntimeException("Could not start H2 server", t);
		}
	}
	
	public static void main(String[] args) {
		Server server = startH2Server();
		System.out.println("Start H2... " + server.getStatus());
	}

}
