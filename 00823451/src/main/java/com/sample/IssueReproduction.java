package com.sample;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.drools.KnowledgeBase;
import org.drools.SystemEventListenerFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.impl.EnvironmentFactory;
import org.drools.io.ResourceFactory;
import org.drools.persistence.jpa.JPAKnowledgeService;
import org.drools.runtime.Environment;
import org.drools.runtime.EnvironmentName;
import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.process.workitem.wsht.SyncWSHumanTaskHandler;
import org.jbpm.task.Content;
import org.jbpm.task.Task;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.local.LocalTaskService;

import bitronix.tm.TransactionManagerServices;

public class IssueReproduction {

	private String name;
	
	private String id;

	public IssueReproduction(String name, String id) {
		super();
		this.name = name;
		this.id = id;
	}
	
	private EntityManagerFactory emf;
	
	private LocalTaskService localTaskService;
	
	public void reproduction() {

		StatefulKnowledgeSession ksession = newStatefulKnowledgeSession(name);
		
		localTaskService = getTaskService(ksession);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "kylin soong");
		params.put("number", "18611907049");
		ksession.startProcess(id, params);
		
		List<TaskSummary> list = localTaskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
        TaskSummary task = list.get(0);
                        
        localTaskService.start(task.getId(), "john");
        localTaskService.complete(task.getId(), "john", null);
        
        System.out.println("Name: " + task.getName());
        System.out.println(task.getActualOwner());
        System.out.println("Comment: " + task.getSubject());
        System.out.println("Priority: " + task.getPriority());
        System.out.println("Skipable: " + task.isSkipable());
        
//        printDocumentContent(task);
        printUserTask(task);
        
        ksession.dispose();
	}


	private  void printDocumentContent(TaskSummary task) {
		
		long id = localTaskService.getTask(task.getId()).getTaskData().getDocumentContentId();
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Content content = em.find(Content.class, id);
			Object obj = new ObjectInputStream(new ByteArrayInputStream(content.getContent())).readObject();
			System.out.println("Extract from DB: " + obj);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			if(null != em){
				em.close();
			}
		}
	}

	private void printUserTask(TaskSummary task) {
		Task taskDef=localTaskService.getTask(task.getId());
		Content content = localTaskService.getContent(taskDef.getTaskData().getDocumentContentId());
		
		ByteArrayInputStream bais = new ByteArrayInputStream(content.getContent());
		ObjectInputStream ois =null;
		HashMap taskinfo = null;
		try {
			ois = new ObjectInputStream(bais);
			Object obj = ois.readObject(); 
			System.out.println("Extract via API: " + obj);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private LocalTaskService getTaskService(StatefulKnowledgeSession ksession) {
		TaskService taskService = new TaskService(emf, SystemEventListenerFactory.getSystemEventListener());
        LocalTaskService localTaskService = new LocalTaskService(taskService);
        
        SyncWSHumanTaskHandler humanTaskHandler = new SyncWSHumanTaskHandler(localTaskService, ksession);
		humanTaskHandler.setLocal(true);
		humanTaskHandler.connect();
		ksession.getWorkItemManager().registerWorkItemHandler("Human Task", humanTaskHandler);
		return localTaskService;
	}

	public StatefulKnowledgeSession newStatefulKnowledgeSession(String name) {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource(name), ResourceType.BPMN2);
        return loadStatefulKnowledgeSession(kbuilder.newKnowledgeBase(), -1);
    }

    public StatefulKnowledgeSession loadStatefulKnowledgeSession(KnowledgeBase kbase, int sessionId) {
        StatefulKnowledgeSession ksession;
        Map<String, String> map = new HashMap<String, String>();
        map.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        emf = Persistence.createEntityManagerFactory("org.jbpm.persistence.jpa", map);
        Environment env = EnvironmentFactory.newEnvironment();
        env.set(EnvironmentName.ENTITY_MANAGER_FACTORY, emf);
        env.set(EnvironmentName.TRANSACTION_MANAGER, TransactionManagerServices.getTransactionManager());

        if (sessionId == -1) {
            ksession = JPAKnowledgeService.newStatefulKnowledgeSession(kbase, null, env);
        } else {
            ksession = JPAKnowledgeService.loadStatefulKnowledgeSession(sessionId, kbase, null, env);
        }

        return ksession;
    }

}
