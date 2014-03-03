package com.kybpm.web.model;

import java.io.Serializable;

public class SelectedItem implements Serializable {
	private boolean selected;
	private Integer productId;
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
}
