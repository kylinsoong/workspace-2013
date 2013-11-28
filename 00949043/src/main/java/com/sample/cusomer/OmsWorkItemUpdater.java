package com.sample.cusomer;

import org.jboss.seam.Component;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;
import com.moneris.us.ordermanager.sales.OrderController;
import com.moneris.us.ordermanager.sales.entities.MerchantInformation;
import com.moneris.us.ordermanager.sales.entities.OrderDetails;
import com.moneris.us.ordermanager.sales.home.OrderDetailsHome;
import com.moneris.us.ordermanager.worklist.entities.OmsWorkItem;

@Name("omsWorkItemUpdater")
public class OmsWorkItemUpdater {

	@Logger
	private Log log;
	@In(create = true)
	private OmsWorkItemHome omsWorkItemHome;
	@In(create = true)
	private OrderDetailsHome orderDetailsHome;
	@In(create = true)
	private OmsProcessClient omsProcessClient;
	@In(create = true)
	private OrderController orderControl;

	public OmsWorkItem refreshDetails(OmsWorkItem oWorkItem) {
		if (oWorkItem != null) {
			this.orderDetailsHome.setOrderDetailsOrderId(oWorkItem.getOrderId());
			this.orderDetailsHome.wire();
			OrderDetails oDetails = this.orderDetailsHome.getInstance();
			OmsWorkItem wItem = this.updateDBWorkItem(oWorkItem);
			this.omsProcessClient.signalProcess("refreshWorkItem", this.omsProcessClient.getOmsWorkItemTemplate(oDetails), oDetails.getProcessId());
			OmsWorkItem refreshedItem = (OmsWorkItem) wItem.clone();
			refreshedItem.setId(0);
			this.omsProcessClient.refreshWorklistItem(oWorkItem.getTaskId(), refreshedItem);
			return wItem;
		}
		log.warn("For some reason oWorkItem is null - no refresh done");
		return new OmsWorkItem();
	}

	public OmsWorkItem updateDBWorkItem(OmsWorkItem oWorkItem) {
		OmsWorkItem wItem = new OmsWorkItem();
		this.omsWorkItemHome.setOmsWorkItemId(oWorkItem.getId());
		this.omsWorkItemHome.wire();
		wItem = this.refreshWorkItemBean(this.omsWorkItemHome.getInstance());
		this.omsWorkItemHome.update();
		return wItem;
	}

	public OmsWorkItem refreshWorkItemBean(OmsWorkItem oldWorkItem) {
		this.orderDetailsHome.setOrderDetailsOrderId(oldWorkItem.getOrderId());
		this.orderDetailsHome.wire();
		OrderDetails oDetails = this.orderDetailsHome.getInstance();
		oldWorkItem.setExternalMerchantId(oDetails.getMerchant().getExternalMerchantId());
		MerchantInformation pInfo = oDetails.getMerchant().getParentMerchant();
		if (pInfo != null) {
			oldWorkItem.setHqName(pInfo.getNameOfAccount());
		}
		oldWorkItem.setRush(oDetails.getRushOrder() >= 1);
		oldWorkItem.setNameOfAccount(oDetails.getMerchant().getNameOfAccount());
		return oldWorkItem;
	}

	public boolean getShowThirdPartySetup(Integer orderId) {
		this.orderControl.loadOrder(orderId);
		return this.omsProcessClient.showThirdPartySetup();
	}

	public boolean getIsEselectPlusOrdered(Integer orderId) {
		this.orderControl.loadOrder(orderId);
		return this.omsProcessClient.isEselectPlusOrdered();
	}

	public static OmsWorkItemUpdater getComponentInstance() throws Exception {
		OmsWorkItemUpdater omsWorkItemUpdater = (OmsWorkItemUpdater) Component.getInstance(OmsWorkItemUpdater.class, true);
		return omsWorkItemUpdater;
	}
}
