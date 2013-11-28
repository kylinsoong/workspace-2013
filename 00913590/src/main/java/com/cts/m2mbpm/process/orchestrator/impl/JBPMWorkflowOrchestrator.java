/*
 *--------------------------------------------------------------------------
 *                       Metered and Managed BPM (M2MBPM)
 *--------------------------------------------------------------------------
 *
 *                COPYRIGHT (c) 2012 CTS All Rights Reserved
 *
 *  Entire Contents of this software product Copyright � 2012 Cognizant
 *  Technology Solutions
 *
 *  This module contains confidential and proprietary information of CTS,
 *  and any reproduction, disclosure or use in whole or in part is expressly
 *  forbidden except as may be specifically authorized by prior written
 *  agreement or permission of CTS.
 *
 *--------------------------------------------------------------------------
 *  Program Title : Metered and Managed BPM (M2MBPM)
 *  Description   : This serves as the jBPM based WorkflowService teamplate 
 *  				that must be injected by the Business processes to realize 
 *  				workflow related operations via the framework. This 
 *  				is a template pattern implementation to coordinate the
 *  				flow of execution.
 *
 *  File Name	  : WorkflowServiceImpl.java
 *  Author        : Anindya Saha
 *  Creation Date : Dec 31, 2012
 *--------------------------------------------------------------------------
 */
package com.cts.m2mbpm.process.orchestrator.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.SystemEventListenerFactory;
import org.drools.base.MapGlobalResolver;
import org.drools.compiler.BPMN2ProcessFactory;
import org.drools.compiler.ProcessBuilderFactory;
import org.drools.event.process.ProcessEventListener;
import org.drools.marshalling.impl.ProcessMarshallerFactory;
import org.drools.persistence.jpa.JPAKnowledgeService;
import org.drools.runtime.Environment;
import org.drools.runtime.EnvironmentName;
import org.drools.runtime.KnowledgeSessionConfiguration;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.drools.runtime.process.ProcessRuntimeFactory;
import org.jbpm.bpmn2.BPMN2ProcessProviderImpl;
import org.jbpm.marshalling.impl.ProcessMarshallerFactoryServiceImpl;
import org.jbpm.process.builder.ProcessBuilderFactoryServiceImpl;
import org.jbpm.process.instance.ProcessRuntimeFactoryServiceImpl;
import org.jbpm.process.workitem.wsht.BlockingGetTaskResponseHandler;
import org.jbpm.process.workitem.wsht.WSHumanTaskHandler;
import org.jbpm.task.AccessType;
import org.jbpm.task.Status;
import org.jbpm.task.Task;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.ContentData;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.UserGroupCallbackManager;
import org.jbpm.task.service.hornetq.HornetQTaskClientConnector;
import org.jbpm.task.service.hornetq.HornetQTaskClientHandler;
import org.jbpm.task.service.mina.MinaTaskClientConnector;
import org.jbpm.task.service.mina.MinaTaskClientHandler;
import org.jbpm.task.service.mina.MinaTaskServer;
import org.jbpm.task.service.responsehandlers.BlockingTaskOperationResponseHandler;
import org.jbpm.task.service.responsehandlers.BlockingTaskSummaryResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.SerializationUtils;

import com.cts.m2mbpm.dao.ApplicationDao;
import com.cts.m2mbpm.dao.ProcBizDataMapDao;
import com.cts.m2mbpm.dao.ProcessAuditDao;
import com.cts.m2mbpm.entity.Application;
import com.cts.m2mbpm.entity.ProcBizDataMap;
import com.cts.m2mbpm.entity.ProcessAuditData;
import com.cts.m2mbpm.exception.ProcessException;
import com.cts.m2mbpm.process.entity.BusinessEntity;
import com.cts.m2mbpm.process.entity.ProcessEntity;
import com.cts.m2mbpm.process.event.AbortProcessEventHandler;
import com.cts.m2mbpm.process.event.CreateProcessEventHandler;
import com.cts.m2mbpm.process.event.UpdateProcessEventHandler;
import com.cts.m2mbpm.process.orchestrator.ProcessOrchestrator;
import com.cts.m2mbpm.task.TaskView;
import com.cts.m2mbpm.task.TaskViewAdapter;
import com.cts.m2mbpm.util.PropertyHolder;

public class JBPMWorkflowOrchestrator implements ProcessOrchestrator {

	private static final Logger logger = LoggerFactory.getLogger(JBPMWorkflowOrchestrator.class);

	private static final String userLocale = "en-UK";

	private KnowledgeBase kbase;

	private Environment env;

	private KnowledgeSessionConfiguration config;

	// private TaskClient taskClient;

	private boolean initialized = false;

	private ProcBizDataMapDao procBizDataMapDao;

	private ApplicationDao applicationDao;

	private ProcessAuditDao processAuditDao;

	private ProcessEventListener jbpmProcessEventListener;

	private EntityManagerFactory jbpmEmf;

	private TaskService taskService;

	/**
	 * Load the bpmn file into knowledgebase
	 */
	public synchronized void init() throws Exception {

		if (initialized == false) {

			ProcessBuilderFactory.setProcessBuilderFactoryService(new ProcessBuilderFactoryServiceImpl());
			ProcessMarshallerFactory.setProcessMarshallerFactoryService(new ProcessMarshallerFactoryServiceImpl());
			ProcessRuntimeFactory.setProcessRuntimeFactoryService(new ProcessRuntimeFactoryServiceImpl());
			BPMN2ProcessFactory.setBPMN2ProcessProvider(new BPMN2ProcessProviderImpl());

			env = KnowledgeBaseFactory.newEnvironment();
			env.set(EnvironmentName.ENTITY_MANAGER_FACTORY, jbpmEmf);
			// env.set(EnvironmentName.TRANSACTION_MANAGER, TransactionManagerServices.getTransactionManager());
			env.set(EnvironmentName.GLOBALS, new MapGlobalResolver());

			// try to get the usergroup callback properties
			InputStream usergroupsin = Thread.currentThread().getContextClassLoader().getResourceAsStream("jbpm.usergroup.callback.properties");
			if (usergroupsin != null) {
				Reader reader = new InputStreamReader(usergroupsin);
				Properties callbackproperties = new Properties();
				try {
					callbackproperties.load(reader);
					UserGroupCallbackManager.getInstance().setCallbackFromProperties(callbackproperties);
					System.out.println("Task service registered usergroup callback ...");
				} catch (Exception e) {

					logger.error("Task service unable to register usergroup callback ..." + e.getMessage());
				}
			}

			Properties properties = new Properties();
			properties.put("drools.processInstanceManagerFactory", "org.jbpm.persistence.processinstance.JPAProcessInstanceManagerFactory");
			properties.put("drools.processSignalManagerFactory", "org.jbpm.persistence.processinstance.JPASignalManagerFactory");
			config = KnowledgeBaseFactory.newKnowledgeSessionConfiguration(properties);

			try {
				if (logger.isInfoEnabled()) {
					logger.info("Starting Mina task server.................");
				}

				/*
				 * taskClient = new TaskClient(new MinaTaskClientConnector( "WSHumanTask", new MinaTaskClientHandler( SystemEventListenerFactory
				 * .getSystemEventListener())));
				 * 
				 * boolean isClientConnected = taskClient.connect(PropertyHolder .getInstance().get("mina.task.server.ip"), Integer
				 * .parseInt(PropertyHolder.getInstance().get( "mina.task.server.port")));
				 * 
				 * if (!isClientConnected) {
				 * 
				 * logger.info( "-----------------------------------------------------------------------------" ); System.out .println(
				 * "======================================================================" );
				 */
				MinaTaskServer minaServer = new MinaTaskServer(taskService, Integer.parseInt(PropertyHolder.getInstance().get("mina.task.server.port")));
				Thread thread = new Thread(minaServer);
				thread.start();

				/*
				 * HornetQTaskServer hornetQServer = new HornetQTaskServer(taskService,
				 * Integer.parseInt(PropertyHolder.getInstance().get("hornetq.task.server.port"))); Thread thread = new Thread(hornetQServer);
				 * thread.start();
				 */

			} catch (Exception ex) {
				logger.error("Problem in init() of JBPMWorkflowOrchestrator...", ex.fillInStackTrace());
				System.out.println("Problem in init() of JBPMWorkflowOrchestrator...");
			}

			initialized = true;
		}
	}

	/**
	 * Create the knowledge session that uses JPA to persists runtime state
	 */
	public synchronized StatefulKnowledgeSession createSession() throws Exception {
		System.out.println("************ Creating Knowledge Session *************");

		init();

		if (env.get(EnvironmentName.CMD_SCOPED_ENTITY_MANAGER) != null) {
			((EntityManager) env.get(EnvironmentName.CMD_SCOPED_ENTITY_MANAGER)).clear();
		}
		if (env.get(EnvironmentName.APP_SCOPED_ENTITY_MANAGER) != null) {
			((EntityManager) env.get(EnvironmentName.APP_SCOPED_ENTITY_MANAGER)).clear();
		}

		StatefulKnowledgeSession ksession = JPAKnowledgeService.newStatefulKnowledgeSession(kbase, config, env);

		System.out.println("************ Created Knowledge Session, Session id = " + ksession.getId() + " ************");

		registerHandlers(ksession);

		registerEventListeners(ksession);

		return ksession;
	}

	public synchronized StatefulKnowledgeSession loadSession(int id) throws Exception {
		System.out.println("************ Loading Knowledge Session " + id + " *************");

		init();

		if (env.get(EnvironmentName.CMD_SCOPED_ENTITY_MANAGER) != null) {
			((EntityManager) env.get(EnvironmentName.CMD_SCOPED_ENTITY_MANAGER)).clear();
		}
		if (env.get(EnvironmentName.APP_SCOPED_ENTITY_MANAGER) != null) {
			((EntityManager) env.get(EnvironmentName.APP_SCOPED_ENTITY_MANAGER)).clear();
		}

		StatefulKnowledgeSession ksession = JPAKnowledgeService.loadStatefulKnowledgeSession(id, kbase, config, env);

		System.out.println("************ Loaded Knowledge Session, Session id = " + ksession.getId() + " ************");

		registerHandlers(ksession);

		registerEventListeners(ksession);

		return ksession;
	}

	private void registerHandlers(StatefulKnowledgeSession ksession) {
		registerHumanTaskHandlers(ksession);
		// registerEmailHandler(ksession);
	}

	private void registerHumanTaskHandlers(StatefulKnowledgeSession ksession) {
		try {

			TaskClient taskClient = this.connectTaskServer();
			WSHumanTaskHandler humanTaskHandler = new WSHumanTaskHandler(ksession);
			humanTaskHandler.setClient(taskClient);
			humanTaskHandler.connect();

			ksession.getWorkItemManager().registerWorkItemHandler("Human Task", humanTaskHandler);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * Connect to the task server
	 */
	private TaskClient connectTaskServer() {

		TaskClient taskClient = null;
		
		taskClient = new TaskClient(new MinaTaskClientConnector("MinaTaskHandler" + UUID.randomUUID(), new MinaTaskClientHandler(
				SystemEventListenerFactory.getSystemEventListener())));
		taskClient.connect(PropertyHolder.getInstance().get("mina.task.server.ip"),
				Integer.parseInt(PropertyHolder.getInstance().get("mina.task.server.port")));

		/*taskClient = new TaskClient(new HornetQTaskClientConnector("HornetQHandler" + UUID.randomUUID(), new HornetQTaskClientHandler(
				SystemEventListenerFactory.getSystemEventListener())));

		taskClient.connect(PropertyHolder.getInstance().get("hornetq.task.server.ip"),
				Integer.parseInt(PropertyHolder.getInstance().get("hornetq.task.server.port")));*/

		return taskClient;

	}

	private void registerEventListeners(StatefulKnowledgeSession ksession) {
		ksession.addEventListener(jbpmProcessEventListener);
	}

	/**
	 * 
	 * @param processEntity
	 * @param handler
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public ProcessEntity createProcess(ProcessEntity processEntity, CreateProcessEventHandler handler) throws ProcessException {
		if (handler != null)
			handler.preCreateProcess(processEntity);

		this.createProcess(processEntity);

		if (handler != null)
			handler.postCreateProcess(processEntity);

		return processEntity;
	}

	/**
	 * 
	 * @param processEntity
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public final ProcessEntity createProcess(ProcessEntity processEntity) throws ProcessException {

		this.createjBPMProcess(processEntity);

		this.associateProcessWithBusinessData(processEntity);

		return processEntity;
	}

	/**
	 * 
	 * @param processEntity
	 */
	private void createjBPMProcess(ProcessEntity processEntity) throws ProcessException {

		String processName = processEntity.getProcessName();
		Map<String, Object> params = processEntity.getProcessParamMap();

		try {
			StatefulKnowledgeSession ksession = this.createSession();
			ProcessInstance p = ksession.startProcess(processName, params);
			ksession.fireAllRules();

			if (logger.isInfoEnabled()) {
				logger.info("process created successfully...");
			}

			processEntity.setProcessInstanceId(p.getId());
		} catch (Exception ex) {
			logger.error("Error: " + ex.getMessage());
			throw new ProcessException(ex.getMessage().toString());
		}
	}

	/**
	 * 
	 * @param processEntity
	 */
	private void associateProcessWithBusinessData(ProcessEntity processEntity) throws ProcessException {

		if (logger.isInfoEnabled()) {
			logger.info("Associate business data with process data...");
		}
		Map<String, BusinessEntity> businessDataMap = processEntity.getBusinessDataMap();
		// TODO: Implement persistence in batch here
		for (String key : businessDataMap.keySet()) {
			ProcBizDataMap data = new ProcBizDataMap();

			data.setProcessInstanceId(processEntity.getProcessInstanceId());
			data.setProcessName(processEntity.getProcessName());
			data.setBusinessKey(key);
			data.setBusinessdata(SerializationUtils.serialize(businessDataMap.get(key)));

			procBizDataMapDao.createBusinessProcMap(data);
		}

	}

	/**
	 * Abort the process instance sent along in the process entity
	 * 
	 * @param processEntity
	 * @param handler
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void abortProcess(ProcessEntity processEntity, AbortProcessEventHandler handler) {
		if (handler != null)
			handler.preAbortProcess(processEntity);

		this.abortProcess(processEntity);

		if (handler != null)
			handler.postAbortProcess(processEntity);
	}

	/**
	 * Abort the process instance sent along in the process entity
	 * 
	 * @param processEntity
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public final void abortProcess(ProcessEntity processEntity) {

		this.abortJBPMProcess(processEntity);
	}

	/**
	 * Abort the process instance sent along in the process entity
	 * 
	 * @param processEntity
	 */
	private final void abortJBPMProcess(ProcessEntity processEntity) {

		TaskClient taskClient = null;
		try {
			taskClient = this.connectTaskServer();
			ProcessAuditData auditData = new ProcessAuditData();
			auditData.setInstanceId(processEntity.getProcessInstanceId());

			auditData = processAuditDao.getProcessAuditData(auditData);

			StatefulKnowledgeSession ksession = this.loadSession(auditData.getSessionId());
			ksession.abortProcessInstance(processEntity.getProcessInstanceId());

			// Ideally ksession.abortProcessInstance() should have exited all
			// active tasks, the next block is backup
			List<Status> statuses = new ArrayList<Status>();
			statuses.add(Status.InProgress);
			statuses.add(Status.Reserved);
			statuses.add(Status.Ready);
			statuses.add(Status.Created);
			statuses.add(Status.Suspended);

			BlockingTaskSummaryResponseHandler taskSummaryHandler = new BlockingTaskSummaryResponseHandler();
			// taskClient.connect();
			taskClient.getTasksByStatusByProcessId(processEntity.getProcessInstanceId(), statuses, userLocale, taskSummaryHandler);

			List<TaskSummary> activeTasks = taskSummaryHandler.getResults();

			for (TaskSummary task : activeTasks) {
				BlockingTaskOperationResponseHandler responseHandler = new BlockingTaskOperationResponseHandler();
				taskClient.exit(task.getId(), "Administrator", responseHandler);
			}

			if (logger.isInfoEnabled()) {
				logger.info("Process aborted successfully...");
			}
		} catch (Exception ex) {
			logger.error("Error: " + ex.getMessage());
			throw new ProcessException(ex.getMessage().toString());
		} finally {
			try {
				if (taskClient != null) {
					taskClient.disconnect();
				}
			} catch (Exception ex) {
				logger.error("While closing the Task client got an Error");
				throw new ProcessException(ex);
			}
		}
	}

	/**
	 * @param ksession Register Email handler with Knowledge Session
	 */
	/*
	 private void registerEmailHandler(StatefulKnowledgeSession ksession) {
	 
	 try { EmailWorkItemHandler emailWorkItemHandler = new EmailWorkItemHandler(); emailWorkItemHandler.setConnection(PropertyHolder.
	 getInstance().get("mail.server.host"), PropertyHolder.getInstance().get("mail.server.port"), PropertyHolder.getInstance().get("mail.userName"),
	 PropertyHolder .getInstance().get("mail.password"));
	 
	 emailWorkItemHandler.getConnection().setStartTls(true); ksession.getWorkItemManager().registerWorkItemHandler("Email", emailWorkItemHandler); }
	 catch (Exception ex) { ex.printStackTrace(); }
	  
	 }
	 */

	/**
	 * Get all the process details
	 */
	@Transactional(readOnly = true)
	public final void retrieveProcess(ProcessEntity processEntity) {

		this.populateProcessParamMap(processEntity);

		this.populateBusinessData(processEntity);
	}

	/**
	 * Get the process parameter map associated with the Process
	 * 
	 * @param processEntity
	 */
	private void populateProcessParamMap(ProcessEntity processEntity) {
		// TODO Implement
	}

	/**
	 * Get the business data associated with the Process
	 * 
	 * @param processEntity
	 */
	private void populateBusinessData(ProcessEntity processEntity) {

		List<ProcBizDataMap> procBizData = procBizDataMapDao.getBusinessData(processEntity.getProcessInstanceId());

		try {
			for (ProcBizDataMap dataMap : procBizData) {

				processEntity.addBusinessData(dataMap.getBusinessKey(), (BusinessEntity) SerializationUtils.deserialize(dataMap.getBusinessdata()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update the process details
	 * 
	 * @param processEntity
	 * @param handler
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateProcess(ProcessEntity processEntity, UpdateProcessEventHandler handler) {
		if (handler != null)
			handler.preUpdateProcess(processEntity);

		this.updateProcess(processEntity);

		if (handler != null)
			handler.postUpdateProcess(processEntity);
	}

	/**
	 * Update the process details
	 * 
	 * @param processEntity
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public final void updateProcess(ProcessEntity processEntity) {

		this.updateAssociationData(processEntity);
	}

	/**
	 * Method to update the association table
	 * 
	 * @param processData
	 */
	private void updateAssociationData(ProcessEntity processData) {

		List<ProcBizDataMap> businessData = new ArrayList<ProcBizDataMap>();

		for (String key : processData.getBusinessDataMap().keySet()) {
			ProcBizDataMap data = new ProcBizDataMap();

			data.setProcessInstanceId(processData.getProcessInstanceId());
			data.setBusinessKey(key);
			data.setBusinessdata(SerializationUtils.serialize(processData.getBusinessDataMap().get(key)));

			businessData.add(data);

		}
		procBizDataMapDao.updateBusinessProcMap(processData.getProcessInstanceId(), businessData);
	}

	/**
	 * Get all the tasks assigned to a user
	 */
	public List<TaskView> getAssignedTasks(String userId) throws ProcessException {
		
		List<Status> status = new ArrayList<Status>();
		status.add(Status.Ready);
		status.add(Status.Reserved);
		status.add(Status.InProgress);

		TaskClient taskClient = null;

		try {

			taskClient = this.connectTaskServer();
			BlockingTaskSummaryResponseHandler taskSummaryHandler = new BlockingTaskSummaryResponseHandler();
			taskClient.getTasksAssignedAsPotentialOwnerByStatus(userId, status, userLocale, taskSummaryHandler);

			List<TaskSummary> taskSummaries = taskSummaryHandler.getResults();

			return TaskViewAdapter.adaptToTaskView(taskSummaries);
		} catch (Exception e) {
			logger.error("Error in getting the task list for user:" + userId);
			throw new ProcessException(e);

		} finally {
			try {
				if (taskClient != null) {
					taskClient.disconnect();
				}
			} catch (Exception ex) {
				logger.error("Error occured while closing the Task client");
				throw new ProcessException(ex);
			}
		}

	}

	public List<TaskView> getTaskByProcessInstanceId(long processInstanceId) throws ProcessException {

		List<Status> status = new ArrayList<Status>();
		status.add(Status.Ready);
		status.add(Status.Reserved);
		status.add(Status.InProgress);

		BlockingTaskSummaryResponseHandler responseHandler = new BlockingTaskSummaryResponseHandler();
		TaskClient taskClient = null;

		try {
			taskClient = connectTaskServer();
			taskClient.getTasksByStatusByProcessId(processInstanceId, status, userLocale, responseHandler);
			List<TaskSummary> taskSummaries = responseHandler.getResults();

			return TaskViewAdapter.adaptToTaskView(taskSummaries);

		} catch (Exception ex) {
			logger.error("Error in fetching tasks for processinstanceid: " + processInstanceId);
			throw new ProcessException(ex);

		} finally {
			try {
				if (taskClient != null) {
					taskClient.disconnect();
				}
			} catch (Exception ex) {
				logger.error("Error occured while closing the Task client");
				throw new ProcessException(ex);
			}
		}

	}

	public List<TaskView> getGroupTasks(String userId, List<String> groupIds) {
		
		List<Status> status = new ArrayList<Status>();
		status.add(Status.Ready);

		
		// taskClient.connect();
		TaskClient taskClient = null;
				
		try {

			taskClient = this.connectTaskServer();
			
			BlockingTaskSummaryResponseHandler taskSummaryHandler = new BlockingTaskSummaryResponseHandler();
			taskClient.getTasksAssignedAsPotentialOwnerByStatusByGroup(userId, groupIds, status, userLocale, taskSummaryHandler);
	
			List<TaskSummary> taskSummaries = taskSummaryHandler.getResults();
	
			return TaskViewAdapter.adaptToTaskView(taskSummaries);
		} catch (Exception ex) {
			logger.error("Error in fetching group tasks for user: " + userId);
			throw new ProcessException(ex);

		} finally {
			try {
				if (taskClient != null) {
					taskClient.disconnect();
				}
			} catch (Exception ex) {
				logger.error("Error occured while closing the Task client");
				throw new ProcessException(ex);
			}
		}
	}

	/**
	 * Start task
	 */
	public void startTask(long taskId, String userId) {

		TaskClient taskClient = null;
		try {
			BlockingTaskOperationResponseHandler taskOperationHandler = new BlockingTaskOperationResponseHandler();

			taskClient = this.connectTaskServer();
			taskClient.start(taskId, userId, taskOperationHandler);
		} catch (Exception ex) {
			logger.error("Error while starting the task: " + taskId);
			throw new ProcessException(ex);
		} finally {
			try {
				if (taskClient != null) {
					taskClient.disconnect();
				}
			} catch (Exception ex) {
				logger.error("Error occured while closing the Task client");
				throw new ProcessException(ex);
			}
		}
	}

	/**
	 * Claim a group task
	 */
	public void claimTask(long taskId, String userId, List<String> groupIds) {
		TaskClient taskClient = null;
		try {
			BlockingTaskOperationResponseHandler taskOperationHandler = new BlockingTaskOperationResponseHandler();

			taskClient = this.connectTaskServer();
			taskClient.claim(taskId, userId, groupIds, taskOperationHandler);
		} catch (Exception ex) {
			logger.error("Error while claiming the task: " + taskId);
			throw new ProcessException(ex);
		} finally {
			try {
				if (taskClient != null) {
					taskClient.disconnect();
				}
			} catch (Exception ex) {
				logger.error("Error occured while closing the Task client");
				throw new ProcessException(ex);
			}
		}
	}

	/**
	 * Complete a task with a taskid and data for a user
	 */
	public void completeTask(long taskId, String userId) {

		this.completeTask(taskId, null, userId);
	}

	public void completeTask(long taskId, Map<String, Object> data, String userId) {

		TaskClient taskClient = null;
		
		try {
			taskClient = this.connectTaskServer();
			BlockingGetTaskResponseHandler responseHandler = new BlockingGetTaskResponseHandler();
			// taskClient.connect();
			taskClient.getTask(taskId, responseHandler);

			Task task = responseHandler.getTask();

			if (task.getTaskData().getStatus() != Status.InProgress) {
				BlockingTaskOperationResponseHandler taskOperationHandler = new BlockingTaskOperationResponseHandler();
				taskClient.start(taskId, userId, taskOperationHandler);
			}

			// this.completeProgressTask(taskId, data, userId);
			BlockingTaskOperationResponseHandler taskOperationHandler = new BlockingTaskOperationResponseHandler();
			ContentData cData = null;
			if (data != null) {
				try {
					cData = new ContentData();
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					ObjectOutputStream out = new ObjectOutputStream(bos);
					out.writeObject(data);
					out.close();
					cData.setContent(bos.toByteArray());
					cData.setAccessType(AccessType.Inline);
				} catch (IOException ex) {
					throw new RuntimeException();
				}
			}
			// taskClient = connectTaskServer();
			taskClient.complete(taskId, userId, cData, taskOperationHandler);

			// BlockingGetTaskResponseHandler responseHandler = new BlockingGetTaskResponseHandler();
			// taskClient.getTask(taskId, responseHandler);

			// task = responseHandler.getTask();

			StatefulKnowledgeSession ksession = this.loadSession(task.getTaskData().getProcessSessionId());
			ksession.getWorkItemManager().completeWorkItem(task.getTaskData().getWorkItemId(), data);
			ksession.fireAllRules();

			if (logger.isInfoEnabled()) {
				logger.info("Task " + taskId + " completed successfully...");
			}

		} catch (Exception ex) {
			logger.error("Error occured while completing the task " + taskId);
			throw new ProcessException(ex);
		} finally {
			try {
				if (taskClient != null) {
					taskClient.disconnect();
				}
			} catch (Exception ex) {
				logger.error("While closing the Task client got an Error");
				throw new ProcessException(ex);
			}
		}
	}

	/**
	 * This is similar to 'completeTask' method, but to complete a task that is in 'Progress' state. In this case client start method is not called.
	 */
	/*
	 * public void completeProgressTask(long taskId, Map<String, Object> data, String userId) {
	 * 
	 * TaskClient taskClient = null; Task task = null; try { BlockingTaskOperationResponseHandler taskOperationHandler = new
	 * BlockingTaskOperationResponseHandler(); ContentData cData = null; if (data != null) { try { cData = new ContentData(); ByteArrayOutputStream
	 * bos = new ByteArrayOutputStream(); ObjectOutputStream out = new ObjectOutputStream(bos); out.writeObject(data); out.close();
	 * cData.setContent(bos.toByteArray()); cData.setAccessType(AccessType.Inline); } catch (IOException ex) { throw new RuntimeException(); } }
	 * taskClient = connectTaskServer(); taskClient.complete(taskId, userId, cData, taskOperationHandler);
	 * 
	 * BlockingGetTaskResponseHandler responseHandler = new BlockingGetTaskResponseHandler(); taskClient.getTask(taskId, responseHandler);
	 * 
	 * task = responseHandler.getTask(); } catch (Exception ex) { logger.error("Error occured while completing the task:" + taskId); throw new
	 * ProcessException(ex); } finally { try { if (taskClient != null) { taskClient.disconnect(); } } catch (Exception ex) {
	 * logger.error("While closing the Task client got an Error"); throw new ProcessException(ex); } }
	 * 
	 * try { StatefulKnowledgeSession ksession = this.loadSession(task .getTaskData().getProcessSessionId());
	 * ksession.getWorkItemManager().completeWorkItem( task.getTaskData().getWorkItemId(), data); ksession.fireAllRules();
	 * 
	 * if (logger.isInfoEnabled()) { logger.info("Task completed successfully..."); }
	 * 
	 * } catch (Exception ex) { logger.error("Error: " + ex.getMessage()); throw new ProcessException(ex.getMessage().toString()); } }
	 */

	/**
	 * Delegate the task to another user
	 */
	public void delegateTask(long taskId, String fromUser, String toUser) {

		TaskClient taskClient = null;
		try {
			BlockingTaskOperationResponseHandler taskOperationHandler = new BlockingTaskOperationResponseHandler();
			taskClient = this.connectTaskServer();
			if (toUser == null) {
				taskClient.release(taskId, fromUser, taskOperationHandler);
			} else if (toUser.equals(fromUser)) {
				taskClient.claim(taskId, toUser, taskOperationHandler);
			} else {
				taskClient.delegate(taskId, fromUser, toUser, taskOperationHandler);
			}
		} catch (Exception ex) {
			logger.error("Error occured while delegating the task: " + taskId);
			throw new ProcessException(ex);
		} finally {
			try {
				if (taskClient != null) {
					taskClient.disconnect();
				}
			} catch (Exception ex) {
				logger.error("Error occured while closing the Task client");
				throw new ProcessException(ex);
			}
		}
	}

	public TaskView getTask(long taskId) {

		TaskClient taskClient = null;
		try {
			BlockingGetTaskResponseHandler responseHandler = new BlockingGetTaskResponseHandler();
			taskClient = this.connectTaskServer();
			taskClient.getTask(taskId, responseHandler);

			Task task = responseHandler.getTask();

			BlockingTaskSummaryResponseHandler taskSummaryHandler = new BlockingTaskSummaryResponseHandler();
			taskClient.getTasksByStatusByProcessId(task.getTaskData().getProcessInstanceId(),
					Collections.singletonList(task.getTaskData().getStatus()), userLocale, taskSummaryHandler);

			List<TaskSummary> taskSummaries = taskSummaryHandler.getResults();

			for (TaskSummary taskSummary : taskSummaries) {
				if (taskSummary.getId() == taskId) {
					return TaskViewAdapter.adaptToTaskView(taskSummary);
				}
			}
		} catch (Exception ex) {
			logger.error("Error occured while getting the task: " + taskId);
			throw new ProcessException(ex);
		} finally {
			try {
				if (taskClient != null) {
					taskClient.disconnect();
				}
			} catch (Exception ex) {
				logger.error("While closing the Task client got an Error");
				throw new ProcessException(ex);
			}
		}

		return null;
	}

	/**
	 * Method to get the applications
	 */
	@Transactional(readOnly = true)
	public List<Application> getApplicationsDetails() {

		if (logger.isInfoEnabled()) {
			logger.info("Inside WorkflowServiceImpl.getApplicationsDetails...");
		}

		List<Application> applications = applicationDao.getApplications();
		return applications;
	}

	public ProcessAuditData getProcessAuditData(ProcessAuditData auditData) {

		return processAuditDao.getProcessAuditData(auditData);
	}

	@Override
	public List<ProcessAuditData> getAllProcessAuditData() {

		return processAuditDao.getAllProcessAuditData();
	}

	@Override
	public int getProcessInstancesCount() throws ProcessException {

		return processAuditDao.getProcessInstancesCount();
	}

	@Override
	public int getTransactionsCount() throws ProcessException {

		return processAuditDao.getTransactionsCount();
	}

	public void setProcBizDataMapDao(ProcBizDataMapDao procBizDataMapDao) {
		this.procBizDataMapDao = procBizDataMapDao;
	}

	public void setApplicationDao(ApplicationDao applicationDao) {
		this.applicationDao = applicationDao;
	}

	public void setProcessAuditDao(ProcessAuditDao processAuditDao) {
		this.processAuditDao = processAuditDao;
	}

	public void setJbpmEmf(EntityManagerFactory jbpmEmf) {
		this.jbpmEmf = jbpmEmf;
	}

	public void setJbpmProcessEventListener(ProcessEventListener jbpmProcessEventListener) {
		this.jbpmProcessEventListener = jbpmProcessEventListener;
	}

	public KnowledgeBase getKbase() {
		return kbase;
	}

	public void setKbase(KnowledgeBase kbase) {
		this.kbase = kbase;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

}
