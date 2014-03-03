/**
 * 
 */
package com.kybpm.calculate.service;

import java.util.Iterator;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebService;

import com.kybpm.common.dao.calculate.CalculateDAO;
import com.kybpm.common.dao.inventory.InventoryDAO;
import com.kybpm.common.domains.calculate.SalesTotal;
import com.kybpm.common.entities.Order;
import com.kybpm.common.entities.Product;
import com.kybpm.common.entities.ProductOrder;

/**
 * @author Garvin Dean (Amentra, Inc.)
 * 
 */
@Stateless
@WebService(endpointInterface = "com.kybpm.calculate.service.CalculateService")
@Remote(value = CalculateService.class)
public class CalculateServiceImpl {
	private static final double DEFAULT_TAX_RATE = 0.065d;
	private static final double DEFAULT_SHIPPING_FEE = 15.00d; 

	@EJB(mappedName="CalculateDAOImpl/local")
	CalculateDAO calculateDAO;
	
	@EJB(mappedName="InventoryDAOImpl/local")
	InventoryDAO inventoryDAO;

	public SalesTotal calculateGrandTotal(Order cart) {
		SalesTotal salesTotal = null;
		try {
			// Get state code from billing address to determine the sales tax
			// rate;
			String taxingState = cart.getAddressByBillAddressId().getState();

			// look up tax rate by state code:
			double taxRate = calculateDAO.getSalesTaxRateByState(taxingState);
			if ( taxRate == 0d){
				// Assign a default tax rate of 6.5%
				taxRate = DEFAULT_TAX_RATE;
			}

			// Get zip code from shipping address to determine the shipping fee.
			String shipToZipCode = cart.getAddressByShipAddressId().getZip();
			String shipFromZipCode = "27606";
			double shippingFee = calculateDAO
					.getShippingRateByOriginDestinationZip(shipFromZipCode, shipToZipCode);
			if ( shippingFee == 0d ) {
				//Assign a default shipping fee of $15.00
				shippingFee = DEFAULT_SHIPPING_FEE;
			}

			double productTotal = 0.0;
			Iterator<ProductOrder> pIter = cart.getProductOrders().iterator();
			while (pIter.hasNext()) {
				ProductOrder po = pIter.next();
				Product pPurchase = po.getProduct();
				Product pInventory = null;
				if ( pPurchase.getProductId() != null ){
					pInventory = inventoryDAO.getProductById(pPurchase.getProductId());
					if ( pInventory == null ) {
						throw new Exception("Could not find product in inventory, please verify product id.");
					}
				}
				if ( pInventory.getQuantity() < pPurchase.getQuantity()) {
					throw new Exception( "Inventory alert, waiting on more stock from the manufacture for product " + pInventory.getProductName());
				}
				productTotal += (pInventory.getPrice() * (po.getQuantity() >= 1 ? po.getQuantity() : 1));
			}
			salesTotal = new SalesTotal(productTotal, productTotal *  taxRate, shippingFee);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return (salesTotal == null ? new SalesTotal(0d, 0d, 0d) : salesTotal);
	}

	public Double calculateProductTotal(Order cart) {
		SalesTotal st = calculateGrandTotal(cart);
		return st.getProductTotal();
	}

	public Double calculateSalesTax(Order cart) {
		SalesTotal st = calculateGrandTotal(cart);
		return st.getTaxTotal();

	}

	public Double calculateShippingTotal(Order cart) {
		SalesTotal st = calculateGrandTotal(cart);
		return st.getShippingTotal();
	}

}
