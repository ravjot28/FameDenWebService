package com.fameden.bean;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "FAMEDEN_USERS")
public class FamedenUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "famdenExternalUserId_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "famdenExternalUserId_seq", name = "famdenExternalUserId_seq")
	private int famdenExternalUserId;
	private String emailAddress;
	private String verificationCode;
	private char isVerified;
	private String registrationMode;
	private Date updateDate;
	private Date creationDate;
	private char active;

	public int getFamdenExternalUserId() {
		return famdenExternalUserId;
	}

	public void setFamdenExternalUserId(int famdenExternalUserId) {
		this.famdenExternalUserId = famdenExternalUserId;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getRegistrationMode() {
		return registrationMode;
	}

	public void setRegistrationMode(String registrationMode) {
		this.registrationMode = registrationMode;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public char getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(char isVerified) {
		this.isVerified = isVerified;
	}

	public char getActive() {
		return active;
	}

	public void setActive(char active) {
		this.active = active;
	}

}
