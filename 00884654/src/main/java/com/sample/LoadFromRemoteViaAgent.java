package com.sample;

import org.drools.KnowledgeBase;
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.agent.impl.PrintStreamSystemEventListener;
import org.drools.event.knowledgeagent.AfterChangeSetAppliedEvent;
import org.drools.event.knowledgeagent.AfterChangeSetProcessedEvent;
import org.drools.event.knowledgeagent.AfterResourceProcessedEvent;
import org.drools.event.knowledgeagent.BeforeChangeSetAppliedEvent;
import org.drools.event.knowledgeagent.BeforeChangeSetProcessedEvent;
import org.drools.event.knowledgeagent.BeforeResourceProcessedEvent;
import org.drools.event.knowledgeagent.KnowledgeAgentEventListener;
import org.drools.event.knowledgeagent.KnowledgeBaseUpdatedEvent;
import org.drools.event.knowledgeagent.ResourceCompilationFailedEvent;
import org.drools.io.ResourceChangeScannerConfiguration;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

public class LoadFromRemoteViaAgent {
	
	static KnowledgeAgent kagent;

	private static StatefulKnowledgeSession readKnowledgeSession(String changeset) throws Exception {
		kagent = KnowledgeAgentFactory.newKnowledgeAgent("kagent");  
		ResourceChangeScannerConfiguration sconf = ResourceFactory.getResourceChangeScannerService().newResourceChangeScannerConfiguration();
		sconf.setProperty("drools.resource.scanner.interval","30");
		ResourceFactory.getResourceChangeScannerService().configure(sconf);
		ResourceFactory.getResourceChangeNotifierService().start();
		ResourceFactory.getResourceChangeScannerService().start();
		kagent.setSystemEventListener(new PrintStreamSystemEventListener());
		kagent.applyChangeSet(ResourceFactory.newClassPathResource(changeset));
		kagent.addEventListener(new KnowledgeAgentEventListener(){

			public void beforeChangeSetApplied(BeforeChangeSetAppliedEvent event) {
			}

			public void afterChangeSetApplied(AfterChangeSetAppliedEvent event) {
			}

			public void beforeChangeSetProcessed(BeforeChangeSetProcessedEvent event) {
			}

			public void afterChangeSetProcessed(AfterChangeSetProcessedEvent event) {
			}

			public void beforeResourceProcessed(BeforeResourceProcessedEvent event) {
			}

			public void afterResourceProcessed(AfterResourceProcessedEvent event) {
			}

			public void knowledgeBaseUpdated(KnowledgeBaseUpdatedEvent event) {
				System.out.println("knowledgeBase updated, fire all rules");
				event.getKnowledgeBase().newStatefulKnowledgeSession().fireAllRules();
			}

			public void resourceCompilationFailed(ResourceCompilationFailedEvent event) {
				
			}});
		KnowledgeBase kbase = kagent.getKnowledgeBase();
		return kbase.newStatefulKnowledgeSession();
	}

	public static void main(String[] args) throws Exception {

		StatefulKnowledgeSession ksession = readKnowledgeSession("changeset.xml");
		ksession.fireAllRules();
		ksession.dispose();
		
//		kagent.dispose();
	}

}
