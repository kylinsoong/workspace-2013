/**
 * 
 */
package com.kybpm.common.dao.calculate;

import java.util.ArrayList;

import javax.ejb.Local;

import com.kybpm.common.entities.ShippingRate;
import com.kybpm.common.entities.TaxRate;

/**
 * @author Garvin Dean (Amentra, Inc.)
 *
 */
@Local
public interface CalculateDAO {

	public double getSalesTaxRateByState(String stateCode);
	public double getShippingRateByOriginDestinationZip(String originZipCode, String destinationZipCode);
	public int addShippingRate(ShippingRate shippingRate);
	public ShippingRate modifyShippingRate(ShippingRate shippingRate);
	public void deleteShippingRate(ArrayList<Integer> idList);
	public int addTaxRate(TaxRate taxRate );
	public TaxRate modifyTaxRate( TaxRate taxRate);
	public void deleteTaxRate(ArrayList<Integer> idList);
	public ShippingRate[] findAllShippingRates();
	public TaxRate[] findAllTaxRates();
	public TaxRate getTaxRateById(int id);
	public ShippingRate getShippingRateById(int id);
}
