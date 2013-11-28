package com.sample.cusomer;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.SystemEventListenerFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.persistence.jpa.JPAKnowledgeService;
import org.drools.runtime.Environment;
import org.drools.runtime.EnvironmentName;
import org.drools.runtime.StatefulKnowledgeSession;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jbpm.task.TaskService;
import org.jbpm.task.service.UserGroupCallbackManager;

@Name("seamProcessKnowledgeSession")
@Scope(ScopeType.APPLICATION)
public class SeamProcessKnowledgeSession {
	private StatefulKnowledgeSession ksession;
	private static final String JBPM_RESOURCE_PROCESS = "omsProcess.bpmn";
	private static final String JBPM_RESOURCE_TASK = "omsTask.bpmn";
	private TaskService taskService;

	public SeamProcessKnowledgeSession() {
		this.init();
	}
	
	public void init(){
		System.out.println("INIT OMS Process ksession");
		KnowledgeBase kbase = readKnowledgeBase();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.jbpm.persistence.jpa");
		Environment env = KnowledgeBaseFactory.newEnvironment();
		env.set(EnvironmentName.ENTITY_MANAGER_FACTORY, emf);
		ksession = JPAKnowledgeService.newStatefulKnowledgeSession(kbase, null, env);
		this.taskService = this.initTaskService();		
		
		UserGroupCallbackManager manager = UserGroupCallbackManager.getInstance();
		if (!manager.existsCallback()) {
			manager.setCallback(new MonerisLdapUserGroupCallback());
		}
	}

	private KnowledgeBase readKnowledgeBase() {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource(JBPM_RESOURCE_PROCESS), ResourceType.BPMN2);
		kbuilder.add(ResourceFactory.newClassPathResource(JBPM_RESOURCE_TASK), ResourceType.BPMN2);
		return kbuilder.newKnowledgeBase();
	}

	public StatefulKnowledgeSession getKsession() {
		return ksession;
	}

	public void setKsession(StatefulKnowledgeSession ksession) {
		this.ksession = ksession;
	}

	private TaskService initTaskService() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.jbpm.persistence.jpa");
		org.jbpm.task.service.TaskService taskService = new org.jbpm.task.service.TaskService(emf, SystemEventListenerFactory.getSystemEventListener());
		MonerisLocalTaskService localTaskService = new MonerisLocalTaskService(taskService);
		MonerisSyncWSHumanTaskHandler humanTaskHandler = new MonerisSyncWSHumanTaskHandler(localTaskService, this.ksession);
		humanTaskHandler.setLocal(true);
		humanTaskHandler.connect();
		this.ksession.getWorkItemManager().registerWorkItemHandler("Human Task", humanTaskHandler);
		return localTaskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public TaskService getTaskService() {
		return taskService;
	}

}
