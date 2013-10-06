package com.fameden.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "FAMEDEN_REQUEST_TBL")
public class FamedenRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "requestID_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "requestID_seq", name = "requestID_seq")
	private int requestID;
	private String requestType;
	private Date requestDate;
	private Date requestUpdateDate;
	private String requestUser;
	private String requestStatus;
	private String customerIP;
	private int famedenUserId;

	public int getRequestID() {
		return requestID;
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Date getRequestUpdateDate() {
		return requestUpdateDate;
	}

	public void setRequestUpdateDate(Date requestUpdateDate) {
		this.requestUpdateDate = requestUpdateDate;
	}

	public String getRequestUser() {
		return requestUser;
	}

	public void setRequestUser(String requestUser) {
		this.requestUser = requestUser;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getCustomerIP() {
		return customerIP;
	}

	public void setCustomerIP(String customerIP) {
		this.customerIP = customerIP;
	}

	public int getFamedenUserId() {
		return famedenUserId;
	}

	public void setFamedenUserId(int famedenUserId) {
		this.famedenUserId = famedenUserId;
	}

}
