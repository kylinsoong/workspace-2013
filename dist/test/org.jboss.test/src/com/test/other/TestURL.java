package com.test.other;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class TestURL {

	public static void main(String[] args) throws MalformedURLException {
		File file = new File("/home/kylin/work/test/props");
		URL url = file.toURL();
		System.out.println(url);
	}

}
