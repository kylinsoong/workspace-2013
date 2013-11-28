package com.sample;

import org.drools.KnowledgeBase;
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.agent.impl.PrintStreamSystemEventListener;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.bpmn2.xml.XmlBPMNProcessDumper;
import org.jbpm.ruleflow.core.RuleFlowProcess;

public class SupportCase917804 {

	public static void main(String[] args) {

		KnowledgeAgent kagent = KnowledgeAgentFactory.newKnowledgeAgent("kagent"); 
		ResourceFactory.getResourceChangeNotifierService().start();
		ResourceFactory.getResourceChangeScannerService().start();
		kagent.setSystemEventListener(new PrintStreamSystemEventListener());
		kagent.applyChangeSet(ResourceFactory.newClassPathResource("changeset.xml"));
		KnowledgeBase kbase = kagent.getKnowledgeBase();
		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		ProcessInstance processInstance = ksession.startProcess("com.sample.bpmn.hello");
		RuleFlowProcess definition = ((RuleFlowProcess)processInstance.getProcess());
 		String str = XmlBPMNProcessDumper.INSTANCE.dump(definition,true);
		System.out.println(str);
		ksession.dispose();
	}

}
