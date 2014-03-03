package com.kylin.troubleshooting.hang.deadlock;

public class ResourceRunner {

	public static void main(String[] args) {
		
		if(args.length <= 0) {
			System.out.println("Please Run ResourceRunner with parameter <1>, <2>,<a>");
			System.out.println("\t <1> indicate run Thread 1");
			System.out.println("\t <2> indicate run Thread 2");
			System.out.println("\t <a> indicate run Thread 1");
			System.out.println("Run ResourceRunner Demo: com.kylin.troubleshooting.hang.deadlock.ResourceRunner a");
			Runtime.getRuntime().exit(0);
		}

		Resource a = new Resource("Resource A");
		Resource b = new Resource("Resource B");
		
		Thread t1 = new Thread(new ResourceThread("jvm-hang-lab-3-1", a, b, true ));
		Thread t2 = new Thread(new ResourceThread("jvm-hang-lab-3-2", a, b, false ));
	
		if(args[0].equals("1")){
			t1.start();
		} else if(args[0].equals("2")){
			t2.start();
		} else if(args[0].equals("a")){
			t1.start();
			t2.start();
		}
	}

}
