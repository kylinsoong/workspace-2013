package com.kylin.troubleshooting.hang;

import java.io.PrintWriter;

public class TestException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public void printStackTrace(final PrintWriter writer) {
		if (TestClass.TEST_FLF)
			System.out.println("-- TestException printStackTrace");
		super.printStackTrace(writer);
	}
}