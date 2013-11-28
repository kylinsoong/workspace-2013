package org.jbpm.test.humantask;

import java.util.HashMap;
import java.util.Map;

public class SupportCase00964739 {

	public static void main(String[] args) throws Exception {
		
		JBPMHumanTaskExecutor.init();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("task_autocomplete", Boolean.TRUE);
		JBPMHumanTaskExecutor.getKsession().startProcess("com.sample.bpmn.hello", params);
		
	}
	

}
