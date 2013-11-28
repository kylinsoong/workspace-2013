package org.jboss.as.quickstarts.ear.controller;

public class InjectNullException extends RuntimeException {

	private static final long serialVersionUID = 2753985908450534228L;

	public InjectNullException(String message, Throwable cause) {
		super(message, cause);
	}

	public InjectNullException(String message) {
		super(message);
	}

}
