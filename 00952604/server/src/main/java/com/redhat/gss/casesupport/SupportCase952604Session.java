package com.redhat.gss.casesupport;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

@Stateless
@Remote(SupportCase952604Service.class)
@Local(SupportCase952604ServiceLocal.class)
public class SupportCase952604Session implements SupportCase952604ServiceLocal {

	public void printClassLoader() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		System.out.println("Print Class Loader Hierarchy:");
		print(loader);
	}
	
	private void print(ClassLoader loader) {
		println("    " + loader);
		if(loader.getParent() != null) {
			print(loader.getParent());
		}
	}
	
	private void println(Object obj) {
		System.out.println(obj);
	}

	public void printHibernateVersion() {
		System.out.println("Print Hibernate Version String:");
		System.out.println("    " + org.hibernate.Version.getVersionString());
	}
	

   
}
