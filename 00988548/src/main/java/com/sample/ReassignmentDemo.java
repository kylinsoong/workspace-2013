package com.sample;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.process.workitem.wsht.SyncWSHumanTaskHandler;
import org.jbpm.task.Task;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.DefaultUserGroupCallbackImpl;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.UserGroupCallbackManager;
import org.jbpm.task.service.local.LocalTaskService;
import org.jbpm.test.JBPMHelper;

import bitronix.tm.TransactionManagerServices;
import bitronix.tm.resource.jdbc.PoolingDataSource;

/**
 * This is a sample file to launch a process.
 */
public class ReassignmentDemo {

    private static EntityManagerFactory emf;

    public static final void main(String[] args) throws Exception {
        startUp();
        // load up the knowledge base
        KnowledgeBase kbase = readKnowledgeBase();
        StatefulKnowledgeSession ksession = newStatefulKnowledgeSession(kbase);
        LocalTaskService localTaskService = getTaskServiceAndRegisterHumanTaskHandler(ksession);
        // start a new process instance
        Map<String,Object> params = new HashMap<String, Object>();
        ProcessInstance pi = ksession.startProcess("com.sample.bpmn.hello", params);
        System.out.println("Process started ... : kid = " + ksession.getId() + ", pid = " + pi.getId());
        
        List<TaskSummary> list = localTaskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
        System.out.println("The number of tasks for john = " + list.size());
        list = localTaskService.getTasksAssignedAsPotentialOwner("mary", "en-UK");
        System.out.println("The number of tasks for mary = " + list.size());
        
        ksession.dispose();
        localTaskService.dispose();
        
        //----------------------
        System.out.println("sleeping..."); // Double click 'Task 1' to check 'reassignment' configuration. Reassignment will happen after 5 seconds! 
        Thread.sleep(6000);
        //----------------------
        
        ksession = newStatefulKnowledgeSession(kbase);
        localTaskService = getTaskServiceAndRegisterHumanTaskHandler(ksession);
        
        list = localTaskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
        System.out.println("The number of tasks for john = " + list.size());
        list = localTaskService.getTasksAssignedAsPotentialOwner("mary", "en-UK");
        System.out.println("The number of tasks for mary = " + list.size());
        
        TaskSummary taskSummary = list.get(0);
        Task task = localTaskService.getTask(taskSummary.getId());

        localTaskService.start(task.getId(), "mary");
        
        localTaskService.complete(task.getId(), "mary", null);
        
        ksession.dispose();
        localTaskService.dispose();
        
        System.exit(0);
    }

    private static KnowledgeBase readKnowledgeBase() throws Exception {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("sample.bpmn"), ResourceType.BPMN2);
        return kbuilder.newKnowledgeBase();
    }

    private static void startUp() {

        // for H2
        JBPMHelper.startH2Server();
        JBPMHelper.setupDataSource();
        
        // for your DB. Please change dialect in persistence.xml, too.
//        setupDataSource();
        
        UserGroupCallbackManager.getInstance().setCallback(new DefaultUserGroupCallbackImpl());
        
        Map<String, String> map = new HashMap<String, String>();
        emf = Persistence.createEntityManagerFactory("org.jbpm.persistence.jpa", map);
    }
    
    public static PoolingDataSource setupDataSource() {
        PoolingDataSource pds = new PoolingDataSource();
        pds.setUniqueName("jdbc/jbpm-ds");
        pds.setClassName("bitronix.tm.resource.jdbc.lrc.LrcXADataSource");
        pds.setMaxPoolSize(5);
        pds.setAllowLocalTransactions(true);
        pds.getDriverProperties().put("user", "mysql");
        pds.getDriverProperties().put("password", "mysql");
        pds.getDriverProperties().put("url", "jdbc:mysql://localhost:3306/testbrms531");
        pds.getDriverProperties().put("driverClassName", "com.mysql.jdbc.Driver");
        pds.init();
        return pds;
    }

    public static StatefulKnowledgeSession newStatefulKnowledgeSession(KnowledgeBase kbase) {
        return loadStatefulKnowledgeSession(kbase, -1);
    }

    public static StatefulKnowledgeSession loadStatefulKnowledgeSession(KnowledgeBase kbase, int sessionId) {
        StatefulKnowledgeSession ksession;

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

    private static LocalTaskService getTaskServiceAndRegisterHumanTaskHandler(StatefulKnowledgeSession ksession) {
        TaskService taskeService = new TaskService(emf, SystemEventListenerFactory.getSystemEventListener());
        LocalTaskService localTaskService = new LocalTaskService(taskeService);

        SyncWSHumanTaskHandler humanTaskHandler = new SyncWSHumanTaskHandler(localTaskService, ksession);
        humanTaskHandler.setLocal(true);
        humanTaskHandler.connect();
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task", humanTaskHandler);
        return localTaskService;
    }
}