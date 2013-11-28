package com.sample;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.io.impl.UrlResource;
import org.drools.runtime.StatefulKnowledgeSession;

public class SingalEventSend {

	private static StatefulKnowledgeSession newKnowledgeSession(String url)throws Exception {
		UrlResource resource = (UrlResource) ResourceFactory.newUrlResource(url);
		resource.setBasicAuthentication("enabled");
		resource.setUsername("admin");
		resource.setPassword("admin");
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(resource, ResourceType.PKG);
		KnowledgeBase kbase = kbuilder.newKnowledgeBase();
		return kbase.newStatefulKnowledgeSession();
	}

	public static void main(String[] args) throws Exception {

		StatefulKnowledgeSession ksession = newKnowledgeSession("http://localhost:8080/jboss-brms/org.drools.guvnor.Guvnor/package/org.drools.bpmn2/LATEST");
		ksession.startProcess("com.sample.bpmn.hello");
		ksession.signalEvent("payment", 110);
		System.out.println("Done");
	}

}
