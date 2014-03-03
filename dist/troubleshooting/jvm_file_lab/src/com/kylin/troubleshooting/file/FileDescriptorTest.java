package com.kylin.troubleshooting.file;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class FileDescriptorTest {

	public static void main(String[] args) throws IOException {
		
		Set<File> set = new HashSet<File>();
		
		new File("test").mkdir();
		
		try {
			for(int i = 1 ;; i ++) {
				File file = new File("test/test-" + i);
				file.createNewFile();
				set.add(file);
				System.out.println("Open File " + i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			new File("test").delete();
		}
		
		
	}

}
