package com.redhat.gss.servlet;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionPrintListener implements HttpSessionListener{

	public void sessionCreated(HttpSessionEvent e) {
		System.out.println(e.getSession().getId() + " Created");
	}

	public void sessionDestroyed(HttpSessionEvent e) {
		System.out.println(e.getSession().getId() + " Destroyed");
	}

}
