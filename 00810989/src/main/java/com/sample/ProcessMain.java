package com.sample;

import java.util.Collection;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.bpmn2.core.Definitions;
import org.jbpm.workflow.core.impl.WorkflowProcessImpl;
import org.drools.runtime.process.NodeInstance;
import org.jbpm.workflow.instance.impl.WorkflowProcessInstanceImpl;
import org.drools.definition.process.Node;

/**
 * This is a sample file to launch a process.
 */
public class ProcessMain {

	public static final void main(String[] args) throws Exception {
		// load up the knowledge base
		KnowledgeBase kbase = readKnowledgeBase();
		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		// start a new process instance
		ProcessInstance processInstance = ksession.startProcess("com.sample.bpmn.hello");
		WorkflowProcessImpl process = (WorkflowProcessImpl) processInstance.getProcess();
		Node[] nodes = process.getNodes();
		for (Node node : nodes){
			System.out.println(node.getName());
		}
		
//		WorkflowProcessInstanceImpl workflowProcessInstanceImpl = (WorkflowProcessInstanceImpl) processInstance;
//		Collection<NodeInstance> nodeInstances  = workflowProcessInstanceImpl.getNodeInstances();
//		for (NodeInstance nodeInstance : nodeInstances){
//			System.out.println("current node -> " + nodeInstance.getNodeName());
//		}
//		Collection<NodeInstance> nodeInstances = ((WorkflowProcessInstanceImpl)processInstance).getNodeInstances();
		
//		System.out.println(processInstance.getProcess().getId());
//		System.out.println(processInstance.getProcess().getName());
//		
//		Definitions definitions = (Definitions) processInstance.getProcess().getMetaData().get("Definitions");
//		
//		System.out.println(processInstance.getProcess().getKnowledgeType());
	}

	private static KnowledgeBase readKnowledgeBase() throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("sample.bpmn"), ResourceType.BPMN2);
		return kbuilder.newKnowledgeBase();
	}
	
}















