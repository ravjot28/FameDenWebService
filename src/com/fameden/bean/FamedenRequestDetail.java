package com.fameden.bean;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "FAMEDEN_REQUEST_DETAIL_TBL")
public class FamedenRequestDetail implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "requestID_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "requestID_seq", name = "requestID_seq")
	private int requestDetailID;
	private String itemName;
	private String itemID;
	private String itemType;
	private double customerCost;
	private String paymentMode;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "requestID")
	private FamedenRequest famedenRequest;
	
	

	public FamedenRequest getFamedenRequest() {
		return famedenRequest;
	}

	public void setFamedenRequest(FamedenRequest famedenRequest) {
		this.famedenRequest = famedenRequest;
	}

	public int getRequestDetailID() {
		return requestDetailID;
	}

	public void setRequestDetailID(int requestDetailID) {
		this.requestDetailID = requestDetailID;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemID() {
		return itemID;
	}

	public void setItemID(String i) {
		this.itemID = i;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public double getCustomerCost() {
		return customerCost;
	}

	public void setCustomerCost(double customerCost) {
		this.customerCost = customerCost;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

}
