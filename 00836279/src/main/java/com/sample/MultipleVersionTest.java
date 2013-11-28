package com.sample;

import org.drools.KnowledgeBase;
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.agent.impl.PrintStreamSystemEventListener;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

public class MultipleVersionTest {
	
	static KnowledgeAgent kagent;

	private static StatefulKnowledgeSession readKnowledgeSession(String changeset) throws Exception {
		kagent = KnowledgeAgentFactory.newKnowledgeAgent("kagent");    
		ResourceFactory.getResourceChangeNotifierService().start();
		ResourceFactory.getResourceChangeScannerService().start();
		kagent.setSystemEventListener(new PrintStreamSystemEventListener());
		kagent.applyChangeSet(ResourceFactory.newClassPathResource(changeset));
		KnowledgeBase kbase = kagent.getKnowledgeBase();
		return kbase.newStatefulKnowledgeSession();
	}
	
	public static void main(String[] args) throws Exception {

		StatefulKnowledgeSession ksession1 = readKnowledgeSession("changeset-1.xml");
		ksession1.fireAllRules();
		ksession1.dispose();
		
		StatefulKnowledgeSession ksession2 = readKnowledgeSession("changeset-2.xml");
		ksession2.fireAllRules();
		ksession2.dispose();
		
		kagent.dispose();
	}
}
