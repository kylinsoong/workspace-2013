package com.sample;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.io.impl.UrlResource;
import org.drools.runtime.StatefulKnowledgeSession;

public class MultipleVersionTestAPI {

	public static void main(String[] args) throws Exception {
		
		StatefulKnowledgeSession ksession1 = newKnowledgeSession("http://localhost:8080/jboss-brms/org.drools.guvnor.Guvnor/package/com.sample/V1");
		ksession1.fireAllRules();
		ksession1.dispose();
		
		StatefulKnowledgeSession ksession2 = newKnowledgeSession("http://localhost:8080/jboss-brms/org.drools.guvnor.Guvnor/package/com.sample/V2");
		ksession2.fireAllRules();
		ksession2.dispose();
	}
	
	private static StatefulKnowledgeSession newKnowledgeSession(String url) throws Exception {
		KnowledgeBase kbase = readKnowledgeBase(url);
		return kbase.newStatefulKnowledgeSession();
	}

	@SuppressWarnings("restriction")
	private static KnowledgeBase readKnowledgeBase(String url) throws Exception {

		UrlResource resource = (UrlResource) ResourceFactory.newUrlResource(url);
		resource.setBasicAuthentication("enabled");
        resource.setUsername("admin");
        resource.setPassword("admin");
        
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(resource, ResourceType.PKG);
        
        return kbuilder.newKnowledgeBase();
	}

}
