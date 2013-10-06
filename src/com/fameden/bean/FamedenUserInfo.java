package com.fameden.bean;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "FAMEDEN_USER_INFO")
public class FamedenUserInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "famdenExternalUserId")
	private FamedenUser famedenUser;
	private String fullName;
	private String mobileNo;
	private String alternateEmailAddress;

	public FamedenUser getFamedenUser() {
		return famedenUser;
	}

	public void setFamedenUser(FamedenUser famedenUser) {
		this.famedenUser = famedenUser;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getAlternateEmailAddress() {
		return alternateEmailAddress;
	}

	public void setAlternateEmailAddress(String alternateEmailAddress) {
		this.alternateEmailAddress = alternateEmailAddress;
	}

}
