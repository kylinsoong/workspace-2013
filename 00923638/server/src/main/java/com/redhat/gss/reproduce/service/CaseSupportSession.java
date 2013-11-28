package com.redhat.gss.reproduce.service;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.redhat.gss.reproduce.model.GssCase;

@Stateless
@Remote(CaseSupportService.class)
@Local(CaseSupportServiceLocal.class)
public class CaseSupportSession implements CaseSupportServiceLocal {
	
	private static final Logger logger = Logger.getLogger(CaseSupportSession.class);

	@PersistenceContext(unitName="com.redhat.gss.reproduce.model")   
    protected EntityManager em;  
	
//	@TransactionAttribute(TransactionAttributeType.REQUIRED)  
	public void add(GssCase gssCase) {

		logger.info("Test Add GssCase Entity " + gssCase.getName());
		
		em.persist(gssCase);
	}
}
