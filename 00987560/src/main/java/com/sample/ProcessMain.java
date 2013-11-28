package com.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.drools.KnowledgeBase;
import org.drools.SystemEventListenerFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.task.Status;
import org.jbpm.task.TaskService;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.AsyncTaskServiceWrapper;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.hornetq.CommandBasedHornetQWSHumanTaskHandler;
import org.jbpm.task.service.hornetq.HornetQTaskClientConnector;
import org.jbpm.task.service.hornetq.HornetQTaskClientHandler;

/**
 * This is a sample file to launch a process.
 */
public class ProcessMain {

	public static final void main(String[] args) throws Exception {

		KnowledgeBase kbase = readKnowledgeBase();
		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		
		TaskClient clientForHumanTaskHandler = new TaskClient(new HornetQTaskClientConnector("HornetQConnector" + UUID.randomUUID(), new HornetQTaskClientHandler(SystemEventListenerFactory.getSystemEventListener())));
        clientForHumanTaskHandler.connect("127.0.0.1", 5153);
        
        CommandBasedHornetQWSHumanTaskHandler handler = new CommandBasedHornetQWSHumanTaskHandler(ksession);
        handler.setClient(clientForHumanTaskHandler);
        handler.connect();
        
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task", handler);
        
        ProcessInstance pi = ksession.startProcess("com.sample.bpmn.hello");
        System.out.println("Process started ... : pid = " + pi.getId());
        
		// Wait to make sure that Task is committed
        Thread.sleep(3000);
        
        TaskClient clientForHumanTaskClient = new TaskClient(new HornetQTaskClientConnector("HornetQConnector" + UUID.randomUUID(), new HornetQTaskClientHandler(SystemEventListenerFactory.getSystemEventListener())));
        clientForHumanTaskClient.connect("127.0.0.1", 5153);
        TaskService taskService = new AsyncTaskServiceWrapper(clientForHumanTaskClient);
        
//        List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
        
        List<Status> status = new ArrayList<Status>();
        status.add(Status.Reserved);
        status.add(Status.InProgress);
        List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwnerByStatus("betty", status, "en-UK");
        System.out.println("task list size: " + list.size());
          
        Thread.sleep(3000);
        
		taskService.disconnect();
        
        clientForHumanTaskHandler.disconnect();
        
        System.out.println("taskService is disconnected");
        
        System.exit(0);
	}

	private static KnowledgeBase readKnowledgeBase() throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("sample.bpmn"), ResourceType.BPMN2);
		return kbuilder.newKnowledgeBase();
	}
	
}