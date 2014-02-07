package com.redhat.gss.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class CaseReproduceService {
	
	private String connFactoryName = "ConnectionFactory";
	private String transactedName = "true";
	private String acknowledgeModeName = "AUTO_ACKNOWLEDGE";
	private String queueQueueName = "jbpm.ht.tasksQueue";
	private String responseQueueName = "jbpm.ht.responseQueue";
	
	public void reproduce(){

	}

}
