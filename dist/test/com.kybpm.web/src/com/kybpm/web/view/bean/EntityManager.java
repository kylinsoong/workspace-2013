package com.kybpm.web.view.bean;

import static org.jboss.seam.ScopeType.SESSION;

import javax.ejb.Stateful;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;

import com.kybpm.web.model.Customer;


@Name("em")
@Scope(SESSION)
public class EntityManager {
	
	@In @Out
	private Customer customer;
	
	public EntityManager() {
		
	}
}
