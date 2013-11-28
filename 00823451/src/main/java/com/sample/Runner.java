package com.sample;


import org.jbpm.task.service.DefaultUserGroupCallbackImpl;
import org.jbpm.task.service.UserGroupCallbackManager;
import org.jbpm.test.JBPMHelper;

public class Runner {

	public static void main(String[] args) {
		
		startUp();
		System.out.println("--------------- Run User Task with Content setted ----------------------");
		new IssueReproduction("test.bpmn", "org.jbpm.quickstart.usertask").reproduction();
		Runtime.getRuntime().exit(0);
	}
	

	private static void startUp() {
		JBPMHelper.startH2Server();
        JBPMHelper.setupDataSource();
        UserGroupCallbackManager.getInstance().setCallback(new DefaultUserGroupCallbackImpl());
	}

}
