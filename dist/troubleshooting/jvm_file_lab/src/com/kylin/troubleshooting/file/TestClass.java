package com.kylin.troubleshooting.file;

import java.io.*;
import java.util.*;

public class TestClass {
	private static Vector fileHandles = null;

	public static void openFile(int open) {
		
		if (fileHandles == null){
			fileHandles = new Vector();
		}
		
		int handelSize = fileHandles.size();
		
		if (open >= 0) {
			if (open < handelSize) {
				for (int n = open; n < handelSize; n++) {
					try {
						FileReader handle = (FileReader) fileHandles.get(0);
						handle.close();
						fileHandles.remove(0);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (open > handelSize) {
				try {
					for (int n = handelSize; n <= open; n++) {
						File tmpFile = File.createTempFile("JBossFileHandler",".hnd");
						FileReader handle = new FileReader(tmpFile);
						fileHandles.add((Object) handle);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	}
}
