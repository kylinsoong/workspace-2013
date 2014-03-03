/**
 * 
 */
package com.kybpm.common.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * @author Garvin Dean (Amentra, Inc.)
 * 
 */

@Entity
@NamedQueries( {
	    @NamedQuery(name = "ShippingRate.findById", query = "SELECT sr FROM ShippingRate AS sr WHERE sr.id = :srId"),
		@NamedQuery(name = "ShippingRate.findAll", query = "SELECT sr FROM ShippingRate AS sr"),
		@NamedQuery(name = "ShippingRate.findToFromShippingRate", query = "SELECT sr FROM ShippingRate AS sr WHERE sr.originZipCode = :srOriginZipCode AND sr.destinationZipCode = :srDestinationZipCode"),
		@NamedQuery(name = "ShippingRate.findToDestinationShippingRate", query = "SELECT sr FROM ShippingRate AS sr WHERE sr.destinationZipCode = :srDestinationZipCode AND sr.originZipCode = :srOriginZipCode "
				+ "AND sr.effectiveStartDate <= :srNow AND sr.expirationDate >= :srNow") })
@Table(name = "shipping_rate", catalog = "jb336")
public class ShippingRate implements Serializable {

	private Integer id;
	private String destinationZipCode;
	private String originZipCode;
	private Double cost;
	private Date effectiveStartDate;
	private Date expirationDate;

	public ShippingRate() {
		// Default
	}

	/**
	 * @param id
	 * @param destinationZipCode
	 * @param originZipCode
	 * @param cost
	 * @param effectiveStartDate
	 * @param expirationDate
	 */
	public ShippingRate(Integer id, String destinationZipCode,
			String originZipCode, Double cost, Date effectiveStartDate,
			Date expirationDate) {
		this.id = id;
		this.destinationZipCode = destinationZipCode;
		this.originZipCode = originZipCode;
		this.cost = cost;
		this.effectiveStartDate = effectiveStartDate;
		this.expirationDate = expirationDate;
	}

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "shipping_rate_id", unique = true)
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the destinationZipCode
	 */
	@Column(name = "destination_zipcode", nullable = false, length = 5)
	@NotNull
	@Length(max = 5)
	public String getDestinationZipCode() {
		return destinationZipCode;
	}

	/**
	 * @param destinationZipCode
	 *            the destinationZipCode to set
	 */
	public void setDestinationZipCode(String destinationZipCode) {
		this.destinationZipCode = destinationZipCode;
	}

	/**
	 * @return the originZipCode
	 */
	@Column(name = "origin_zipcode", nullable = false, length = 5)
	@NotNull
	@Length(max = 5)
	public String getOriginZipCode() {
		return originZipCode;
	}

	/**
	 * @param originZipCode
	 *            the originZipCode to set
	 */
	public void setOriginZipCode(String originZipCode) {
		this.originZipCode = originZipCode;
	}

	/**
	 * @return the cost
	 */
	@Column(name = "cost", nullable = false, precision = 22, scale = 0)
	@NotNull
	public Double getCost() {
		return cost;
	}

	/**
	 * @param cost
	 *            the cost to set
	 */
	public void setCost(Double cost) {
		this.cost = cost;
	}

	/**
	 * @return the effectiveStartDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "effective_start_dt", nullable = false, length = 19)
	@NotNull
	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}

	/**
	 * @param effectiveStartDate
	 *            the effectiveStartDate to set
	 */
	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	/**
	 * @return the expirationDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "effective_end_dt", nullable = false, length = 19)
	@NotNull
	public Date getExpirationDate() {
		return expirationDate;
	}

	/**
	 * @param expirationDate
	 *            the expirationDate to set
	 */
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getClass().getName() + "::id=" + id + " destinationZipCode=" + destinationZipCode + " originZipCode=" + originZipCode +
		" cost=" + cost + " effectiveStartDate=" + effectiveStartDate + " expirationDate=" +  expirationDate;
	}
	
	

}
