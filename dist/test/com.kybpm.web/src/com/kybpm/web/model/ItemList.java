package com.kybpm.web.model;

import java.io.Serializable;

public class ItemList implements Serializable {
	
	public static final String ORDER_DIRECTION_ASC = "asc";
	public static final String ORDER_DIRECTION_DESC = "desc";
	
	private String orderColumn;
	private String orderDirection;
	private Integer selectedItem;
	
	public ItemList() {
	}

	public ItemList(String orderColumn, String orderDirection, Integer selectedItem) {
		this.orderColumn = orderColumn;
		this.orderDirection = orderDirection;
		this.selectedItem = selectedItem;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}
	
	public Integer getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(Integer selectedItem) {
		this.selectedItem = selectedItem;
	}
}
