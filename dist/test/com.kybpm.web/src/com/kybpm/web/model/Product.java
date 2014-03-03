package com.kybpm.web.model;

import org.jboss.seam.ScopeType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("product")
@Scope(ScopeType.CONVERSATION)
public class Product implements Serializable{

	private Integer productId;
	private ProductType productType;
	private String productName;
	private String productDescription;
	private Vendor vendor;
	private double cost;
	private double price;
	private long quantity;
	private byte[] productImage;
	private List<ProductOrder> productOrders = new ArrayList<ProductOrder>(0);
	private List<Discount> discounts = new ArrayList<Discount>(0);

	public Product() {
	}

	public Product(Integer productId, ProductType productType, String productName,
			String productDescription, Vendor vendor, double cost, double price,
			long quantity, byte[] productImage) {
		this.productId = productId;
		this.productType = productType;
		this.productName = productName;
		this.productDescription = productDescription;
		this.vendor = vendor;
		this.cost = cost;
		this.price = price;
		this.quantity = quantity;
		this.productImage = productImage;
	}
	public Product(Integer productId, ProductType productType, String productName,
			String productDescription, Vendor vendor, double cost, double price,
			long quantity, byte[] productImage,
			List<ProductOrder> productOrders, List<Discount> discounts) {
		this.productId = productId;
		this.productType = productType;
		this.productName = productName;
		this.productDescription = productDescription;
		this.vendor = vendor;
		this.cost = cost;
		this.price = price;
		this.quantity = quantity;
		this.productImage = productImage;
		this.productOrders = productOrders;
		this.discounts = discounts;
	}

	@Create
	public void init() {
		this.productId = 0;
		this.productType = new ProductType();
		this.productName = "";
		this.productDescription = "";
		this.vendor = new Vendor();
		this.cost = 0.0;
		this.price = 0.0;
		this.quantity = 0;
		this.productImage = null;
		this.productOrders = new ArrayList<ProductOrder>();
		this.discounts = new ArrayList<Discount>();
	}
	public Integer getProductId() {
		return this.productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public ProductType getProductType() {
		return this.productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return this.productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	
	public Vendor getVendor() {
		return this.vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public double getCost() {
		return this.cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getQuantity() {
		return this.quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public byte[] getProductImage() {
		return this.productImage;
	}

	public void setProductImage(byte[] productImage) {
		this.productImage = productImage;
	}

	public List<ProductOrder> getProductOrders() {
		return this.productOrders;
	}

	public void setProductOrders(List<ProductOrder> productOrders) {
		this.productOrders = productOrders;
	}

	public List<Discount> getDiscounts() {
		return this.discounts;
	}

	public void setDiscounts(List<Discount> discounts) {
		this.discounts = discounts;
	}

	public void copyTo(Product target) {
		target.cost = this.cost;
		target.discounts.clear();
		target.discounts.addAll(this.discounts);
		target.price = this.price;
		target.productDescription = this.productDescription;
		target.productId = this.productId;
		target.productImage = this.productImage;
		target.productName = this.productName;
		target.productOrders.clear();
		target.productOrders.addAll(this.productOrders);
		target.productType = this.productType;
		target.quantity = this.quantity;
		target.vendor = this.vendor;
	}
	
	public com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.Product toOrderServiceProduct() {
		com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.Product wsProduct = 
				new com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.Product();
		wsProduct.setCost(this.cost);
		wsProduct.setPrice(this.price);
		wsProduct.setProductDescription(this.productDescription);
		wsProduct.setProductId(this.productId);
		//wsProduct.setProductImage(this.productImage);
		wsProduct.setProductName(this.productName);
		wsProduct.setQuantity(this.quantity);
		wsProduct.setVendor(this.vendor.toOrderServiceVendor());
		com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.Discount[] wsDiscounts = 
				new com.kybpm.web.ws.client.OrderManagerServiceImplServiceStub.Discount[this.discounts.size()];
		for(int i = 0; i < this.discounts.size(); i++) {
			wsDiscounts[i] = discounts.get(i).toOrderServiceDiscount();
		}
		wsProduct.setDiscounts(wsDiscounts);
		wsProduct.setProductType(this.productType.toOrderServiceProductType());
		return wsProduct;
	}
	
	public com.kybpm.web.ws.client.CalculateServiceImplServiceStub.Product toCalculateServiceProduct() {
		com.kybpm.web.ws.client.CalculateServiceImplServiceStub.Product wsProduct = 
				new com.kybpm.web.ws.client.CalculateServiceImplServiceStub.Product();
		wsProduct.setCost(this.cost);
		wsProduct.setPrice(this.price);
		wsProduct.setProductDescription(this.productDescription);
		wsProduct.setProductId(this.productId);
		//wsProduct.setProductImage(this.productImage);
		wsProduct.setProductName(this.productName);
		wsProduct.setQuantity(this.quantity);
		wsProduct.setVendor(this.vendor.toCalculateServiceVendor());
		com.kybpm.web.ws.client.CalculateServiceImplServiceStub.Discount[] wsDiscounts = 
				new com.kybpm.web.ws.client.CalculateServiceImplServiceStub.Discount[this.discounts.size()];
		for(int i = 0; i < this.discounts.size(); i++) {
			wsDiscounts[i] = discounts.get(i).toCalculateServiceDiscount();
		}
		wsProduct.setDiscounts(wsDiscounts);
		wsProduct.setProductType(this.productType.toCalculateServiceProductType());
		return wsProduct;
	}
}
