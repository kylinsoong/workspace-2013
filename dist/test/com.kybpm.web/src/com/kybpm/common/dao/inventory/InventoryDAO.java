package com.kybpm.common.dao.inventory;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;

import com.kybpm.common.entities.Product;

@Local
public interface InventoryDAO {
	
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
