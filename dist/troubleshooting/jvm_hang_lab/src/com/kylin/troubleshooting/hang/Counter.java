package com.kylin.troubleshooting.hang;

public class Counter {
  private static int count;

  public static synchronized int getCount(){
	  
    try {
      Thread.sleep(15 * 1000);
    }
    catch (Exception ex) {
    }
    return ++count;
  }
}