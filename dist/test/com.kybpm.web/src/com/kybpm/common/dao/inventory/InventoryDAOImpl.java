package com.kybpm.common.dao.inventory;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.kybpm.common.entities.Product;

@Stateless
public class InventoryDAOImpl implements InventoryDAO {

	@PersistenceContext(unitName="kybpm")
	private EntityManager em;
	
	private Log log;

	/* (non-Javadoc)
	 * @see com.common.dao.inventory.InventoryDAO#addProduct(com.common.entities.Product)
	 */
	public Integer addProduct(Product product) {
		try{
			Session session = (Session)em.getDelegate();
			session.save(product);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return product.getProductId();
	}

	/* (non-Javadoc)
	 * @see com.common.dao.inventory.InventoryDAO#deleteProduct(java.util.ArrayList)
	 */
	public void deleteProduct(ArrayList<Integer> idList) {
		try{
			Session session = (Session)em.getDelegate();
			for (Integer id : idList) {
				Product product = em.find(Product.class, id);
				session.delete(product);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}

		
	}

	/* (non-Javadoc)
	 * @see com.common.dao.inventory.InventoryDAO#getProductById(java.lang.Integer)
	 */
	public Product getProductById(Integer productId) {
		Product product = new Product();
		Product[] productList = {};
		try{	
			Session session = (Session)em.getDelegate();
			org.hibernate.Query query = session.createQuery("select p from Product p where p.productId = :productId");
			query.setParameter("productId", productId);
			
			//TODO: NEED TO FIGURE OUT HOW to retrieve a single object by ID
			/*Query query = em.createQuery("select p from Product p where p.productId = ?1");
			query.setParameter(1, productId);*/
			List criteriaList = query.list();
			productList = new Product[criteriaList.size()];
			int count = 0;
			for(Object object : criteriaList) {
				productList[count] = (Product)object; 
				count++;				
			}
			System.out.println(session.isConnected() + " SESSION IS CONNECTED : SESSION IS OPEN "+ session.isOpen());
			System.out.println(productList.length + " <-- product list size");
			if(productList.length > 0) {
				product = productList[0];
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(product.getProductName());
		return product;
	}

	/* (non-Javadoc)
	 * @see com.common.dao.inventory.InventoryDAO#getProducts(com.common.entities.Product)
	 */
	@SuppressWarnings("unchecked")
	public Product[] getProducts(Product product) {
		//ArrayList<Product> productList = new ArrayList();
		Product[] productArray = {};
		try{	
			Session session = (Session)em.getDelegate();
			//Example exampleProduct = Example.create(product);
			Criteria criteria = session.createCriteria(Product.class);
			if( product.getProductId() != null && product.getProductId() > 0) {
				criteria.add(Restrictions.eq("productId",product.getProductId()));
			}			
			if( product.getProductName() != null && !product.getProductName().isEmpty() ) {
				criteria.add(Restrictions.like("productName", "%" + product.getProductName() + "%"));
			}
			if( product.getProductDescription() != null && !product.getProductDescription().isEmpty() ) {
				criteria.add(Restrictions.like("productDescription", "%" + product.getProductDescription() + "%"));
			}
			if( product.getCost() != 0 ) {
				criteria.add(Restrictions.like("cost", product.getCost()));
			}
			if( product.getPrice() != 0 ) {
				criteria.add(Restrictions.like("price", product.getPrice()));
			}
			if( product.getQuantity() != 0 ) {
				criteria.add(Restrictions.like("quantity", product.getQuantity()));
			}
			if ( product.getProductType() != null && product.getProductType().getProductTypeId() != null ) {
				criteria.add(Restrictions.like("productType", product.getProductType()));
			}
			if ( product.getVendor() != null && !product.getVendor().getVendorName().isEmpty()) {
				criteria.createAlias("vendor", "vend");
				criteria.add(Restrictions.like("vend.vendorName", "%" + product.getVendor().getVendorName() + "%"));
			}
			List<Product> criteriaList = criteria.list();
			productArray = new Product[criteriaList.size()];
			int count = 0;
			for (Object object : criteriaList) {
				productArray[count] = (Product)object;
				count++;
			}
			
			//System.out.println(productList.get(0).getProductName());
/*			if(criteriaList.size() > 0){

				for (Object object : criteriaList) {
					System.out.println(object.getClass().toString());
				}
			}*/
			//productList.add(criteria.list());	
		}
		catch(Exception e){
			e.printStackTrace();
		}
/*		System.out.println("Products returned: "+ productList.size());
		System.out.println("Product ID: " + productList.get(0).getProductId());
		System.out.println("Product Name: " + productList.get(0).getProductName());
		System.out.println("Product Description: " + productList.get(0).getProductDescription()); */
		
		return productArray;//productList;
	}
}
