package com.kybpm.web.model;

import org.jboss.seam.ScopeType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;

@Name("productList")
@Scope(ScopeType.CONVERSATION)
public class ProductList extends ItemList implements Serializable {

	@DataModel("resultList")
	private List<Product> resultList;
	
	private String searchType;
	private String searchField;
	
	public static final String NAME_SEARCH_TYPE = "Product Name";
	public static final String DESCRIPTION_SEARCH_TYPE = "Product Description";
	public static final String VENDOR_SEARCH_TYPE = "Vendor";
	
	public ProductList () {
	}
	
	public ProductList(List<Product> resultList) {
		this.resultList = resultList;
	}
	
	@Create
	public void init() {
		this.resultList = new ArrayList<Product>();
		this.searchField = "";
		this.searchType = "";
		this.setOrderColumn("");
		this.setOrderDirection("");
		this.setSelectedItem(0);
	}
	
	public List<Product> getResultList() {
		return resultList;
	}

	public void setResultList(List<Product> resultList) {
		this.resultList = resultList;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
}
