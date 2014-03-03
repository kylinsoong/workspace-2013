package com.kybpm.calculate.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebService;

import com.kybpm.common.dao.calculate.CalculateDAO;
import com.kybpm.common.entities.ShippingRate;
import com.kybpm.common.entities.TaxRate;

/**
 * Session Bean implementation class CalculateAdminServiceImpl
 */
@Stateless
@WebService(endpointInterface = "com.kybpm.calculate.service.CalculateAdminService")
@Remote(value = CalculateAdminService.class)
public class CalculateAdminServiceImpl {

	@EJB(mappedName="CalculateDAOImpl/local")
	CalculateDAO calculateDAO;

	/**
	 * Default constructor.
	 */
	public CalculateAdminServiceImpl() {
	}

	private static final String DATE_FORMAT = "yyyyMMddHHmmss";
	private static final SimpleDateFormat sdf = new java.text.SimpleDateFormat(
			DATE_FORMAT);

	public int addShippingRate(String destinationZip, String originZip,
			double cost, String effectiveDateYYYYMMDDHHmmss,
			String expirationDateYYYYMMDDHHmmss) {
		ShippingRate newShippingRate = createShippingRate(destinationZip,
				originZip, cost, effectiveDateYYYYMMDDHHmmss,
				expirationDateYYYYMMDDHHmmss);

		int id = calculateDAO.addShippingRate(newShippingRate);
		return (id > 1 ? id : -1);
	}

	public int addTaxRate(String stateCode, double rate,
			String effectiveDateYYYYMMDDHHmmss,
			String expirationDateYYYYMMDDHHmmss) {
		TaxRate taxRate = new TaxRate();

		try {
			Date effectiveDate = sdf.parse(effectiveDateYYYYMMDDHHmmss);
			Date expirationDate = sdf.parse(expirationDateYYYYMMDDHHmmss);
			taxRate.setEffectiveStartDate(effectiveDate);
			taxRate.setExpirationDate(expirationDate);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Couldn't parse the date string "
					+ effectiveDateYYYYMMDDHHmmss + " or "
					+ expirationDateYYYYMMDDHHmmss);
		}
		taxRate.setRate(rate);
		taxRate.setStateCode(stateCode);
		int id = calculateDAO.addTaxRate(taxRate);
		return (id > 1 ? id : -1);
	}

	public ShippingRate modifyShippingRateData(int id, String destinationZip,
			String originZip, double cost, String effectiveDateYYYYMMDDHHmmss,
			String expirationDateYYYYMMDDHHmmss) {
		ShippingRate newShippingRate = createShippingRate(destinationZip,
				originZip, cost, effectiveDateYYYYMMDDHHmmss,
				expirationDateYYYYMMDDHHmmss);
		ShippingRate savedShippingRate = modifyShippingRate(newShippingRate);
		return savedShippingRate;
	}

	public ShippingRate modifyShippingRate(ShippingRate newShippingRate) {
		ShippingRate savedShippingRate = calculateDAO
				.modifyShippingRate(newShippingRate);
		return savedShippingRate;
	}

	public TaxRate modifyTaxRate(TaxRate taxRate) {
		TaxRate savedTaxRate = calculateDAO.modifyTaxRate(taxRate);
		return savedTaxRate;
	}

	public void removeShippingRates(ArrayList<Integer> ids) {
		calculateDAO.deleteShippingRate(ids);
	}

	public void removeTaxRates(ArrayList<Integer> ids) {
		calculateDAO.deleteTaxRate(ids);
	}

	public TaxRate[] findAllTaxRates() {
		return calculateDAO.findAllTaxRates();

	}

	public ShippingRate[] findAllShippingRates() {
		return calculateDAO.findAllShippingRates();
	}

	public TaxRate getTaxRateById(int id) {
		return calculateDAO.getTaxRateById(id);
	}

	public ShippingRate getShippingRateById(int id) {
		return calculateDAO.getShippingRateById(id);
	}

	private ShippingRate createShippingRate(String destinationZip,
			String originZip, double cost, String effectiveDateYYYYMMDDHHmmss,
			String expirationDateYYYYMMDDHHmmss) {

		ShippingRate shippingRate = new ShippingRate();

		try {
			Date effectiveDate = sdf.parse(effectiveDateYYYYMMDDHHmmss);
			Date expirationDate = sdf.parse(expirationDateYYYYMMDDHHmmss);
			shippingRate.setEffectiveStartDate(effectiveDate);
			shippingRate.setExpirationDate(expirationDate);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Couldn't parse the date string "
					+ effectiveDateYYYYMMDDHHmmss + " or "
					+ expirationDateYYYYMMDDHHmmss);
		}

		shippingRate.setDestinationZipCode(destinationZip);
		shippingRate.setOriginZipCode(originZip);
		shippingRate.setCost(cost);
		return shippingRate;
	}

}
