package com.fameden.bean;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Embeddable
public class FamedenUserMappingCompositePK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "famdenExternalUserId")
	private FamedenUser famedenUser;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "famdenInternalUserId")
	private FamedenUserKeys famedenUserKeys;

	public FamedenUser getFamedenUser() {
		return famedenUser;
	}

	public void setFamedenUser(FamedenUser famedenUser) {
		this.famedenUser = famedenUser;
	}

	public FamedenUserKeys getFamedenUserKeys() {
		return famedenUserKeys;
	}

	public void setFamedenUserKeys(FamedenUserKeys famedenUserKeys) {
		this.famedenUserKeys = famedenUserKeys;
	}

}
