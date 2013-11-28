package com.sample.cusomer;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.faces.event.ActionEvent;
import org.drools.runtime.process.ProcessInstance;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jbpm.task.Content;
import org.jbpm.task.PeopleAssignments;
import org.jbpm.task.Status;
import org.jbpm.task.Task;
import org.jbpm.task.TaskService;
import org.jbpm.task.User;
import com.moneris.us.ordermanager.sales.OrderController;
import com.moneris.us.ordermanager.sales.beans.InternetSolutionBean;
import com.moneris.us.ordermanager.sales.entities.MerchantInformation;
import com.moneris.us.ordermanager.sales.entities.OrderDetails;
import com.moneris.us.ordermanager.sales.entities.ProductDetails;
import com.moneris.us.ordermanager.sales.entities.Products;
import com.moneris.us.ordermanager.sales.home.OrderDetailsHome;
import com.moneris.us.ordermanager.sales.types.CardPlanType;
import com.moneris.us.ordermanager.sales.types.OrderProduct;
import com.moneris.us.ordermanager.sales.types.OrderStatusType;
import com.moneris.us.ordermanager.upload.Phoenix.request.PhoenixUploadManager;
import com.moneris.us.ordermanager.worklist.WorklistSearchBean;
import com.moneris.us.ordermanager.worklist.entities.OmsWorkItem;

@Name("omsProcessClient")
public class OmsProcessClient {

	@Logger
	private Log log;

	@In(create = true)
	private SeamProcessKnowledgeSession seamProcessKnowledgeSession;
	private static final String JBPM_PROCESS = "com.moneris.us.ordermanager.process.omsProcessFlow";

	@In(create = true)
	private ProcessBean processBean;
	@In(create = true)
	private OrderDetailsHome orderDetailsHome;
	@In(create = true)
	private OmsWorkItemList omsWorkItemList;
	@In(create = true)
	private WorklistSearchBean worklistSearchBean;
	@In(create = true)
	private OrderController orderControl;
	@In(create = true)
	private Credentials credentials;
	@In(create = true)
	private InternetSolutionBean internetSolutionBean;
	@In(create = true)
	private PhoenixUploadManager phoenixUploadManager;

	private static Set<String> gatewayTPCCheck = new HashSet<String>();
	static {
		gatewayTPCCheck.add("IPAY - MCP");
		gatewayTPCCheck.add("RocketGate - MCP");
		gatewayTPCCheck.add("Plug n Pay - MCP");
		gatewayTPCCheck.add("USA ePAY - MCP");
	}

	// TODO we need to refactor this it's getting a little long. Split into two
	// classes one to handle handle starting the process and the other to work
	// on the tasks
	public long startProcess(int orderId, String salesUser) {
		this.orderDetailsHome.setOrderDetailsOrderId(orderId);
		OrderDetails oDetails = this.orderDetailsHome.getInstance();
		HashMap<String, Object> inputVal = this.getProcessInputMap(oDetails, salesUser);
		if (this.seamProcessKnowledgeSession.getKsession() == null) {
			this.seamProcessKnowledgeSession.init();
			log.warn("Null knowledgebase found : re-initializing knowledgebase");
		}
		ProcessInstance pInstance = this.seamProcessKnowledgeSession.getKsession().startProcess(JBPM_PROCESS, inputVal);
		this.orderDetailsHome.getInstance().setProcessId(pInstance.getId());
		return pInstance.getId();
	}

	private HashMap<String, Object> getProcessInputMap(OrderDetails oDetails, String salesUser) {
		HashMap<String, Object> inputVal = new HashMap<String, Object>();
		inputVal.put("orderId", oDetails.getOrderId());
		inputVal.put("isAutoApproved", new Boolean(false));
		inputVal.put("isMatchFound", new Boolean(true));
		inputVal.put("isSuccessfulFDMSUpload", new Boolean(false));
		inputVal.put("isSuccessfulPhoenixUpload", new Boolean(false));

		// TODO the variable isOrderedEselectPlus should be changed to
		// doPhoenixUpload
		inputVal.put("isOrderedEselectPlus", this.isEselectPlusOrdered());
		inputVal.put("showThirdPartySetup", this.showThirdPartySetup());

		inputVal.put("taskBeanSalesInput", OmsTaskBeanFactory.getTaskBean(OmsTaskNames.SALES_INPUT, salesUser));
		inputVal.put("taskBeanAttachDoc", OmsTaskBeanFactory.getTaskBean(OmsTaskNames.ATTACH_DOC, salesUser));
		inputVal.put("taskBeanManualAdj", OmsTaskBeanFactory.getTaskBean(OmsTaskNames.MANUAL_ADJ, salesUser));
		inputVal.put("taskBeanPreVerify", OmsTaskBeanFactory.getTaskBean(OmsTaskNames.PRE_VERIFY, salesUser));
		inputVal.put("taskBeanTPC", OmsTaskBeanFactory.getTaskBean(OmsTaskNames.TPC));
		inputVal.put("taskBeanActivation", OmsTaskBeanFactory.getTaskBean(OmsTaskNames.ACTIVATION));
		inputVal.put("taskBeanManualFDMS", OmsTaskBeanFactory.getTaskBean(OmsTaskNames.MANUAL_FDMS));
		inputVal.put("taskBeanManualPhoenix", OmsTaskBeanFactory.getTaskBean(OmsTaskNames.MANUAL_PHOENIX));
		inputVal.put("taskBeanDeployment", OmsTaskBeanFactory.getTaskBean(OmsTaskNames.DEPLOYMENT));
		inputVal.put("taskBeanPostVerify", OmsTaskBeanFactory.getTaskBean(OmsTaskNames.POST_VERIFY));
		inputVal.put("taskBeanReviewMatch", OmsTaskBeanFactory.getTaskBean(OmsTaskNames.REVIEW_MATCH));
		inputVal.put("taskBeanVendorSetup", OmsTaskBeanFactory.getTaskBean(OmsTaskNames.VENDORSETUP));
		inputVal.put("taskBeanMatchError", OmsTaskBeanFactory.getTaskBean(OmsTaskNames.MATCH_REQUEST_ERROR));
		inputVal.put("taskBeanProcessError", OmsTaskBeanFactory.getTaskBean(OmsTaskNames.PROCESS_ERROR));
		inputVal.put("omsWorkItem", this.getOmsWorkItemTemplate(oDetails));
		return inputVal;
	}

	public OmsWorkItem getOmsWorkItemTemplate(OrderDetails oDetails) {
		OmsWorkItem wItem = new OmsWorkItem();

		wItem.setExternalMerchantId(oDetails.getMerchant().getExternalMerchantId());
		wItem.setNameOfAccount(oDetails.getMerchant().getNameOfAccount());
		MerchantInformation pInfo = oDetails.getMerchant().getParentMerchant();
		if (pInfo != null) {
			wItem.setHqName(pInfo.getNameOfAccount());
		}
		wItem.setOrderCreated(oDetails.getOrderCreationTime());
		wItem.setPendDate(oDetails.getPendDate());
		wItem.setOrderId(oDetails.getOrderId());
		wItem.setRush(oDetails.getRushOrder() >= 1);
		return wItem;
	}

	public boolean isEselectPlusOrdered() {
		List<Products> prodList = this.orderDetailsHome.getInstance().getProductList();
		for (Products products : prodList) {
			if (products.getProductName().equals(OrderProduct.ESELECT.value())) {
				System.out.println("Eselect Found");
				return products.getOrdered().equals("true");
			}
		}
		System.out.println("Test for PC Solution");
		return this.phoenixUploadManager.doUploadForPCSolutions();
	}

	public boolean showThirdPartySetup() {
		List<Products> prodList = this.orderDetailsHome.getInstance().getProductList();
		for (Products products : prodList) {
			if (products.getProductName().equals(OrderProduct.AMEX.value()) || products.getProductName().equals(OrderProduct.AUTODEBIT.value())
					|| products.getProductName().equals(OrderProduct.CHECKCONVERSION.value()) || products.getProductName().equals(OrderProduct.GIFTCARD.value())
					|| products.getProductName().equals(OrderProduct.LOYALTYCARD.value())) {
				if (products.getOrdered().equals("true")) {
					if (products.getProductName().equals(OrderProduct.AMEX.value())) {
						boolean isNew = productDetailsEquals(products, "amexCardPlanType", CardPlanType.NEW.value());
						if (isNew) {
							return true;
						}
					} else {
						return true;
					}
				}
			}
		}
		String iGateway = this.internetSolutionBean.getInternetGateway();
		if (iGateway != null && gatewayTPCCheck.contains(iGateway)) {
			return true;
		}
		return false;
	}

	private boolean productDetailsEquals(Products prod, String detailName, String detailValue) {
		if (detailName != null && detailValue != null) {
			List<ProductDetails> pDetailsList = prod.getProductDetailsList();
			for (ProductDetails productDetails : pDetailsList) {
				if (detailName.equals(productDetails.getParameterName())) {
					return detailValue.equals(productDetails.getParameterValue());
				}
			}
		}
		return false;
	}

	public void openTask(OmsWorkItem omsWorkItem, String uid, List<String> groupIds) {
		this.processBean.setOmsWorkItem(omsWorkItem);
		this.openTask(omsWorkItem.getTaskId(), uid, omsWorkItem.getOrderId());
	}

	public void openTask(long taskid, String uid, int orderId) {
		this.claimTask(taskid, uid);
		this.orderControl.loadOrder(orderId);
	}

	public void claimTask(long taskid, String uid) {
		TaskService service = this.seamProcessKnowledgeSession.getTaskService();
		Task task = service.getTask(taskid);
		if (task.getTaskData().getStatus() == Status.Ready) {
			service.claim(taskid, uid);
		}
		if (task.getTaskData().getStatus() == Status.Reserved) {
			service.start(taskid, uid);
		}
	}

	public void claimTask(long taskid) {
		this.claimTask(taskid, this.credentials.getUsername());
	}

	public void claimTask(int orderId, OmsTaskNames taskName) {
		OmsWorkItem workItem = this.omsWorkItemList.getWorkItem(orderId, taskName.value());
		if (workItem != null) {
			this.claimTask(workItem.getTaskId());
		}
	}

	public void signalProcess(String eventType, Object data, long processInstanceId) {
		if (data == null) {
			System.out.println("data is null");
		}
		this.seamProcessKnowledgeSession.getKsession().signalEvent(eventType, data, processInstanceId);
	}

	public List<OmsWorkItem> getWorkList(String userOrGroup) {
		this.worklistSearchBean.setSearchAssigned(userOrGroup);
		return this.omsWorkItemList.getWorklistResults();
	}

	public void markTaskDeficient(ActionEvent event) {
		// Note: a default ActionListener with an ActionEvent argument is
		// required if calling a method with an argument in the actionListener
		// attribute.
		this.markTaskDeficient(this.processBean.getOmsWorkItem().getTaskId());
	}

	public void markTaskDeficient(long taskId) {
		TaskService service = this.seamProcessKnowledgeSession.getTaskService();
		Task task = service.getTask(taskId);
		this.signalProcess("deficient", null, task.getTaskData().getProcessInstanceId());
	}

	public boolean isUserAllowed(int orderId, String taskName) {
		// Note: Currently the system ignores cases when users logs in. This is
		// somewhat of an issue because the workflow is case sensitive. This
		// checks to confirm that the user is using a valid user id to perform
		// an operation on the task.
		String userId = this.credentials.getUsername();
		OmsWorkItem workItem = this.processBean.getOmsWorkItem();
		if (workItem == null || workItem.getOrderId() != orderId) {
			workItem = this.omsWorkItemList.getWorkItem(orderId, taskName);
		}
		String assignedUser = workItem.getAssignedUser();
		if (assignedUser == null || userId == null) {
			this.log.warn("A UserId is null. Login as:[" + userId +"] Assigned to:[" + assignedUser +"]");
			return false;
		}
		boolean isAllowed = assignedUser.equals(userId);
		if(isAllowed){
			return true;
		}
		this.log.warn("UserIds don't match. Login as:[" + userId +"] Assigned to:[" + assignedUser +"]");
		return false;
	}

	public void completeTask(ActionEvent event) {
		// Note: a default ActionListener with an ActionEvent argument is
		// required if calling a method with an argument in the actionListener
		// attribute.
		throw new RuntimeException("This method should not be called.  Please use completeTask(String userName)");
	}

	public void completeTask(String userName) {
		this.completeTask(this.processBean.getTaskId(), userName);
	}

	public void completeTask() {
		this.completeTask(this.processBean.getTaskId(), credentials.getUsername());
	}

	public void completeTask(long taskid, String userName) {
		TaskService service = this.seamProcessKnowledgeSession.getTaskService();
		Task task = service.getTask(taskid);
		if (task.getTaskData().getStatus() != Status.InProgress) {
			this.claimTask(taskid, userName);
		}
		service.complete(taskid, userName, null);
	}

	public void completeTask(int orderId, String taskName) {
		this.completeTask(orderId, taskName, this.credentials.getUsername());
	}
	
	public void completeTask(int orderId, String taskName, String userId) {
		OmsWorkItem workItem = this.processBean.getOmsWorkItem();
		if (workItem == null || workItem.getOrderId() != orderId) {
			workItem = this.omsWorkItemList.getWorkItem(orderId, taskName);
			this.processBean.setOmsWorkItem(workItem);
		}
		this.completeTask(userId);
	}

	public void cancelOrder(ActionEvent event) {
		throw new RuntimeException("This method should not be called.  Please use setWorklistParamListener(TaskSummary taskSummary, Integer orderId)");
	}

	public void cancelOrder(OrderDetails orderDetails) {
		this.abortOrder(orderDetails, OrderStatusType.CANCELED);
	}

	public void declineOrder(OrderDetails orderDetails) {
		// TODO instead of abort we need to reassign the task to a different
		// role
		// 1 unassign task
		// 2 assign task to different role
		this.abortOrder(orderDetails, OrderStatusType.DECLINED);
	}

	private void abortOrder(OrderDetails orderDetails, OrderStatusType status) {
		// TODO test to see if this perstist task status properly
		int orderId = orderDetails.getOrderId();
		List<Long> taskIdList = new ArrayList<Long>();
		List<OmsWorkItem> workItemList = this.omsWorkItemList.getWorkItems(orderId);
		for (OmsWorkItem omsWorkItem : workItemList) {
			taskIdList.add(omsWorkItem.getTaskId());
		}
		this.abortFlow(orderDetails.getProcessId(), taskIdList);
		orderDetails.setOrderStatus(status.value());
	}

	public void cancelOrder(long mainProcessId, long taskId, String userName) {
		List<Long> taskIdList = new ArrayList<Long>();
		taskIdList.add(taskId);
		this.abortFlow(mainProcessId, taskIdList);
		this.orderDetailsHome.getInstance().setOrderStatus(OrderStatusType.CANCELED.value());
		this.orderDetailsHome.update();
	}

	public void declineOrder(long mainProcessId, long taskId, String userName) {
		List<Long> taskIdList = new ArrayList<Long>();
		taskIdList.add(taskId);
		this.abortFlow(mainProcessId, taskIdList);
		this.orderDetailsHome.getInstance().setOrderStatus(OrderStatusType.DECLINED.value());
		this.orderDetailsHome.update();
	}

	private void abortFlow(long mainProcessId, List<Long> taskId) {
		TaskService service = this.seamProcessKnowledgeSession.getTaskService();
		this.seamProcessKnowledgeSession.getKsession().abortProcessInstance(mainProcessId);
		for (Long tid : taskId) {
			Task task = service.getTask(tid);
			long taskProcessId = task.getTaskData().getProcessInstanceId();
			this.seamProcessKnowledgeSession.getKsession().abortProcessInstance(taskProcessId);
		}
	}

	public void setWorklistParamListener(ActionEvent event) {
		// Note: a default ActionListener with an ActionEvent argument is
		// required if calling a method with an argument in the actionListener
		// attribute.
		throw new RuntimeException("This method should not be called.  Please use setWorklistParamListener(TaskSummary taskSummary, Integer orderId)");
	}

	public void setWorklistParamListener(OmsWorkItem omsWorkItem) {
		this.processBean.setOmsWorkItem(omsWorkItem);
	}

	public String getScreenName() {
		String taskName = this.processBean.getOmsWorkItem().getTaskName();
		if (taskName != null && taskName.startsWith("(Deficient)")) {
			taskName = "Deficient";
		}
		return taskName;
	}

	public Map<String, Object> getTaskContent(long taskId) {
		TaskService service = this.seamProcessKnowledgeSession.getTaskService();
		Content content = service.getContent(taskId);
		ByteArrayInputStream bais = new ByteArrayInputStream(content.getContent());
		try {
			ObjectInputStream is = new ObjectInputStream(bais);
			Object obj = null;
			Map<String, Object> taskOutParam = new HashMap<String, Object>();
			while ((obj = is.readObject()) != null) {
				System.out.println("OBJECT: " + obj);
				if (Map.class.isAssignableFrom(obj.getClass())) {
					taskOutParam = (HashMap<String, Object>) obj;
					return taskOutParam;
				}
				return null;
			}
		} catch (Exception e) {
			log.error("There was an error reading task content...", e);
		}
		return null;
	}

	public void reassignTaskToUser(long taskid, String userId) {
		System.out.println("ReassignTaskToUser [" + taskid + "][" + userId + "]");
		this.unassignTask(taskid);
		this.claimTask(taskid, userId);
	}

	public void reassignTaskToRole(long taskid, String role) {
		System.out.println("ReassignTaskToRole [" + taskid + "][" + role + "]");
		TaskService service = this.seamProcessKnowledgeSession.getTaskService();
		Task task = service.getTask(taskid);

		// For some reason we need to add the below code to allow the user to
		// call the forward method. Otherwise we will get a
		// org.jbpm.task.service.PermissionDeniedException exception. Some more
		// investigation might be required to see if we can remove the below
		// lines
		User actualOwner = task.getTaskData().getActualOwner();
		if (actualOwner != null) {
			PeopleAssignments peopleAssignments = task.getPeopleAssignments();
			if (!peopleAssignments.getPotentialOwners().contains(actualOwner)) {
				peopleAssignments.getPotentialOwners().add(actualOwner);
			}
		}
		// For the above lines of code refer to the previous comment

		service.forward(taskid, this.credentials.getUsername(), role);
	}

	public void unassignTask(long taskid) {
		TaskService service = this.seamProcessKnowledgeSession.getTaskService();
		Task task = service.getTask(taskid);
		Status taskStatus = task.getTaskData().getStatus();
		if (taskStatus == Status.Reserved || taskStatus == Status.InProgress) {
			service.release(taskid, "Administrator");
		}
	}

	public void refreshWorklistItem(long taskid, OmsWorkItem oWorkItem) {
		TaskService service = this.seamProcessKnowledgeSession.getTaskService();
		Task task = service.getTask(taskid);
		long taskProcessId = task.getTaskData().getProcessInstanceId();
		this.signalProcess("refreshWorkItem", oWorkItem, taskProcessId);
	}

}
