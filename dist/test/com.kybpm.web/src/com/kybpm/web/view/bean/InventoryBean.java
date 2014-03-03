package com.kybpm.web.view.bean;

import java.util.ArrayList;

import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;

import com.kybpm.web.model.Cart;
import com.kybpm.web.model.Discount;
import com.kybpm.web.model.Product;
import com.kybpm.web.model.ProductList;
import com.kybpm.web.model.ProductOrder;
import com.kybpm.web.model.ProductType;
import com.kybpm.web.model.Vendor;
import com.kybpm.web.ws.client.InventoryManagerServiceImplServiceStub;
import com.kybpm.web.ws.client.InventoryManagerServiceImplServiceStub.GetProducts;
import com.kybpm.web.ws.client.InventoryManagerServiceImplServiceStub.GetProductsResponse;

@Name("inventory")
public class InventoryBean extends BaseBean implements Inventory {

	@Logger
	private Log log;
	
	@In(create=true) @Out
	private Product product;
	
	@In(create=true) @Out
	private ProductList productList;
	
	@In(create=true) @Out
	private Cart cart;
	
	@In
	FacesMessages facesMessages;
	
	private String searchField;
	
	@In(create=true)
	private Checkout checkout;
	
	@Begin(join=true)
	public String search() {
		
		try {
			if(productList.getSearchType().isEmpty() && !productList.getSearchField().isEmpty()) {
				facesMessages.add("A search type must be selected.");
			}
			else {

				InventoryManagerServiceImplServiceStub stub = new InventoryManagerServiceImplServiceStub();
				GetProductsResponse getProductsResponse;
				GetProducts getProducts = new GetProducts();
				com.kybpm.web.ws.client.InventoryManagerServiceImplServiceStub.Product wsProduct = new com.kybpm.web.ws.client.InventoryManagerServiceImplServiceStub.Product();

				if(productList.getSearchType().equals(productList.NAME_SEARCH_TYPE)) {
					product.setProductName(productList.getSearchField());
					wsProduct.setProductName(product.getProductName());
				} else if(productList.getSearchType().equals(productList.DESCRIPTION_SEARCH_TYPE)) {
					product.setProductDescription(productList.getSearchField());
					wsProduct.setProductDescription(product.getProductDescription());
				} else if(productList.getSearchType().equals(productList.VENDOR_SEARCH_TYPE)) {
					product.getVendor().setVendorName(productList.getSearchField());
					com.kybpm.web.ws.client.InventoryManagerServiceImplServiceStub.Vendor wsVendor = new com.kybpm.web.ws.client.InventoryManagerServiceImplServiceStub.Vendor();
					wsVendor.setVendorName(product.getVendor().getVendorName());
					wsProduct.setVendor(wsVendor);
				}
								
				getProducts.setArg0(wsProduct);
				getProductsResponse = stub.getProducts(getProducts);
				log.info("Test");
				com.kybpm.web.ws.client.InventoryManagerServiceImplServiceStub.Product[] results = getProductsResponse.get_return().getItem();
	
				productList.getResultList().clear();
				if(results != null) {
					for(int i = 0; i < results.length; i++) {
						
						Product product = new Product();
						product.setProductId(results[i].getProductId());
						product.setCost(results[i].getCost());
						product.setDiscounts(new ArrayList<Discount>());
						if(results[i].getDiscounts() != null) {
							for(int j = 0; j < results[i].getDiscounts().length; j++) {
								Discount discount = new Discount();
								discount.setDiscountId(results[i].getDiscounts()[j].getDiscountId());
								discount.setAmountOff(results[i].getDiscounts()[j].getAmountOff());
								discount.setPercentDiscount(results[i].getDiscounts()[j].getAmountOff());
								discount.setStartTimestamp(results[i].getDiscounts()[j].getStartTimestamp().getTime());
								discount.setEndTimestamp(results[i].getDiscounts()[j].getEndTimestamp().getTime());
								discount.setProduct(product);
								product.getDiscounts().add(discount);
							}
						}
						product.setPrice(results[i].getPrice());
						product.setProductDescription(results[i].getProductDescription());
						
						//TODO:copy image from wsProduct to product
						//product.setProductImage(results[i].getProductImage());
						product.setProductName(results[i].getProductName());
						product.setProductOrders(new ArrayList<ProductOrder>());
						ProductType productType = new ProductType();
						if(results[i].getProductType() != null) {
							productType.setProductTypeId(results[i].getProductType().getProductTypeId());
							productType.setProductType(results[i].getProductType().getProductType());
						}
						product.setProductType(productType);
						product.setQuantity(results[i].getQuantity());
						Vendor productVendor = new Vendor();
						if(results[i].getVendor() != null) {
							productVendor.setVendorId(results[i].getVendor().getVendorId());
							productVendor.setVendorName(results[i].getVendor().getVendorName());
						}
						product.setVendor(productVendor);
						productList.getResultList().add(product);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			facesMessages.addToControl("errormsg", "An exception occurred to when " +
					"searching the inventory");
			return null;
		}
		return "";
	}
	
	public String addToCart() {
		
		int selectedId = productList.getSelectedItem();
		
		boolean foundProduct = false;
		for(ProductOrder productOrder : this.cart.getOrder().getProductOrders()) {
			if(productOrder.getProduct().getProductId() == selectedId) {
				foundProduct = true;
				productOrder.setQuantity(productOrder.getQuantity()+1);
				break;
			}
		}

		if(!foundProduct) {
			for(Product p : productList.getResultList()) {
				if(p.getProductId() == selectedId) {
					Product newProduct = new Product();
					p.copyTo(newProduct);
					ProductOrder productOrder = new ProductOrder(0, new com.kybpm.web.model.Order(), newProduct, 1);
					this.cart.getOrder().getProductOrders().add(productOrder);
					break;
				}
			}
		}
		checkout.calculateTotals();		
		return "";
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
}
