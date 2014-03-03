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

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * @author Garvin Dean (Amentra, Inc.)
 * 
 */
@Entity
@NamedQueries( {
		@NamedQuery(name = "TaxRate.findById", query = "SELECT tr FROM TaxRate AS tr WHERE tr.id = :trId"),
		@NamedQuery(name = "TaxRate.findAll", query = "SELECT tr FROM TaxRate AS tr"),
		@NamedQuery(name = "TaxRate.findTaxRateByStateCode", query = "SELECT tr FROM TaxRate AS tr WHERE tr.stateCode = :trStateCode "
				+ "AND tr.effectiveStartDate <= :trNow AND tr.expirationDate >= :trNow") })
@Table(name = "tax_rate", catalog = "jb336")
public class TaxRate implements Serializable {

	private Integer id;
	private String stateCode;
	private Double rate;
	private Date effectiveStartDate;
	private Date expirationDate;

	public TaxRate() {
		// default
	}

	/**
	 * @param id
	 * @param stateCode
	 * @param rate
	 * @param effectiveStartDate
	 * @param expirationDate
	 */
	public TaxRate(Integer id, String stateCode, Double rate,
			Date effectiveStartDate, Date expirationDate) {
		this.id = id;
		this.stateCode = stateCode;
		this.rate = rate;
		this.effectiveStartDate = effectiveStartDate;
		this.expirationDate = expirationDate;
	}

	/**
	 * @param id
	 * @param stateCode
	 * @param rate
	 * @param effectiveStartDate
	 */
	public TaxRate(Integer id, String stateCode, Double rate,
			Date effectiveStartDate) {
		this.id = id;
		this.stateCode = stateCode;
		this.rate = rate;
		this.effectiveStartDate = effectiveStartDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tax_rate_id", unique = true)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "state_code", nullable = false, length = 2)
	@NotNull
	@Length(max = 2)
	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	@Column(name = "rate", nullable = false, precision = 22, scale = 0)
	@NotNull
	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "eff_start_date", nullable = false, length = 19)
	@NotNull
	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "eff_end_date", nullable = false, length = 19)
	@NotNull
	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	public String toString(){
		return this.getClass().getName() + ":id="+ id + " stateCode=" + stateCode + " rate=" + rate + " effectiveStartDate=" + effectiveStartDate +
		" expirationDate=" + expirationDate;
	}
}
