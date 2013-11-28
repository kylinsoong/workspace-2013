package com.sample.cusomer;

import java.util.Date;
import org.jboss.seam.Component;
import org.jbpm.task.Task;
import org.jbpm.task.service.ContentData;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.local.LocalTaskService;
import com.moneris.us.ordermanager.worklist.entities.OmsWorkItem;

public class MonerisLocalTaskService extends LocalTaskService {

	public MonerisLocalTaskService(TaskService taskService) {
		super(taskService);
	}

	@Override
	public void start(long taskId, String userId) {
		super.start(taskId, userId);
		this.updateOmsWorkItem(taskId, TaskStatus.ASSIGNED.value(), userId);
	}

	@Override
	public void complete(long taskId, String userId, ContentData outputData) {
		super.complete(taskId, userId, outputData);
		this.updateOmsWorkItem(taskId, TaskStatus.COMPLETED.value(), userId);
	}

	@Override
	public void exit(long taskId, String userId) {
		super.exit(taskId, userId);
		this.updateOmsWorkItem(taskId, TaskStatus.CANCELED.value(), userId);
	}

	@Override
	public void release(long taskId, String userId) {
		super.release(taskId, userId);
		this.updateOmsWorkItem(taskId, TaskStatus.UNASSIGNED.value(), userId);
	}

	@Override
	public void stop(long taskId, String userId) {
		super.stop(taskId, userId);
		this.updateOmsWorkItem(taskId, TaskStatus.CANCELED.value(), userId);
	}

	@Override
	public void forward(long taskId, String userId, String targetEntityId) {
		super.forward(taskId, userId, targetEntityId);
		this.updateOmsWorkItem(taskId, TaskStatus.UNASSIGNED.value(), userId, targetEntityId);
	}

	// TODO there is a bug in this code. It throws a
	// javax.persistence.TransactionRequiredException: no transaction is in
	// progress whenever we mark deficient
//	@Override
//	public void addTask(Task task, ContentData content) {
//		System.out.println("Call add Task");
//		OrderStatusManager statusManager = new OrderStatusManager();
//		statusManager.updateTaskStatus(content);
//		super.addTask(task, content);
//	}

	private void updateOmsWorkItem(long taskid, String taskStatus, String userid) {
		this.updateOmsWorkItem(taskid, taskStatus, userid, null);
	}

	private void updateOmsWorkItem(long taskid, String taskStatus, String userid, String group) {
		OmsWorkItemList wItemList = (OmsWorkItemList) Component.getInstance(OmsWorkItemList.class, true);
		OmsWorkItem wItem = wItemList.getWorkItem(taskid);
		if (wItem != null) {
			if (group != null) {
				wItem.setAssignedGroup(group);
			}
			wItem.setTaskStatus(taskStatus);
			if (wItem.getAssignedUser() == null || userid != "Administrator") {
				wItem.setAssignedUser(userid);
			}
			if (taskStatus.equals(TaskStatus.COMPLETED.value()) || taskStatus.equals(TaskStatus.CANCELED.value())) {
				wItem.setTaskCompleted(new Date());
			}
			if (taskStatus.equals(TaskStatus.ASSIGNED.value())) {
				wItem.setTaskStarted(new Date());
			}
			OmsWorkItemHome wItemHome = (OmsWorkItemHome) Component.getInstance(OmsWorkItemHome.class, true);
			wItemHome.setInstance(wItem);
			wItemHome.update();
			wItemHome.clearInstance();
		} else {
			// Log warning message regarding failed search
			System.out.println("==================Task ID: " + taskid + " Returned Null Result.");
		}
	}

}
