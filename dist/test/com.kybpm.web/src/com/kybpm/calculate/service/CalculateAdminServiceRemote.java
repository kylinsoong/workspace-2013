package com.kybpm.calculate.service;

import java.util.ArrayList;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.kybpm.common.entities.ShippingRate;
import com.kybpm.common.entities.TaxRate;

@WebService
@SOAPBinding(style = Style.RPC)
public interface CalculateAdminServiceRemote {
	public int addShippingRate(String destinationZip, String originZip,
			double cost, String effectiveDateYYYYMMDDHHmmss,
			String expirationDateYYYYMMDDHHmmss);

	public int addTaxRate(String stateCode, double rate,
			String effectiveDateYYYYMMDDHHmmSS,
			String expirationDateYYYYMMDDHHmmSS);

	public void removeShippingRates(ArrayList<Integer> ids);

	public void removeTaxRates(ArrayList<Integer> ids);

	public ShippingRate modifyShippingRateData(int id, String destinationZip,
			String originZip, double cost, String effectiveDateYYYYMMDDHHmmss,
			String expirationDateYYYYMMDDHHmmss);

	public ShippingRate modifyShippingRate(ShippingRate shippingRate);

	public TaxRate modifyTaxRate(TaxRate taxRate);

	public TaxRate[] findAllTaxRates();

	public ShippingRate[] findAllShippingRates();
	
	public TaxRate getTaxRateById(int id);
	
	public ShippingRate getShippingRateById(int id);

}
