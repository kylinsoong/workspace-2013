package com.sample;

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
import org.drools.runtime.process.ProcessInstance;

import org.jbpm.process.workitem.wsht.SyncWSHumanTaskHandler;
import org.jbpm.task.TaskService;
import org.jbpm.task.service.DefaultUserGroupCallbackImpl;
import org.jbpm.task.service.UserGroupCallbackManager;
import org.jbpm.task.service.local.LocalTaskService;
import org.jbpm.test.JBPMHelper;

public class CaseSupport00949043 {


	private static final String JBPM_RESOURCE_PROCESS = "sample.bpmn";
	
	private StatefulKnowledgeSession ksession;
	private TaskService taskService;
	
	public CaseSupport00949043() {
		JBPMHelper.startH2Server();
		JBPMHelper.setupDataSource();
		KnowledgeBase kbase = readKnowledgeBase();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.jbpm.persistence.jpa");
		Environment env = KnowledgeBaseFactory.newEnvironment();
		env.set(EnvironmentName.ENTITY_MANAGER_FACTORY, emf);
		ksession = JPAKnowledgeService.newStatefulKnowledgeSession(kbase, null, env);
		taskService = initTaskService();
		UserGroupCallbackManager.getInstance().setCallback(new DefaultUserGroupCallbackImpl());
	}

	public static void main(String[] args) {
		new CaseSupport00949043().reproduction();
	}

	private void reproduction() {
		
		ProcessInstance pInstance = ksession.startProcess("com.sample.bpmn.hello");
		System.out.println("Start Process...: " + pInstance.getId());
	}

	private TaskService initTaskService() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.jbpm.persistence.jpa");
		org.jbpm.task.service.TaskService taskService = new org.jbpm.task.service.TaskService(emf, SystemEventListenerFactory.getSystemEventListener());
		LocalTaskService localTaskService = new LocalTaskService(taskService);
		SyncWSHumanTaskHandler humanTaskHandler = new SyncWSHumanTaskHandler(localTaskService, ksession);
		humanTaskHandler.setLocal(true);
		humanTaskHandler.connect();
		ksession.getWorkItemManager().registerWorkItemHandler("Human Task", humanTaskHandler);
		return localTaskService;
	}

	private KnowledgeBase readKnowledgeBase() {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource(JBPM_RESOURCE_PROCESS), ResourceType.BPMN2);
		return kbuilder.newKnowledgeBase();
	}

}
