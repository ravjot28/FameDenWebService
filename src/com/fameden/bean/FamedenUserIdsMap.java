package com.fameden.bean;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "FAMEDEN_USER_MAPPING")
public class FamedenUserIdsMap implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private FamedenUserMappingCompositePK famedenUserMappingCompositePK;

	public FamedenUserMappingCompositePK getFamedenUserMappingCompositePK() {
		return famedenUserMappingCompositePK;
	}

	public void setFamedenUserMappingCompositePK(
			FamedenUserMappingCompositePK famedenUserMappingCompositePK) {
		this.famedenUserMappingCompositePK = famedenUserMappingCompositePK;
	}

}
