/**
 * 
 */
package com.kybpm.inventory.service;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebService;

import com.kybpm.common.dao.inventory.InventoryDAO;
import com.kybpm.common.entities.Product;

/**
 * @author Amentra Inc.
 *
 */
@Stateless
@WebService(endpointInterface="com.kybpm.inventory.service.InventoryManagerService")
@Remote(value=InventoryManagerService.class)
public class InventoryManagerServiceImpl{

	@EJB(mappedName="InventoryDAOImpl/local")
	private InventoryDAO inventoryDAO;
	
	/**
	 * @param product
	 * @return
	 */
	public Integer addProduct(Product product) {
		try{
			inventoryDAO.addProduct(product);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return product.getProductId();
	}

	/**
	 * @param idList
	 */
	public void deleteProduct(ArrayList<Integer> idList) {
		try{
			inventoryDAO.deleteProduct(idList);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @param productId
	 * @return
	 */
	public Product getProductById(Integer productId) {
		Product product = new Product();
		try{	
			product = inventoryDAO.getProductById(productId);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return product;
	}

	/**
	 * @param product
	 * @return
	 */
	public Product[] getProducts(Product product) {
		Product[] productArray = {};
		try{
			productArray = inventoryDAO.getProducts(product);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return productArray;
	}
	

	
}
