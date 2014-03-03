/**
 * 
 */
package com.kybpm.common.dao.calculate;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;

import com.kybpm.common.entities.ShippingRate;
import com.kybpm.common.entities.TaxRate;

/**
 * @author Garvin Dean (Amentra, Inc.)
 * 
 */

@Stateless
public class CalculateDAOImpl implements CalculateDAO, CalculateDAORemote {

	@PersistenceContext(unitName = "kybpm")
	private EntityManager em;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.common.dao.calculate.CalculateDAO#getSalesTaxRateByState(java.lang
	 * .String)
	 */
	public double getSalesTaxRateByState(String stateCode) {
		Session session = (Session) em.getDelegate();
		Query query = session.getNamedQuery("TaxRate.findTaxRateByStateCode");
		query.setParameter("trStateCode", stateCode);
		Date trNow = new Date();
		query.setParameter("trNow", trNow);
		query.setParameter("trNow", trNow);
		TaxRate taxRate = (TaxRate) query.uniqueResult();
		return taxRate.getRate();
	}
	
	public TaxRate[] findAllTaxRates(){
		Session session = (Session) em.getDelegate();
		Query query = session.getNamedQuery("TaxRate.findAll");
		
		TaxRate[] taxRates = new TaxRate[]{};
		List list = query.list();
		if ( list.size() > 0) {
			taxRates = new TaxRate[list.size()];
		
		    for ( int i = 0; i < list.size(); i++){
		    	taxRates[i] = (TaxRate)list.get(i);
		    }
		}
		return taxRates;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.common.dao.calculate.CalculateDAO#getTaxRateById
	 * (java.lang.int)
	 */
	public TaxRate getTaxRateById(int id) {
		Session session = (Session) em.getDelegate();
		Query query = session
				.getNamedQuery("TaxRate.findById");
		query.setParameter("trId", id);
		TaxRate taxRate = (TaxRate) query.uniqueResult();
		return taxRate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.common.dao.calculate.CalculateDAO#getShippingRateById
	 * (java.lang.int)
	 */
	public ShippingRate getShippingRateById(int id) {
		Session session = (Session) em.getDelegate();
		Query query = session
				.getNamedQuery("ShippingRate.findById");
		query.setParameter("srId", id);
		ShippingRate shippingRate = (ShippingRate) query.uniqueResult();
		return shippingRate;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.common.dao.calculate.CalculateDAO#getShippingRateByOriginDestinationZip
	 * (java.lang.String)
	 */
	public double getShippingRateByOriginDestinationZip(String originZipCode,
			String destinationZipCode) {
		Session session = (Session) em.getDelegate();
		Query query = session
				.getNamedQuery("ShippingRate.findToDestinationShippingRate");
		query.setParameter("srDestinationZipCode", destinationZipCode);
		query.setParameter("srOriginZipCode", originZipCode);
		Date srNow = new Date();
		query.setParameter("srNow", srNow);
		query.setParameter("srNow", srNow);
		ShippingRate shippingRate = (ShippingRate) query.uniqueResult();
		return shippingRate.getCost();
	}
	
	public ShippingRate[] findAllShippingRates(){
		Session session = (Session) em.getDelegate();
		Query query = session.getNamedQuery("ShippingRate.findAll");
		ShippingRate[] shippingRates = new ShippingRate[]{};
		List list = query.list();
		if ( list.size() > 0) {
		    shippingRates = new ShippingRate[list.size()];
		
		    for ( int i = 0; i < list.size(); i++){
			    shippingRates[i] = (ShippingRate)list.get(i);
		    }
		}
		return shippingRates;
		
	}

	public int addShippingRate(ShippingRate shippingRate) {
		try {
			Session session = (Session) em.getDelegate();
			session.save(shippingRate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return shippingRate.getId();
	}

	public int addTaxRate(TaxRate taxRate) {
		try {
			Session session = (Session) em.getDelegate();
			session.save(taxRate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return taxRate.getId();
	}

	public void deleteShippingRate(ArrayList<Integer> idList) {
		try {
			Session session = (Session) em.getDelegate();
			for (Integer id : idList) {
				ShippingRate shippingRate = em.find(ShippingRate.class, id);
				session.delete(shippingRate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteTaxRate(ArrayList<Integer> idList) {
		try {
			Session session = (Session) em.getDelegate();
			for (Integer id : idList) {
				TaxRate taxRate = em.find(TaxRate.class, id);
				session.delete(taxRate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ShippingRate modifyShippingRate(ShippingRate shippingRate) {
		ShippingRate oldShippingRate = null;
		System.out.println("ShippingRate" + shippingRate.toString());
		try {
			if ( shippingRate.getId() < 1) {
				return null;
			}
			Session session = (Session) em.getDelegate();
			oldShippingRate = em.find(ShippingRate.class,
					shippingRate.getId());
			System.out.println("OLD ShippingRate" + oldShippingRate.toString());
			if (shippingRate.getCost() != oldShippingRate.getCost()) {
				oldShippingRate.setCost(shippingRate.getCost());
			}

			if (!shippingRate.getDestinationZipCode().equals(
					oldShippingRate.getDestinationZipCode())) {
				oldShippingRate.setDestinationZipCode(shippingRate.getDestinationZipCode());
			}

			if (!shippingRate.getOriginZipCode().equals(
					oldShippingRate.getOriginZipCode())) {
				oldShippingRate.setOriginZipCode(shippingRate.getOriginZipCode());
			}

			if (shippingRate.getEffectiveStartDate() != 
					oldShippingRate.getEffectiveStartDate()) {
				oldShippingRate.setEffectiveStartDate(shippingRate.getEffectiveStartDate());
			}
			
			if (shippingRate.getExpirationDate() != 
				oldShippingRate.getExpirationDate()) {
				oldShippingRate.setExpirationDate(shippingRate.getExpirationDate());
			}
			session.update(oldShippingRate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oldShippingRate;
	}

	public TaxRate modifyTaxRate(TaxRate taxRate) {
		try {
			Session session = (Session) em.getDelegate();
			session.saveOrUpdate(taxRate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return taxRate;
	}

}
