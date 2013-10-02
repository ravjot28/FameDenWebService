package com.fameden.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "FAMEDEN_USER_KEYS")
public class FamedenUserKeys implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "famdenInternalUserId_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "famdenInternalUserId_seq", name = "famdenInternalUserId_seq")
	private int famdenInternalUserId;
	private String password;
	private String pubicToken;
	private String privateToken;

	public int getFamdenInternalUserId() {
		return famdenInternalUserId;
	}

	public void setFamdenInternalUserId(int famdenInternalUserId) {
		this.famdenInternalUserId = famdenInternalUserId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPubicToken() {
		return pubicToken;
	}

	public void setPubicToken(String pubicToken) {
		this.pubicToken = pubicToken;
	}

	public String getPrivateToken() {
		return privateToken;
	}

	public void setPrivateToken(String privateToken) {
		this.privateToken = privateToken;
	}

}
