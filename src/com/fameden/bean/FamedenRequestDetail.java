package com.fameden.bean;

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
public class FamedenRequestDetail {
	@Id
	@GeneratedValue(generator = "requestID_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "requestID_seq", name = "requestID_seq")
	private int requestDetailID;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "requestID")
	private int requestID;
	private String itemName;
	private String itemID;
	private String itemType;
	private String customerCost;
	private String paymentMode;

	public int getRequestDetailID() {
		return requestDetailID;
	}

	public void setRequestDetailID(int requestDetailID) {
		this.requestDetailID = requestDetailID;
	}

	public int getRequestID() {
		return requestID;
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
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

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getCustomerCost() {
		return customerCost;
	}

	public void setCustomerCost(String customerCost) {
		this.customerCost = customerCost;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

}
