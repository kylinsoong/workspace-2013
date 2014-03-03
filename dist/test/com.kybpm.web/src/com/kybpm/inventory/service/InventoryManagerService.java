package com.kybpm.inventory.service;

import java.util.ArrayList;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.kybpm.common.entities.Product;

/**
 * @author Amentra Inc.
 *
 */
@WebService
@SOAPBinding(style = Style.RPC)
public interface InventoryManagerService {
	
	/**
	 * @param product
	 * @return
	 */
	public Product[] getProducts(Product product);
	
	/**
	 * @param productId
	 * @return
	 */
	public Product getProductById(Integer productId);
	
	/**
	 * @param product
	 */
	public Integer addProduct(Product product);
	
	/**
	 * @param idList
	 */
	public void deleteProduct(ArrayList<Integer> idList);
}
