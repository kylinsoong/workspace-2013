package com.redhat.gss.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.redhat.gss.model.TestEntity;

@Stateless
@LocalBean
public class TestService {
	
	@PersistenceContext
    private EntityManager em;
	
	public void test(){
		em.persist(new TestEntity("Red Hat GSS CMT Test Demo"));
	}

}
