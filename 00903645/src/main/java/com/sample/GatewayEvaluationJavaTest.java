package com.sample;

import java.util.HashMap;
import java.util.Map;

import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.bpmn2.xml.XmlBPMNProcessDumper;
import org.jbpm.ruleflow.core.RuleFlowProcess;
import org.jbpm.test.JbpmJUnitTestCase;
import org.junit.Test;

/**
 * This is a sample file to test a process.
 */
public class GatewayEvaluationJavaTest extends JbpmJUnitTestCase {

	@Test
	public void testProcess() {
		Map <String,Object> procVar = new HashMap<String,Object>();
		procVar.put("myVar", new Integer("2"));
		StatefulKnowledgeSession ksession = createKnowledgeSession("GatewayEvaluationJava.bpmn");
		ProcessInstance processInstance = ksession.startProcess("com.sample.bpmn.hello",procVar);
		System.out.println("processInstance: "+processInstance);
		RuleFlowProcess definition = ((RuleFlowProcess)processInstance.getProcess());
		System.out.println(definition);
 		String str = XmlBPMNProcessDumper.INSTANCE.dump(definition, true);
		System.out.println(str);
		
		// check whether the process instance has completed successfully
		//assertProcessInstanceCompleted(processInstance.getId(), ksession);
		//assertNodeTriggered(processInstance.getId(), "StartProcess", "Hello", "EndProcess");
	}

}